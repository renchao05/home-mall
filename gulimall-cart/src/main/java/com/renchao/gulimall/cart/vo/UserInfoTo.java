package com.renchao.gulimall.cart.vo;

import lombok.Data;

@Data
public class UserInfoTo {
    private Long userId;
    private String userKey; //一定封装
    private boolean tempUser = false;
}
