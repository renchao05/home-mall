/**
  * Copyright 2022 bejson.com 
  */
package com.renchao.mall.product.vo.spuinfo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Bounds {

    private BigDecimal buyBounds;
    private BigDecimal growBounds;

}