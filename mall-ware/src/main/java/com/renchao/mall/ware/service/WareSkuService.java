package com.renchao.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.to.OrderTo;
import com.renchao.common.utils.PageUtils;
import com.renchao.mall.ware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 17:48:45
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageWrapper(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    Integer hasStock(Long skuId);

    Map<Long, Boolean> hasStock(List<Long> ids);

    void wareLock(OrderTo orderTo);

    List<WareSkuEntity> listBySkuId(Long skuId);

    void unlockStock(Long orderId);
}

