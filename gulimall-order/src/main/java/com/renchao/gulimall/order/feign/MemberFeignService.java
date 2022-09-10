package com.renchao.gulimall.order.feign;

import com.renchao.gulimall.order.vo.MemberAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@FeignClient("gulimall-member")
public interface MemberFeignService {

    @GetMapping("member/memberreceiveaddress/{memberId}/getAddress")
    List<MemberAddressVo> getAddress(@PathVariable("memberId") Long memberId);
}
