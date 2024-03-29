package com.renchao.mall.seckill.service;

import com.renchao.mall.seckill.to.SecKillSkuRedisTo;

import java.util.List;

public interface SeckillService {
    void uploadSeckillSkuLatest3Days();

    List<SecKillSkuRedisTo> getCurrentSeckillSkus();

    SecKillSkuRedisTo getSeckillSkuInfo(String skuId);
}
