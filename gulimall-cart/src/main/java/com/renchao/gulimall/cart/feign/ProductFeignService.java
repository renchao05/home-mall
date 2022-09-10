package com.renchao.gulimall.cart.feign;

import com.renchao.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient("gulimall-product")
public interface ProductFeignService {

    @GetMapping("product/skuinfo/getCarItem/{skuId}")
    R getCarItem(@PathVariable("skuId") Long skuId);

    @GetMapping("product/skuinfo/getPrice/{skuId}")
    String getPrice(@PathVariable("skuId") Long skuId);
}
