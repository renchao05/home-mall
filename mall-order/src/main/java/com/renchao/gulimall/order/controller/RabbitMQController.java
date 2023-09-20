package com.renchao.gulimall.order.controller;

import com.renchao.gulimall.order.entity.OrderReturnReasonEntity;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class RabbitMQController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/send")
    public String SendMessage() {
        OrderReturnReasonEntity orderReturn = new OrderReturnReasonEntity();
        orderReturn.setId(22L);
        orderReturn.setName("测试");
        orderReturn.setCreateTime(new Date());
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                rabbitTemplate.convertAndSend("hello-exchange", "hello.binding", orderReturn, new CorrelationData(UUID.randomUUID().toString()));
            } else {
                rabbitTemplate.convertAndSend("hello-exchange","hello.binding","消息" + i,new CorrelationData(UUID.randomUUID().toString()));
            }
        }
        return "OK!!";
    }
}
