package com.renchao.gulimall.coupon.dao;

import com.renchao.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 16:48:37
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
