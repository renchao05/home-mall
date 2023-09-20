package com.renchao.gulimall.controller;

import com.renchao.gulimall.service.MallSearchService;
import com.renchao.gulimall.vo.SearchParam;
import com.renchao.gulimall.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SearchController {

    @Autowired
    private MallSearchService mallSearchService;

    @GetMapping("list.html")
    public String searchList(SearchParam param, Model model) {
        SearchResult result = mallSearchService.search(param);
        model.addAttribute("result", result);
        return "list";
    }
}
