package com.renchao.gulimall.service;

import com.renchao.gulimall.vo.SearchParam;
import com.renchao.gulimall.vo.SearchResult;

public interface MallSearchService {
    SearchResult search(SearchParam param);
}
