package com.renchao.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renchao.mall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.mall.product.dao.BrandDao;
import com.renchao.mall.product.entity.BrandEntity;
import com.renchao.mall.product.service.BrandService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Transactional
@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<BrandEntity> wrapper = new LambdaQueryWrapper<>();
        Object key = params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.eq(BrandEntity::getBrandId, key).or().like(BrandEntity::getName, key);
        }
        IPage<BrandEntity> page = this.page(new Query<BrandEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    public void updateDetails(BrandEntity brand) {
        this.updateById(brand);
        String name = brand.getName();
        Long brandId = brand.getBrandId();
        if (!StringUtils.isEmpty(name)) {
            categoryBrandRelationService.updateBrand(brandId,name);
        }

        // TODO 更新其他关联
    }

}