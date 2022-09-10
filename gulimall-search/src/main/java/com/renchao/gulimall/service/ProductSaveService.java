package com.renchao.gulimall.service;

import com.renchao.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface ProductSaveService {
    void saveProduct(List<SkuEsModel> models) throws IOException;
}
