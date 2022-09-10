package com.renchao.gulimall.product.feign;

import com.renchao.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("gulimall-seckill")
public interface SeckillFeignService {

    @GetMapping("/seckill/sku/{skuId}")
    R getSeckillSkuInfo(@PathVariable("skuId") String skuId);
}
