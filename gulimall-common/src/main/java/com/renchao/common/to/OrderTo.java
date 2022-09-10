package com.renchao.common.to;

import lombok.Data;

import java.util.List;

@Data
public class OrderTo {
    /**
     * order_id
     */
    private Long orderId;

    private List<WareLockTo> wareLockTos;
    /**
     * 收货人
     */
    private String consignee;
    /**
     * 收货人电话
     */
    private String consigneeTel;
    /**
     * 配送地址
     */
    private String deliveryAddress;
    /**
     * 订单备注
     */
    private String orderComment;
    /**
     * 付款方式【 1:在线付款 2:货到付款】
     */
    private Integer paymentWay;
    /**
     * 订单描述
     */
    private String orderBody;

    /**
     * 订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】
     */
    private Integer status;


}
