package com.renchao.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.utils.PageUtils;
import com.renchao.mall.product.entity.AttrAttrgroupRelationEntity;
import com.renchao.mall.product.entity.AttrEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 11:37:42
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    AttrAttrgroupRelationEntity queryByAttrId(Long attrId);

    List<AttrEntity> attrListByGroupId(Long groupId);

    PageUtils noAttrList(Map<String, Object> params, Long groupId);

    void removeByRelations(List<AttrAttrgroupRelationEntity> relations);

    List<Long> attrIdListByGroupId(Long attrGroupId);
}

