package com.renchao.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renchao.common.constant.WareConstant;
import com.renchao.common.exception.WareLockException;
import com.renchao.common.to.OrderTo;
import com.renchao.common.to.WareLockTo;
import com.renchao.common.utils.R;
import com.renchao.mall.ware.entity.WareOrderTaskDetailEntity;
import com.renchao.mall.ware.entity.WareOrderTaskEntity;
import com.renchao.mall.ware.feign.OrderFeignService;
import com.renchao.mall.ware.feign.ProductFeignService;
import com.renchao.mall.ware.service.WareOrderTaskDetailService;
import com.renchao.mall.ware.service.WareOrderTaskService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.mall.ware.dao.WareSkuDao;
import com.renchao.mall.ware.entity.WareSkuEntity;
import com.renchao.mall.ware.service.WareSkuService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private WareOrderTaskService taskService;

    @Autowired
    WareOrderTaskDetailService taskDetailService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderFeignService orderFeignService;

    @Override

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                new QueryWrapper<WareSkuEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageWrapper(Map<String, Object> params) {
        Object wareId = params.get("wareId");
        Object skuId = params.get("skuId");
        LambdaQueryWrapper<WareSkuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!StringUtils.isEmpty(wareId), WareSkuEntity::getWareId, wareId)
                .eq(!StringUtils.isEmpty(skuId), WareSkuEntity::getSkuId, skuId);
        IPage<WareSkuEntity> page = this.page(new Query<WareSkuEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        LambdaQueryWrapper<WareSkuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WareSkuEntity::getSkuId, skuId).eq(WareSkuEntity::getWareId, wareId);
        List<WareSkuEntity> skus = this.list(wrapper);
        // 如果没有库存，就添加，如果有就更新
        if (CollectionUtils.isEmpty(skus)) {
            WareSkuEntity wareSku = new WareSkuEntity();
            wareSku.setSkuId(skuId);
            wareSku.setWareId(wareId);
            try {
                R r = productFeignService.skuInfo(skuId);
                Map<String, String> skuInfo = (Map<String, String>) r.get("skuInfo");
                wareSku.setSkuName(skuInfo.get("skuName"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            wareSku.setStock(skuNum);
            this.save(wareSku);
        } else {
            this.baseMapper.addStock(skuId, wareId, skuNum);
        }
    }

    @Override
    public Integer hasStock(Long skuId) {
        return baseMapper.selectStockBySkuId(skuId);
    }

    @Override
    public Map<Long, Boolean> hasStock(List<Long> ids) {
        Map<Long, Boolean> map = new HashMap<>();
        for (Long id : ids) {
            Integer stock = hasStock(id);
            map.put(id, stock != null && stock > 0);
        }
        return map;
    }

    /**
     * 锁定库存
     */
    @Override
    @Transactional
    public void wareLock(OrderTo orderTo) {
        List<WareLockTo> wareLockTos = orderTo.getWareLockTos();
        WareOrderTaskEntity task = new WareOrderTaskEntity();
        BeanUtils.copyProperties(orderTo, task);
        task.setCreateTime(new Date());
        task.setTaskStatus(1);
        taskService.save(task);
        Long taskId = task.getId();

        // 遍历每个sku
        for (WareLockTo lockTo : wareLockTos) {
            WareOrderTaskDetailEntity taskDetail = new WareOrderTaskDetailEntity();
            taskDetail.setSkuId(lockTo.getSkuId());
            taskDetail.setSkuName(lockTo.getSkuName());
            taskDetail.setSkuNum(lockTo.getSkuQuantity());
            taskDetail.setTaskId(taskId);
            taskDetail.setLockStatus(1);

            // 获取所有有当前sku的仓库
            List<WareSkuEntity> list = this.listBySkuId(lockTo.getSkuId());
            if (CollectionUtils.isEmpty(list)) {
                throw new WareLockException("库存不足！");
            }
            // 选择一个仓库进行锁定，可以根据地址就近选择，这里没有做该功能。
            boolean isLock = false;
            for (WareSkuEntity entity : list) {
                Integer stock = entity.getStock();
                Integer stockLocked = entity.getStockLocked();
                Integer quantity = lockTo.getSkuQuantity();
                if (stockLocked + quantity <= stock) {
                    Integer row = this.getBaseMapper().wareLock(entity.getId(), quantity);
                    if (row > 0) {
                        isLock = true;
                        taskDetail.setWareId(entity.getWareId());
                        break;
                    }
                }
            }
            // 只要有一个没有锁定成功，都通过异常机制返回
            if (!isLock) {
                throw new WareLockException("库存不足！");
            }
            taskDetailService.save(taskDetail);
        }
        // 检查订单是否完成，如果订单服务没有完成，需要解锁库存
        rabbitTemplate.convertAndSend(WareConstant.WARE_EVENT_EXCHANGE, WareConstant.WARE_DELAY_BINDING, orderTo.getOrderId());
    }

    @Override
    public List<WareSkuEntity> listBySkuId(Long skuId) {
        LambdaQueryWrapper<WareSkuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WareSkuEntity::getSkuId, skuId);
        wrapper.gt(WareSkuEntity::getStock, 0);
        return this.list(wrapper);
    }

    /**
     * 解锁库存
     */
    @Override
    public void unlockStock(Long orderId) {
        WareOrderTaskEntity task = taskService.getByOrderId(orderId);
        if (task == null || task.getTaskStatus() != 1) {
            return;
        }
        R r = orderFeignService.getOrderInfo(task.getOrderId());
        Object status = r.get("status");
        if (!(status == null || status.equals(4) || status.equals(5))) {
            return;
        }
        // 解锁工作单
        WareOrderTaskEntity nweTask = new WareOrderTaskEntity();
        nweTask.setId(task.getId());
        nweTask.setTaskStatus(2);
        taskService.updateById(nweTask);

        // 解锁sku库存
        List<WareOrderTaskDetailEntity> taskDetails = taskDetailService.listByTaskId(task.getId());
        for (WareOrderTaskDetailEntity detail : taskDetails) {
            this.getBaseMapper().wareUnlock(detail.getSkuId(), detail.getWareId(), detail.getSkuNum());
            WareOrderTaskDetailEntity newDetail = new WareOrderTaskDetailEntity();
            newDetail.setId(detail.getId());
            newDetail.setLockStatus(2);
            taskDetailService.updateById(newDetail);
        }
    }
}