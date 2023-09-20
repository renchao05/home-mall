package com.renchao.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.utils.PageUtils;
import com.renchao.mall.product.entity.CategoryEntity;
import com.renchao.mall.product.vo.Catalog2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 11:37:42
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeMenuByIds(List<Long> asList);

    Long[] findCatelogPath(Long catelogId);

    void updateDetails(CategoryEntity category);

    List<CategoryEntity> getLevel1CategoryList();

    Map<Long, List<Catalog2Vo>> getCatalogJson();
}

