package com.renchao.mall.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.fastjson.JSON;
import com.renchao.mall.gateway.utils.R;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class SentinelGatewayConfig {

    //TODO 响应式编程
    public SentinelGatewayConfig(){
        GatewayCallbackManager.setBlockHandler(new BlockRequestHandler(){
            //网关限流了请求，就会调用此回调  Mono Flux
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
                R error = R.error(444, "访问量过大，稍后再试！");
                String errJson = JSON.toJSONString(error);
                return ServerResponse.ok().body(Mono.just(errJson), String.class);
            }
        });
    }
}
