package com.renchao.gulimall.product.feign;


import com.renchao.common.utils.R;
import com.renchao.gulimall.product.feign.fallback.WareFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Service
@FeignClient(value = "mall-ware",fallback = WareFeignServiceFallback.class)
public interface WareFeignService {

    @GetMapping("/ware/waresku/getStock")
    R getStock(@RequestParam("skuId") Long skuId);
}
