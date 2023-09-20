package com.renchao.gulimall.auth.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserRegisterVo {
    @Pattern(regexp = "^\\w{6,18}$",message = "用户名必须是6-18位字符！")
    @NotNull(message = "用户名不能为空！")
    private String username;

    @Length(min = 8,max = 18,message = "密码必须是8-18位字符")
    @NotNull(message = "密码不能为空！")
    private String password;

    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "手机号格式不正确！")
    @NotNull(message = "手机号不能为空！")
    private String mobile;

    @Pattern(regexp = "^\\d{6}$",message = "验证码必须是6位数字！")
    @NotNull(message = "验证码不能为空！")
    private String code;
}
