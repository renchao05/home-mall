package com.renchao.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.utils.PageUtils;
import com.renchao.mall.coupon.entity.CouponEntity;

import java.util.Map;

/**
 * 优惠券信息
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 16:48:37
 */
public interface CouponService extends IService<CouponEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

