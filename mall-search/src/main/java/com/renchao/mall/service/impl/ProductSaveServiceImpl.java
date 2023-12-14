package com.renchao.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.renchao.common.to.es.SkuEsModel;
import com.renchao.mall.constant.EsConstant;
import com.renchao.mall.service.ProductSaveService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductSaveServiceImpl implements ProductSaveService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSaveServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;

    @Override
    public void saveProduct(List<SkuEsModel> models) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        models.forEach(model -> {
            IndexRequest request = new IndexRequest(EsConstant.PRODUCT_INDEX).id(model.getSkuId().toString());
            String skuStr = JSON.toJSONString(model);
            LOGGER.info(skuStr);
            request.source(skuStr, XContentType.JSON);
            bulkRequest.add(request);
        });
        LOGGER.info("bulkRequest:{}", bulkRequest.getDescription());
        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        LOGGER.info("bulk:{}", bulk.buildFailureMessage());
    }
}
