package com.renchao.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.utils.PageUtils;
import com.renchao.gulimall.product.entity.AttrEntity;
import com.renchao.gulimall.product.vo.AttrPathVo;
import com.renchao.gulimall.product.vo.AttrRespVo;
import com.renchao.gulimall.product.vo.AttrVo;

import java.util.Map;

/**
 * 商品属性
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 11:37:42
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryPage(Map<String, Object> params,String attrType, Long catelogId);

    AttrPathVo getAttr(Long attrId);

    void updateDetails(AttrRespVo attr);
}

