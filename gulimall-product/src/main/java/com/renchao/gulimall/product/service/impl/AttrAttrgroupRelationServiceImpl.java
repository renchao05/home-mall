package com.renchao.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renchao.gulimall.product.entity.AttrEntity;
import com.renchao.gulimall.product.entity.AttrGroupEntity;
import com.renchao.gulimall.product.service.AttrGroupService;
import com.renchao.gulimall.product.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.renchao.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.renchao.gulimall.product.service.AttrAttrgroupRelationService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrGroupService attrGroupService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public AttrAttrgroupRelationEntity queryByAttrId(Long attrId) {
        return this.baseMapper.selectOne(
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                .eq(AttrAttrgroupRelationEntity::getAttrId, attrId)
        );
    }

    @Override
    public List<AttrEntity> attrListByGroupId(Long groupId) {
        List<Long> attrIds = attrIdListByGroupId(groupId);
        if (attrIds.size() == 0) {
            return null;
        }
        return attrService.listByIds(attrIds);
    }

    @Override
    public PageUtils noAttrList(Map<String, Object> params,Long groupId) {
        AttrGroupEntity group = attrGroupService.getById(groupId);
        List<Long> attrIds = this.list().stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<>();
        // 过滤销售属性
        wrapper.ne(AttrEntity::getAttrType, 0);
        // 当前分类，和没有被关联的
        wrapper.eq(AttrEntity::getCatelogId, group.getCatelogId()).notIn(AttrEntity::getAttrId, attrIds);
        // 搜索
        Object key = params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(w -> w.eq(AttrEntity::getAttrId, key).like(AttrEntity::getAttrName, key));
        }

        IPage<AttrEntity> page = attrService.page(new Query<AttrEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public void removeByRelations(List<AttrAttrgroupRelationEntity> relations) {
        baseMapper.deleteBatchRelations(relations);
    }

    @Override
    public List<Long> attrIdListByGroupId(Long groupId) {
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AttrAttrgroupRelationEntity::getAttrGroupId, groupId);
        List<AttrAttrgroupRelationEntity> list = this.list(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
    }


}