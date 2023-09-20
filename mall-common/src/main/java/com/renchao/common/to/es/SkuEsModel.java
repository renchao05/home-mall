package com.renchao.common.to.es;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuEsModel {

    private Long skuId;

    private Long spuId;

    private String skuTitle;

    private BigDecimal skuPrice;   // price

    private String skuImg;  // SkuImagesEntity => imgUrl

    private Long saleCount;

    private Boolean hasStock;   // 查询库存，然后判断

    private Long hotScore;  // 热度值，只模拟使用点击率更新热度。点击率增加到一定程度才更新热度值

    private Long brandId;

    private String brandName;   // BrandEntity => name

    private String brandImg;    // BrandEntity => logo

    private Long catalogId;

    private String catalogName; // CategoryEntity => name

    // 通过spu查询 ProductAttrValueEntity,通过AttrEntity筛选可检索的
    private List<Attrs> attrs;



    @Data
    public static class Attrs {
        private Long attrId;
        private String attrName;
        private String attrValue;
    }

}
