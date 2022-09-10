package com.renchao.gulimall.auth.feign;

import com.renchao.common.utils.R;
import com.renchao.gulimall.auth.vo.UserLoginVo;
import com.renchao.gulimall.auth.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient("gulimall-member")
public interface MemberFeignService {

    @PostMapping("member/member/register")
    R register(@RequestBody UserRegisterVo vo);

    @PostMapping("member/member/login")
    R login(@RequestBody UserLoginVo vo);
}
