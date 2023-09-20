package com.renchao.mall.ware.vo;

import lombok.Data;

@Data
public class PurchaseDoneItemsVo {
    private Long itemId;
    private Integer status;
    private String reason;
}
