package com.renchao.gulimall.order.vo;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OrderConfirmVo {
    // 全部收货地址，ums_member_receive_address表
    @Setter
    @Getter
    private List<MemberAddressVo> address;

    // 确定的收货地址
    @Setter
    @Getter
    private MemberAddressVo sa;

    //所有选中的购物项
    @Setter
    @Getter
    private List<OrderItemVo> items;

    // 支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】
    // 默认1
    @Setter
    @Getter
    private Integer payType = 1;


    //订单来源[0->PC订单；1->app订单]
    // 功能没有做，这里默认是PC
    @Setter
    @Getter
    private Integer sourceType = 0;


    //发票记录....

    //优惠券信息...
    @Setter
    @Getter
    private Integer integration;

    // 运费
    @Setter
    @Getter
    private BigDecimal fare;

    //防重令牌
    @Setter
    @Getter
    private String orderToken;

    // 总数量
    public Integer getCount() {
        Integer i = 0;
        if (items != null) {
            for (OrderItemVo item : items) {
                i += item.getCount();
            }
        }
        return i;
    }

    // 总价
    public BigDecimal getTotal() {
        BigDecimal sum = new BigDecimal("0");
        if (items != null) {
            for (OrderItemVo item : items) {
                BigDecimal multiply = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));
                sum = sum.add(multiply);
            }
        }
        return sum;
    }

    // 应付价格
    public BigDecimal getPayPrice() {
        if (fare == null) {
            return getTotal();
        }
        return getTotal().add(fare);
    }
}
