package com.renchao.gulimall.thirdparty.controller;

import com.renchao.common.utils.R;
import com.renchao.gulimall.thirdparty.component.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SmsSendController {

    @Autowired
    private SmsComponent smsComponent;

    @GetMapping("/send")
    public R sendSms(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        smsComponent.sendSmsCode(phone,code);
        return R.ok();
    }

}
