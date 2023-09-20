package com.renchao.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.utils.PageUtils;
import com.renchao.mall.product.entity.SkuSaleAttrValueEntity;
import com.renchao.mall.product.vo.spuinfo.Attr;

import java.util.List;
import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 11:37:42
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveBatchAttr(List<Attr> attr, Long skuId);

    List<SkuSaleAttrValueEntity> listBySkuIds(List<Long> skuIds);
}

