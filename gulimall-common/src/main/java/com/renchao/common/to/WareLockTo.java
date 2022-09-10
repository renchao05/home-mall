package com.renchao.common.to;

import lombok.Data;

@Data
public class WareLockTo {
    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品sku编号
     */
    private Long skuId;
    /**
     * 商品sku名字
     */
    private String skuName;
    /**
     * 商品购买的数量
     */
    private Integer skuQuantity;
}
