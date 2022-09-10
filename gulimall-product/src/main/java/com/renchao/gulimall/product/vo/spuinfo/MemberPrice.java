/**
  * Copyright 2022 bejson.com 
  */
package com.renchao.gulimall.product.vo.spuinfo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MemberPrice {

    private Long id;
    private String name;
    private BigDecimal price;
}