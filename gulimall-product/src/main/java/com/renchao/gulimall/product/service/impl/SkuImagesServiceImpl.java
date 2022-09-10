package com.renchao.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renchao.gulimall.product.vo.spuinfo.Images;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.gulimall.product.dao.SkuImagesDao;
import com.renchao.gulimall.product.entity.SkuImagesEntity;
import com.renchao.gulimall.product.service.SkuImagesService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


@Service("skuImagesService")
public class SkuImagesServiceImpl extends ServiceImpl<SkuImagesDao, SkuImagesEntity> implements SkuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuImagesEntity> page = this.page(
                new Query<SkuImagesEntity>().getPage(params),
                new QueryWrapper<SkuImagesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveBatchImages(List<Images> images, Long skuId) {
        if (CollectionUtils.isEmpty(images)) {
            return;
        }
        List<SkuImagesEntity> skuImagesEntities = images.stream()
                .filter(img -> !StringUtils.isEmpty(img.getImgUrl()))
                .map(img -> {
                    SkuImagesEntity skuImages = new SkuImagesEntity();
                    skuImages.setSkuId(skuId);
                    skuImages.setImgUrl(img.getImgUrl());
                    skuImages.setDefaultImg(img.getDefaultImg());
                    return skuImages;
                }).collect(Collectors.toList());
        this.saveBatch(skuImagesEntities);
    }

    /**
     * 获取默认图片
     */
    @Override
    public SkuImagesEntity getBySkuId(Long skuId) {
        List<SkuImagesEntity> imagesList = this.listBySkuId(skuId);
        if (CollectionUtils.isEmpty(imagesList)) {
            return null;
        }
        List<SkuImagesEntity> imagesEntities = imagesList.stream()
                .filter(images -> images.getDefaultImg() == 1)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(imagesEntities)) {
            return null;
        }
        return imagesEntities.get(0);
    }

    @Override
    public List<SkuImagesEntity> listBySkuId(Long skuId) {
        LambdaQueryWrapper<SkuImagesEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuImagesEntity::getSkuId, skuId);
        return this.list(wrapper);
    }

}