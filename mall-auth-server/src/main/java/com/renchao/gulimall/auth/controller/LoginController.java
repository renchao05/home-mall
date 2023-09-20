package com.renchao.gulimall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.renchao.common.constant.AuthConstant;
import com.renchao.common.to.UserTo;
import com.renchao.common.utils.R;
import com.renchao.gulimall.auth.feign.MemberFeignService;
import com.renchao.gulimall.auth.feign.ThirdPartyFeignService;
import com.renchao.gulimall.auth.vo.UserLoginVo;
import com.renchao.gulimall.auth.vo.UserRegisterVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ThirdPartyFeignService thirdPartyFeignService;

    @Autowired
    private MemberFeignService memberFeignService;

    @ResponseBody
    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone) {
        // 手机号校验
        if (!phone.matches("^[1][3578][0-9]{9}$")) {
            return R.error(333, "手机号码格式错误！");
        }
        String codeKey = AuthConstant.SMS_CODE_KEY_PREFIX + phone;
        // 防刷校验
        String code = redisTemplate.opsForValue().get(codeKey);
        if (!StringUtils.isEmpty(code)) {
            // 获取过期时间
            Long expire = redisTemplate.opsForValue().getOperations().getExpire(codeKey);
            if (expire != null && expire > 0) {
                return R.error(444, String.valueOf(expire));
            }
        }
        // 生成验证码，保存验证码，验证码
        code = String.valueOf(Math.random()).substring(2, 8);
        String codeValue = code + "_" + System.currentTimeMillis();
        redisTemplate.opsForValue().set(codeKey, codeValue, 60, TimeUnit.SECONDS);
        thirdPartyFeignService.sendSms(phone, code);
//        System.out.println("验证码：" + code);
        return R.ok();
    }


    @PostMapping("/register")
    public String register(@Valid UserRegisterVo vo, RedirectAttributes attributes) {
        // 校验验证码mobile
        String code = redisTemplate.opsForValue().get(AuthConstant.SMS_CODE_KEY_PREFIX + vo.getMobile());
        if (StringUtils.isEmpty(code) || !vo.getCode().equals(code.split("_")[0])) {
            Map<String, String> errors = new HashMap<>();
            errors.put("code", "验证码不正确！");
            attributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.renchao05.top/reg.html";
        }

        // 校验成功后删除验证码
        redisTemplate.delete(AuthConstant.SMS_CODE_KEY_PREFIX + vo.getMobile());

        // 调用会员服务 mall-member 注册
        R r = memberFeignService.register(vo);
        if (!r.get("code").equals(0)) {
            attributes.addFlashAttribute("msg", r.get("msg"));
            return "redirect:http://auth.renchao05.top/reg.html";
        }
        return "redirect:http://auth.renchao05.top/login.html";
    }


    /**
     * 登录
     */
    @PostMapping("/login")
    public String login(@Valid UserLoginVo vo,
                        RedirectAttributes attributes,
                        HttpSession session,
                        HttpServletRequest request) {
        R r = memberFeignService.login(vo);
        if (!r.get("code").equals(0)) {
            attributes.addFlashAttribute("msg", r.get("msg"));
            return "redirect:http://auth.renchao05.top/login.html";
        }
        Object o = r.get(AuthConstant.USER);
        UserTo userTo = JSON.parseObject(JSON.toJSONString(o), UserTo.class);
        session.setAttribute(AuthConstant.USER, userTo);
        return "redirect:http://renchao05.top/";
    }


    /**
     * 登录页
     */
    @GetMapping("/login.html")
    public String loginPage(HttpSession session) {
        if (session.getAttribute(AuthConstant.USER) == null) {
            return "login";
        }
        return "redirect:http://renchao05.top/";
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:http://renchao05.top/";
    }
}
