package com.renchao.mall.ware.dao;

import com.renchao.mall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品库存
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 17:48:45
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    void addStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);

    Integer selectStockBySkuId(@Param("skuId") Long skuId);

    Integer wareLock(@Param("id") Long id, @Param("num") Integer num);

    Integer wareUnlock(@Param("skuId") Long skuId,@Param("wareId") Long wareId, @Param("num") Integer num);




}
