package com.renchao.gulimall.ware.feign;

import com.renchao.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient("gulimall-order") // 使用openfeign
public interface OrderFeignService {

    @RequestMapping("order/order/getOrderInfo")
    R getOrderInfo(@RequestParam("id") Long id);

}
