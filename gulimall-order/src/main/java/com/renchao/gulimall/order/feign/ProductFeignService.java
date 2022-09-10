package com.renchao.gulimall.order.feign;

import com.renchao.common.to.SpuInfoTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
@FeignClient("gulimall-product")
public interface ProductFeignService {

    @PostMapping("product/spuinfo/getSpuMap")
    Map<Long, SpuInfoTo> getSpuMapBySkuIds(@RequestBody List<Long> skuIds);

}
