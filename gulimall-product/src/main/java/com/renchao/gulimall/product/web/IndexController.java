package com.renchao.gulimall.product.web;

import com.renchao.gulimall.product.entity.CategoryEntity;
import com.renchao.gulimall.product.service.CategoryService;
import com.renchao.gulimall.product.vo.Catalog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/","/index.html"})
    public String indexPage(Model model) {
        List<CategoryEntity> categoryList = categoryService.getLevel1CategoryList();
        model.addAttribute("categoryList",categoryList);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/json/catalog.json")
    public Map<Long, List<Catalog2Vo>> getCatalogJson() {
        return categoryService.getCatalogJson();
    }
}
