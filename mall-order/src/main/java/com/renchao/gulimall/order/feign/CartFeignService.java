package com.renchao.gulimall.order.feign;

import com.renchao.gulimall.order.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@FeignClient("mall-cart")
public interface CartFeignService {

    @GetMapping("/getCheckedCart")
    List<OrderItemVo> getCheckedCart();
}
