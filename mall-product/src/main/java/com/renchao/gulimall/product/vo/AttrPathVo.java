package com.renchao.gulimall.product.vo;

import lombok.Data;

@Data
public class AttrPathVo extends AttrVo {
    private Long[] catelogPath;
    private Long attrGroupId;
}
