package com.renchao.mall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.mall.product.dao.SpuImagesDao;
import com.renchao.mall.product.entity.SpuImagesEntity;
import com.renchao.mall.product.service.SpuImagesService;
import org.springframework.util.CollectionUtils;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveBatchSpuImages(List<String> spuImages, Long spuId) {
        if (CollectionUtils.isEmpty(spuImages)) {
            return;
        }
        List<SpuImagesEntity> imagesEntities = spuImages.stream().map(img -> {
            SpuImagesEntity images = new SpuImagesEntity();
            images.setSpuId(spuId);
            images.setImgUrl(img);
            return images;
        }).collect(Collectors.toList());
        this.saveBatch(imagesEntities);
    }

}