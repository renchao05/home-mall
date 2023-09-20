package com.renchao.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.utils.PageUtils;
import com.renchao.gulimall.product.entity.SkuImagesEntity;
import com.renchao.gulimall.product.vo.spuinfo.Images;

import java.util.List;
import java.util.Map;

/**
 * sku图片
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 11:37:42
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveBatchImages(List<Images> images, Long skuId);

    SkuImagesEntity getBySkuId(Long skuId);

    List<SkuImagesEntity> listBySkuId(Long skuId);
}

