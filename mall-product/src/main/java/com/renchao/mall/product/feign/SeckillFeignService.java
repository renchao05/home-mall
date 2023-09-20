package com.renchao.mall.product.feign;

import com.renchao.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("mall-seckill")
public interface SeckillFeignService {

    @GetMapping("/seckill/sku/{skuId}")
    R getSeckillSkuInfo(@PathVariable("skuId") String skuId);
}
