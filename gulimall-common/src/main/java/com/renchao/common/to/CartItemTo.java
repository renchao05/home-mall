package com.renchao.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartItemTo {
    private String title;   // 标题
    private String image;   // 图片
    private List<String> skuAttr;   // 销售属性
    private BigDecimal price;   // 价格
}
