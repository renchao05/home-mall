package com.renchao.gulimall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.renchao.common.constant.SeckillConstant;
import com.renchao.common.utils.R;
import com.renchao.gulimall.seckill.feign.CouponFeignService;
import com.renchao.gulimall.seckill.feign.ProductFeignService;
import com.renchao.gulimall.seckill.service.SeckillService;
import com.renchao.gulimall.seckill.to.SecKillSkuRedisTo;
import com.renchao.gulimall.seckill.vo.SeckillSessionVo;
import com.renchao.gulimall.seckill.vo.SeckillSkuVo;
import com.renchao.gulimall.seckill.vo.SkuInfoVo;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void uploadSeckillSkuLatest3Days() {
        R r = couponFeignService.getLast3DaysSession();
        if (r.getCode() != 0) {
            return;
        }
        List<SeckillSessionVo> sessionVos = r.getDataList("data", SeckillSessionVo.class);
        if (CollectionUtils.isEmpty(sessionVos)) {
            return;
        }
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(SeckillConstant.SKUKILL_CACHE_PREFIX);
        // 按照开始时间排序，方便后面获取使用
        List<SeckillSessionVo> sortedVos = sessionVos.stream()
                .sorted((d1, d2) -> (int) (d1.getStartTime().getTime() - d2.getStartTime().getTime()))
                .collect(Collectors.toList());
        // 缓存到 Redis
        for (SeckillSessionVo vo : sortedVos) {
            long startTime = vo.getStartTime().getTime();
            long endTime = vo.getEndTime().getTime();
            String key = SeckillConstant.SESSIONS_CACHE_PREFIX + startTime + "_" + endTime;
            // 如果活动已经缓存过了，就不用再缓存了
            if (!Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
                // 缓存活动信息到Redis
                saveSessionInfos(vo, key);

                // 缓存关联商品信息到Redis
                saveSessionSkuInfos(vo, ops);
            }
        }
    }


    /**
     * 缓存关联商品信息到Redis
     */
    private void saveSessionSkuInfos(SeckillSessionVo sessionVo, BoundHashOperations<String, Object, Object> ops) {
        for (SeckillSkuVo skuVo : sessionVo.getRelationSkus()) {
            SecKillSkuRedisTo skuRedisTo = new SecKillSkuRedisTo();
            // sku基本信息
            R r = productFeignService.getSkuInfo(skuVo.getSkuId());
            if (r.getCode() != 0) {
                continue;
            }
            SkuInfoVo skuInfo = r.getData("skuInfo", SkuInfoVo.class);
            skuRedisTo.setSkuInfo(skuInfo);

            // sku秒杀信息
            BeanUtils.copyProperties(skuVo, skuRedisTo);

            // 秒杀时间信息
            skuRedisTo.setStartTime(sessionVo.getStartTime().getTime());
            skuRedisTo.setEndTime(sessionVo.getEndTime().getTime());

            // 随机码
            String token = UUID.randomUUID().toString().replace("-", "");
            skuRedisTo.setRandomCode(token);

            // 分布式信号量
            RSemaphore semaphore = redissonClient.getSemaphore(SeckillConstant.SKU_STOCK_SEMAPHORE + token);
            semaphore.trySetPermits(skuVo.getSeckillCount());
            ops.put(skuRedisTo.getPromotionSessionId() + "_" + skuRedisTo.getSkuId(), JSON.toJSONString(skuRedisTo));
        }
    }

    /**
     * 缓存活动信息到Redis
     */
    private void saveSessionInfos(SeckillSessionVo sessionVo, String key) {
        List<String> skuIds = sessionVo.getRelationSkus().stream()
                .map(sku -> sku.getPromotionSessionId() + "_" + sku.getSkuId())
                .collect(Collectors.toList());
        redisTemplate.opsForList().leftPushAll(key, skuIds);
    }


    /**
     * 获取当前时间秒杀商品信息
     * @return
     */
    @Override
    public List<SecKillSkuRedisTo> getCurrentSeckillSkus() {
        // 获取所有活动的key
        Set<String> keys = redisTemplate.keys(SeckillConstant.SESSIONS_CACHE_PREFIX + "*");
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(SeckillConstant.SKUKILL_CACHE_PREFIX);
        long now = new Date().getTime();
        // 遍历每个场次
        for (String key : keys) {
            String[] time = key.replace(SeckillConstant.SESSIONS_CACHE_PREFIX, "").split("_");
            long start = Long.parseLong(time[0]);
            long end = Long.parseLong(time[1]);
            // 如果当前时间在活动的时间区间内，则返回该场次的商品信息
            if (now >= start && now < end) {
                // 查询该场次的所有商品sku。格式是："场次_sku"
                List<String> skuIds = redisTemplate.opsForList().range(key, 0, -1);
                if (CollectionUtils.isEmpty(skuIds)) {
                    return null;
                }
                // 批量获取商品信息
                List<String> skusJson = ops.multiGet(skuIds);
                if (skusJson == null) {
                    return null;
                }
                // 将json字符串转换为对象，然后返回
                return skusJson.stream()
                        .map(s -> JSON.parseObject(s, SecKillSkuRedisTo.class))
                        .collect(Collectors.toList());
            }
        }
        return null;
    }

    @Override
    public SecKillSkuRedisTo getSeckillSkuInfo(String skuId) {
        // 查找所有参加秒杀商品的key
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(SeckillConstant.SKUKILL_CACHE_PREFIX);
        Set<String> keys = ops.keys();
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        for (String key : keys) {
            // key: "场次_skuId"
            if (key.split("_")[1].equals(skuId)) {
                String json = ops.get(key);
                SecKillSkuRedisTo skuTo = JSON.parseObject(json, SecKillSkuRedisTo.class);
                long now = new Date().getTime();
                assert skuTo != null;
                // 当前时间超过结束时间
                if (now > skuTo.getEndTime()) {
                    return null;
                }
                // 如果当前不是活动时间，清除随机码
                if (now < skuTo.getStartTime()) {
                    skuTo.setRandomCode("");
                }
                return skuTo;
            }
        }
        return null;
    }
}
