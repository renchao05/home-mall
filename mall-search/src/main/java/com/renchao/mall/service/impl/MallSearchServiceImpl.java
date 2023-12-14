package com.renchao.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.renchao.common.exception.RRException;
import com.renchao.common.to.es.SkuEsModel;
import com.renchao.mall.constant.EsConstant;
import com.renchao.mall.service.MallSearchService;
import com.renchao.mall.vo.SearchParam;
import com.renchao.mall.vo.SearchResult;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MallSearchServiceImpl implements MallSearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MallSearchServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;

    @Override
    public SearchResult search(SearchParam param) {
        // 构建请求体
        SearchRequest request = BuilderSearchRequest(param);

        try {
            // 发送请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            LOGGER.info("查询结果：{}", response.toString());
            // 构建响应对象
            return BuilderSearchResult(response,param);
        } catch (IOException e) {
            LOGGER.error("查询出错{}", e.getMessage(), e);
            throw new RRException("查询出错:" + e.getMessage());
        }
    }

    /**
     * 构建检索请求
     */
    private SearchRequest BuilderSearchRequest(SearchParam param) {
        String keyword = param.getKeyword();
        Long catalogId = param.getCatalog3Id();
        List<Long> brandId = param.getBrandId();
        List<String> attrs = param.getAttrs();
        Integer hasStock = param.getHasStock();
        String skuPrice = param.getSkuPrice();
        String sort = param.getSort();

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 构建bool
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 关键词查询
        if (!StringUtils.isEmpty(keyword)) {
            boolQuery.must(QueryBuilders.matchQuery("skuTitle", keyword));
        }
        // 分类
        if (catalogId != null) {
            boolQuery.filter(QueryBuilders.termQuery("catalogId", catalogId));
        }
        // 品牌
        if (!CollectionUtils.isEmpty(brandId)) {
            boolQuery.filter(QueryBuilders.termsQuery("brandId", brandId));
        }

        // 属性 //attrs=1_5寸:8寸&attrs=2_16G:8G
        if (!CollectionUtils.isEmpty(attrs)) {
            attrs.forEach(a -> {
                BoolQueryBuilder attrBoolQuery = QueryBuilders.boolQuery();
                String[] s = a.split("_");
                String attrId = s[0];
                String[] attrValues = s[1].split(":");
                attrBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                attrBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", attrBoolQuery, ScoreMode.None);
                boolQuery.filter(nestedQuery);
            });
        }

        // 是否有库存
        if (hasStock != null) {
            boolQuery.filter(QueryBuilders.termQuery("hasStock", hasStock == 1));
        }

        // 价格区间 //1_500/_500/500_
        if (!StringUtils.isEmpty(skuPrice)) {
            String[] s = skuPrice.split("_");
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");
            if (!StringUtils.isEmpty(s[0])) {
                rangeQuery.gte(s[0]);
            }
            if (s.length >= 2 && !s[1].isEmpty()) {
                rangeQuery.lte(s[1]);
            }
            boolQuery.filter(rangeQuery);
        }

        sourceBuilder.query(boolQuery);

        // 排序 //sort=hotScore_asc/desc
        if (!StringUtils.isEmpty(sort)) {
            String[] s = sort.split("_");
            sourceBuilder.sort(s[0], s[1].equals("asc") ? SortOrder.ASC : SortOrder.DESC);
        }

        // 分页 //from = (pageNum-1)*size
        sourceBuilder.from((param.getPageNum() - 1) * EsConstant.PRODUCT_PAGE_SIZE);
        sourceBuilder.size(EsConstant.PRODUCT_PAGE_SIZE);

        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("skuTitle").preTags(EsConstant.PRE_TAGS).postTags(EsConstant.POST_TAGS);
        sourceBuilder.highlighter(highlightBuilder);

        // 聚合品牌
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand_agg").field("brandId").size(10);
        brandAgg.subAggregation(AggregationBuilders.terms("brandName").field("brandName"));
        brandAgg.subAggregation(AggregationBuilders.terms("brandImg").field("brandImg"));
        sourceBuilder.aggregation(brandAgg);

        // 聚合分类
        TermsAggregationBuilder catalogAgg = AggregationBuilders.terms("catalog_agg").field("catalogId").size(10);
        catalogAgg.subAggregation(AggregationBuilders.terms("catalogName").field("catalogName"));
        sourceBuilder.aggregation(catalogAgg);

        // 聚合属性
        NestedAggregationBuilder attrAgg = AggregationBuilders.nested("attr_agg", "attrs");
        TermsAggregationBuilder attrAggB = AggregationBuilders.terms("attr_agg").field("attrs.attrId").size(10);
        attrAggB.subAggregation(AggregationBuilders.terms("attrName").field("attrs.attrName"));
        attrAggB.subAggregation(AggregationBuilders.terms("attrValue").field("attrs.attrValue"));
        attrAgg.subAggregation(attrAggB);
        sourceBuilder.aggregation(attrAgg);
//        System.out.println("请求条件：" + sourceBuilder);
        LOGGER.info("请求条件：{}", sourceBuilder);
        return new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, sourceBuilder);
    }


    /**
     * 分析和封装响应数据
     */
    private SearchResult BuilderSearchResult(SearchResponse response,SearchParam param) {
        SearchResult result = new SearchResult();
        // 商品信息
        List<SkuEsModel> skuEsModels = new ArrayList<>();
        response.getHits().forEach(hit-> {
            SkuEsModel skuEsModel = JSON.parseObject(hit.getSourceAsString(), SkuEsModel.class);
            // 设置高亮
            if (!StringUtils.isEmpty(param.getKeyword())) {
                HighlightField skuTitle = hit.getHighlightFields().get("skuTitle");
                skuEsModel.setSkuTitle(skuTitle.getFragments()[0].string());
            }
            skuEsModels.add(skuEsModel);
        });
        result.setProducts(skuEsModels);

        // 页码信息
        result.setPageNum(param.getPageNum());
        long total = response.getHits().getTotalHits().value;
        result.setTotal(total);
        long totalPages = total % EsConstant.PRODUCT_PAGE_SIZE == 0 ? total / EsConstant.PRODUCT_PAGE_SIZE : total / EsConstant.PRODUCT_PAGE_SIZE + 1;
        result.setTotalPages(Math.toIntExact(totalPages));
        result.setPageSize(EsConstant.PRODUCT_PAGE_SIZE);

        // 品牌
        ArrayList<SearchResult.BrandVo> brands = new ArrayList<>();
        Terms brandAgg = response.getAggregations().get("brand_agg");
        brandAgg.getBuckets().forEach(bucket -> {
            SearchResult.BrandVo brandVo = new SearchResult.BrandVo();
            brandVo.setBrandId((Long) bucket.getKey());
            Terms brandName = bucket.getAggregations().get("brandName");
            brandVo.setBrandName(String.valueOf(brandName.getBuckets().get(0).getKey()));
            Terms brandImg = bucket.getAggregations().get("brandImg");
            brandVo.setBrandImg(String.valueOf(brandImg.getBuckets().get(0).getKey()));
            brands.add(brandVo);
        });
        result.setBrands(brands);

        // 分类
        List<SearchResult.CatalogVo> catalogs = new ArrayList<>();
        Terms catalogAgg = response.getAggregations().get("catalog_agg");
        catalogAgg.getBuckets().forEach(bucket -> {
            SearchResult.CatalogVo catalogVo = new SearchResult.CatalogVo();
            catalogVo.setCatalogId((Long) bucket.getKey());
            Terms catalogName = bucket.getAggregations().get("catalogName");
            catalogVo.setCatalogName(String.valueOf(catalogName.getBuckets().get(0).getKey()));
            catalogs.add(catalogVo);
        });
        result.setCatalogs(catalogs);

        // 属性
        List<SearchResult.AttrVo> attrs = new ArrayList<>();
        Nested attrNested = response.getAggregations().get("attr_agg");
        Terms attrAgg = attrNested.getAggregations().get("attr_agg");
        attrAgg.getBuckets().forEach(bucket -> {
            SearchResult.AttrVo attrVo = new SearchResult.AttrVo();
            attrVo.setAttrId((Long) bucket.getKey());
            Terms attrName = bucket.getAggregations().get("attrName");
            attrVo.setAttrName(String.valueOf(attrName.getBuckets().get(0).getKey()));
            Terms attrValue = bucket.getAggregations().get("attrValue");
            List<String> avs = new ArrayList<>();
            attrValue.getBuckets().forEach(b -> avs.add(String.valueOf(b.getKey())));
            attrVo.setAttrValue(avs);
            attrs.add(attrVo);
        });
        result.setAttrs(attrs);
        return result;
    }


}
