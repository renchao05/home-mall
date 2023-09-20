package com.renchao.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.utils.PageUtils;
import com.renchao.gulimall.product.entity.AttrGroupEntity;
import com.renchao.gulimall.product.vo.AttrGroupVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 11:37:42
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    List<AttrGroupVo> listAttrGroupWithAttrByCatId(Long catId);

    List<AttrGroupEntity> listByCatId(Long catalogId);
}

