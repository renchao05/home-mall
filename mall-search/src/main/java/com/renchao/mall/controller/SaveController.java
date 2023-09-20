package com.renchao.mall.controller;

import com.renchao.common.to.es.SkuEsModel;
import com.renchao.common.utils.R;
import com.renchao.mall.service.ProductSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search/save")
public class SaveController {

    @Autowired
    private ProductSaveService productSaveService;

    @PostMapping("/product")
    public R saveProduct(@RequestBody List<SkuEsModel> models) {
        try {
            productSaveService.saveProduct(models);
        } catch (IOException e) {
            return R.error(444, "商品上架异常");
        }
        return R.ok();
    }

}
