package com.renchao.gulimall.order.service;

import com.rabbitmq.client.Channel;
import com.renchao.gulimall.order.entity.OrderReturnReasonEntity;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

//@Service
@RabbitListener(queues = {"hello-queue"})
public class RabbitService {

    @RabbitHandler
    public void receiveMessage(OrderReturnReasonEntity orderReturn,Message message,Channel channel) throws IOException {
        System.out.println("OrderReturnReasonEntity::::" + orderReturn);
        channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
    }

    @RabbitHandler
    public void receiveMessage(String mgs,Message message,Channel channel) throws IOException {
        System.out.println("字符串类型::::" + mgs);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
