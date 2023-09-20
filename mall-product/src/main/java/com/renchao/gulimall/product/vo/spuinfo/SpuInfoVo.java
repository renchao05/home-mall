/**
  * Copyright 2022 bejson.com 
  */
package com.renchao.gulimall.product.vo.spuinfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SpuInfoVo {
    private String spuName;
    private String spuDescription;
    private Long catalogId;
    private Long brandId;
    private BigDecimal weight;
    private Integer publishStatus;
    private List<String> decript;
    private List<String> images;
    private Bounds bounds;
    private List<BaseAttrs> baseAttrs;
    private List<Skus> skus;
}