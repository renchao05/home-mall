package com.renchao.gulimall.order.feign;

import com.renchao.common.to.OrderTo;
import com.renchao.common.to.WareLockTo;
import com.renchao.common.utils.R;
import com.renchao.gulimall.order.vo.MemberAddressVo;
import com.renchao.gulimall.order.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
@FeignClient("gulimall-ware")
public interface WareFeignService {

    @PostMapping("ware/wareinfo/getFare")
    String getFare(@RequestBody MemberAddressVo address);

    @PostMapping("ware/waresku/hasStock")
    Map<Long, Boolean> hasStock(@RequestBody List<Long> ids);

    @PostMapping("ware/waresku/wareLock")
    R wareLock(@RequestBody OrderTo orderTo);
}
