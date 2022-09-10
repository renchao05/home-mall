package com.renchao.gulimall.order.config;

import com.renchao.gulimall.order.interceptor.OrderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class GulimallWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> list = new ArrayList<>();
        list.add("/order/order/getOrderInfo");
        list.add("/payed/notify");
        registry.addInterceptor(new OrderInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(list);
    }
}
