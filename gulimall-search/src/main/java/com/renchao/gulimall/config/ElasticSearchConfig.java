package com.renchao.gulimall.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.host}")
    private String hostname;

    @Value("${elasticsearch.port}")
    private Integer port;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        HttpHost http = new HttpHost(hostname, port, "http");
        RestClientBuilder builder = RestClient.builder(http);
        return new RestHighLevelClient(builder);
    }
}
