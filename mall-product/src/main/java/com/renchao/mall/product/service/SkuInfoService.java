package com.renchao.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.to.CartItemTo;
import com.renchao.common.utils.PageUtils;
import com.renchao.mall.product.entity.SkuInfoEntity;
import com.renchao.mall.product.vo.SkuItemVo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * sku信息
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 11:37:42
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageWrapper(Map<String, Object> params);

    List<SkuInfoEntity> listBySpuId(Long spuId);

    SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException;

    CartItemTo getCarItem(Long skuId);
}

