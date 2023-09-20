package com.renchao.gulimall.seckill.feign;

import com.renchao.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient("mall-coupon")
public interface CouponFeignService {

    @GetMapping("coupon/seckillsession/last3DaysSession")
    R getLast3DaysSession();
}
