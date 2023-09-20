package com.renchao.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.utils.PageUtils;
import com.renchao.mall.product.entity.BrandEntity;
import com.renchao.mall.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 11:37:42
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    void updateBrand(Long brandId, String name);

    List<BrandEntity> brandList(Long catId);
}

