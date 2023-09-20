package com.renchao.mall.order.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class MallFeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // 获取当前的原始请求request
            ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                // 把需要的请求头数据同步到新请求
                String cookie = request.getHeader("Cookie");
                template.header("Cookie", cookie);
            }
        };
    }
}
