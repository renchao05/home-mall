package com.renchao.mall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    private BigDecimal reduce = new BigDecimal("0.00");//减免价格

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Integer getCountNum() {
        return items.stream().mapToInt(CartItem::getCount).sum();
    }


    public Integer getCountType() {
        return items.size();
    }

    public BigDecimal getTotalAmount() {
        BigDecimal totalAmount = new BigDecimal("0");
        for (CartItem item : items) {
            if (item.getCheck()) {
                totalAmount = totalAmount.add(item.getTotalPrice());
            }
        }
        totalAmount = totalAmount.subtract(reduce);
        return totalAmount;
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}
