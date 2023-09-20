package com.renchao.mall.product.vo;

import lombok.Data;

import java.util.List;

@Data
public class Catalog2Vo {

    private Long catalog1Id;
    private List<Catalog3Vo> catalog3List;
    private Long id;
    private String name;

    @Data
    public static class Catalog3Vo {
        private Long catalog2Id;
        private Long id;
        private String name;
    }
}
