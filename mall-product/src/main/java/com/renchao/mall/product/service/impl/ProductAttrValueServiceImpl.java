package com.renchao.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renchao.mall.product.entity.AttrEntity;
import com.renchao.mall.product.service.AttrService;
import com.renchao.mall.product.vo.spuinfo.BaseAttrs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.mall.product.dao.ProductAttrValueDao;
import com.renchao.mall.product.entity.ProductAttrValueEntity;
import com.renchao.mall.product.service.ProductAttrValueService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveBatchBaseAttrs(List<BaseAttrs> baseAttrs, Long spuId) {
        if (CollectionUtils.isEmpty(baseAttrs)) {
            return;
        }
        List<ProductAttrValueEntity> attrValues = baseAttrs.stream().map(baseAttr -> {
            Long attrId = baseAttr.getAttrId();
            ProductAttrValueEntity attrValue = new ProductAttrValueEntity();
            attrValue.setSpuId(spuId);
            attrValue.setAttrId(attrId);
            AttrEntity attrEntity = attrService.getById(attrId);
            attrValue.setAttrName(attrEntity.getAttrName());
            attrValue.setAttrValue(baseAttr.getAttrValues());
            attrValue.setQuickShow(baseAttr.getShowDesc());
            return attrValue;
        }).collect(Collectors.toList());
        this.saveBatch(attrValues);
    }

    @Override
    public List<ProductAttrValueEntity> listBySpuId(Long spuId) {
        LambdaQueryWrapper<ProductAttrValueEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductAttrValueEntity::getSpuId, spuId);
        return this.list(wrapper);
    }

    @Transactional
    @Override
    public void updateBySpuId(Long spuId,List<ProductAttrValueEntity> attrEntities) {
        // 先删除
        LambdaQueryWrapper<ProductAttrValueEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(spuId != null, ProductAttrValueEntity::getSpuId, spuId);
        this.remove(wrapper);

        // 再保存
        List<ProductAttrValueEntity> attrs = attrEntities.stream().peek(attr -> attr.setSpuId(spuId)).collect(Collectors.toList());

        this.saveBatch(attrs);

    }

    @Override
    public List<ProductAttrValueEntity> getBySpuIdAndSearch(Long spuId) {
        List<ProductAttrValueEntity> attrList = this.getBySpuId(spuId);
        if (CollectionUtils.isEmpty(attrList)) {
            return null;
        }
        return attrList.stream().filter(attr -> {
            Integer searchType = attrService.getById(attr.getAttrId()).getSearchType();
            return searchType == 1;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductAttrValueEntity> getBySpuId(Long spuId) {
        LambdaQueryWrapper<ProductAttrValueEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductAttrValueEntity::getSpuId, spuId);
        return this.list(wrapper);
    }

}