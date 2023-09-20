package com.renchao.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renchao.gulimall.product.vo.spuinfo.Attr;
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

import com.renchao.gulimall.product.dao.SkuSaleAttrValueDao;
import com.renchao.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.renchao.gulimall.product.service.SkuSaleAttrValueService;
import org.springframework.util.CollectionUtils;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveBatchAttr(List<Attr> attr, Long skuId) {
        if (CollectionUtils.isEmpty(attr)) {
            return;
        }
        List<SkuSaleAttrValueEntity> skuAttrs = attr.stream().map(a -> {
            SkuSaleAttrValueEntity skuAttr = new SkuSaleAttrValueEntity();
            BeanUtils.copyProperties(a, skuAttr);
            skuAttr.setSkuId(skuId);
            return skuAttr;
        }).collect(Collectors.toList());
        this.saveBatch(skuAttrs);
    }

    @Override
    public List<SkuSaleAttrValueEntity> listBySkuIds(List<Long> skuIds) {
        LambdaQueryWrapper<SkuSaleAttrValueEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SkuSaleAttrValueEntity::getSkuId, skuIds);
        return list(wrapper);
    }

}