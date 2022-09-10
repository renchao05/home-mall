package com.renchao.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renchao.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.renchao.gulimall.product.entity.AttrEntity;
import com.renchao.gulimall.product.service.AttrAttrgroupRelationService;
import com.renchao.gulimall.product.service.AttrService;
import com.renchao.gulimall.product.vo.AttrGroupVo;
import org.springframework.beans.BeanUtils;
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

import com.renchao.gulimall.product.dao.AttrGroupDao;
import com.renchao.gulimall.product.entity.AttrGroupEntity;
import com.renchao.gulimall.product.service.AttrGroupService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrAttrgroupRelationService relationService;

    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        Object key = params.get("key");
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(qw -> qw.eq("attr_group_id", key).or().like("attr_group_name", key));
        }
        if (catelogId != 0) {
            wrapper.eq("catelog_id", catelogId);
        }
        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public List<AttrGroupVo> listAttrGroupWithAttrByCatId(Long catId) {
        LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AttrGroupEntity::getCatelogId, catId);
        List<AttrGroupEntity> groups = this.list(wrapper);
        return groups.stream().map(g -> {
            LambdaQueryWrapper<AttrAttrgroupRelationEntity> gWrapper = new LambdaQueryWrapper<>();
            gWrapper.eq(AttrAttrgroupRelationEntity::getAttrGroupId, g.getAttrGroupId());
            List<Long> attrIds = relationService.list(gWrapper).stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(attrIds)) {
                return null;
            }
            List<AttrEntity> attrs = attrService.listByIds(attrIds);
            AttrGroupVo groupVo = new AttrGroupVo();
            BeanUtils.copyProperties(g, groupVo);
            groupVo.setAttrs(attrs);
            return groupVo;
        }).collect(Collectors.toList());
//        return groupVos;
    }

    @Override
    public List<AttrGroupEntity> listByCatId(Long catalogId) {
        LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AttrGroupEntity::getCatelogId, catalogId);
        return this.list(wrapper);
    }

}