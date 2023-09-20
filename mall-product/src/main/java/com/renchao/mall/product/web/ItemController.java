package com.renchao.mall.product.web;

import com.renchao.mall.product.service.SkuInfoService;
import com.renchao.mall.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.ExecutionException;

@Controller
public class ItemController {

    @Autowired
    private SkuInfoService skuInfoService;

    @GetMapping("/{skuId}.html")
    public String ProductItem(@PathVariable Long skuId, Model model) throws ExecutionException, InterruptedException {
        SkuItemVo skuItem = skuInfoService.item(skuId);
        model.addAttribute("skuItem", skuItem);
        return "item";
    }
}
