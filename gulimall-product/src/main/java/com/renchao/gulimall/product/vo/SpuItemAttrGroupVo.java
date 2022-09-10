package com.renchao.gulimall.product.vo;

import com.renchao.gulimall.product.vo.spuinfo.Attr;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class SpuItemAttrGroupVo {
    private String groupName;
    private List<Attr> attrs;
}
