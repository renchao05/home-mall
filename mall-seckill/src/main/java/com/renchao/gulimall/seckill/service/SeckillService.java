package com.renchao.gulimall.seckill.service;

import com.renchao.gulimall.seckill.to.SecKillSkuRedisTo;

import java.util.List;

public interface SeckillService {
    void uploadSeckillSkuLatest3Days();

    List<SecKillSkuRedisTo> getCurrentSeckillSkus();

    SecKillSkuRedisTo getSeckillSkuInfo(String skuId);
}
