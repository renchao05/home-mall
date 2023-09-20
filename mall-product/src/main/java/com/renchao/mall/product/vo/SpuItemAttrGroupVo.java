package com.renchao.mall.product.vo;

import com.renchao.mall.product.vo.spuinfo.Attr;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class SpuItemAttrGroupVo {
    private String groupName;
    private List<Attr> attrs;
}
