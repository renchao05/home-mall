package com.renchao.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.utils.PageUtils;
import com.renchao.mall.product.entity.SpuInfoDescEntity;

import java.util.List;
import java.util.Map;

/**
 * spu信息介绍
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 11:37:42
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuDecript(List<String> spuDecript,Long spuId);
}

