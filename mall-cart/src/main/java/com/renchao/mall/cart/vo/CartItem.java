package com.renchao.mall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

public class CartItem {
    private Long skuId;
    private Boolean check = false;   // 是否选中状态
    private String title;   // 标题
    private String image;   // 图片
    private List<String> skuAttr;   // 销售属性
    private BigDecimal price;   // 价格
    private Integer count;  // 数量
//    private BigDecimal totalPrice;  // 总价

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getSkuAttr() {
        return skuAttr;
    }

    public void setSkuAttr(List<String> skuAttr) {
        this.skuAttr = skuAttr;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getTotalPrice() {
        return price.multiply(new BigDecimal(count.toString()));
    }

}
