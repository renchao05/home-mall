package com.renchao.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.to.SpuInfoTo;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.R;
import com.renchao.mall.product.entity.SpuInfoEntity;
import com.renchao.mall.product.vo.spuinfo.SpuInfoVo;

import java.util.List;
import java.util.Map;

/**
 * spu信息
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 11:37:42
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuInfoVo spuInfo);

    PageUtils queryPageWrapper(Map<String, Object> params);

    R spuUp(Long spuId);

    Map<Long, SpuInfoTo> getSpuMapBySkuIds(List<Long> skuIds);
}

