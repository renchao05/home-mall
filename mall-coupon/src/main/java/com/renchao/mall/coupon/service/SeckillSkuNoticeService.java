package com.renchao.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.utils.PageUtils;
import com.renchao.mall.coupon.entity.SeckillSkuNoticeEntity;

import java.util.Map;

/**
 * 秒杀商品通知订阅
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 16:48:37
 */
public interface SeckillSkuNoticeService extends IService<SeckillSkuNoticeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

