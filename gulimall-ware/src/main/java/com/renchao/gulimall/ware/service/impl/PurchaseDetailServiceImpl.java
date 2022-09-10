package com.renchao.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.gulimall.ware.dao.PurchaseDetailDao;
import com.renchao.gulimall.ware.entity.PurchaseDetailEntity;
import com.renchao.gulimall.ware.service.PurchaseDetailService;
import org.springframework.util.StringUtils;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Object key = params.get("key");
        Object status = params.get("status");
        Object wareId = params.get("wareId");
        LambdaQueryWrapper<PurchaseDetailEntity> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(w -> w.eq(PurchaseDetailEntity::getPurchaseId, key).or()
                    .eq(PurchaseDetailEntity::getSkuId, key));
        }
        wrapper.eq(!StringUtils.isEmpty(status), PurchaseDetailEntity::getStatus, status);
        wrapper.eq(!StringUtils.isEmpty(wareId), PurchaseDetailEntity::getWareId, wareId);
        IPage<PurchaseDetailEntity> page = this.page(new Query<PurchaseDetailEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

}