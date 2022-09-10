package com.renchao.gulimall.order.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyReturnsCallback implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("消息【{}】,被交换机【{}】退回，退回原因:【{}】,路由key:【{}】",
                new String(message.getBody()),
                exchange,
                replyText,
                routingKey);
    }
}
