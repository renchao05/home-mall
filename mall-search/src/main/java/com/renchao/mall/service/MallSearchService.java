package com.renchao.mall.service;

import com.renchao.mall.vo.SearchParam;
import com.renchao.mall.vo.SearchResult;

public interface MallSearchService {
    SearchResult search(SearchParam param);
}
