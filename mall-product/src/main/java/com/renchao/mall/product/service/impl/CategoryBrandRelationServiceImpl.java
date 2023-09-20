package com.renchao.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.renchao.mall.product.entity.BrandEntity;
import com.renchao.mall.product.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.mall.product.dao.CategoryBrandRelationDao;
import com.renchao.mall.product.entity.CategoryBrandRelationEntity;
import com.renchao.mall.product.service.CategoryBrandRelationService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


@Transactional
@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private BrandServiceImpl brandService;
    @Autowired
    private CategoryServiceImpl categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<CategoryBrandRelationEntity> wrapper = new LambdaQueryWrapper<>();
        Object brandId = params.get("brandId");
        wrapper.eq(!StringUtils.isEmpty(brandId), CategoryBrandRelationEntity::getBrandId, brandId);
        IPage<CategoryBrandRelationEntity> page =
                this.page(new Query<CategoryBrandRelationEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        // 通过品牌表，添加品牌名
        BrandEntity brand = brandService.getById(categoryBrandRelation.getBrandId());
        categoryBrandRelation.setBrandName(brand.getName());
        // 通过分类，添加分类名
        CategoryEntity category = categoryService.getById(categoryBrandRelation.getCatelogId());
        categoryBrandRelation.setCatelogName(category.getName());
        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        entity.setBrandId(brandId);
        entity.setBrandName(name);
        this.update(
                entity,
                new LambdaUpdateWrapper<CategoryBrandRelationEntity>()
                        .eq(CategoryBrandRelationEntity::getBrandId, brandId)
        );
    }

    @Override
    public List<BrandEntity> brandList(Long catId) {
        LambdaQueryWrapper<CategoryBrandRelationEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryBrandRelationEntity::getCatelogId, catId);
        List<Long> brandIds = this.list(wrapper).stream().map(CategoryBrandRelationEntity::getBrandId).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(brandIds)) {
            return null;
        }
        return brandService.listByIds(brandIds);
    }


}