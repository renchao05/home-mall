package com.renchao.mall.order.vo;

import lombok.Data;

@Data
public class PayVo {
    private String outTradeNo; // 商户订单号 必填
    private String subject; // 订单名称 必填
    private String totalAmount;  // 付款金额 必填
    private String body; // 商品描述 可空
}
