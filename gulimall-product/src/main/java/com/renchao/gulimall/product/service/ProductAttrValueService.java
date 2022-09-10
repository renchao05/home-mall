package com.renchao.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.utils.PageUtils;
import com.renchao.gulimall.product.entity.ProductAttrValueEntity;
import com.renchao.gulimall.product.vo.spuinfo.BaseAttrs;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 11:37:42
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveBatchBaseAttrs(List<BaseAttrs> baseAttrs, Long spuId);

    List<ProductAttrValueEntity> listBySpuId(Long spuId);

    void updateBySpuId(Long spuId,List<ProductAttrValueEntity> attrEntities);

    List<ProductAttrValueEntity> getBySpuIdAndSearch(Long spuId);

    List<ProductAttrValueEntity> getBySpuId(Long spuId);
}

