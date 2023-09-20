package com.renchao.mall.order.listener;

import com.rabbitmq.client.Channel;
import com.renchao.common.constant.OrderConstant;
import com.renchao.mall.order.entity.OrderEntity;
import com.renchao.mall.order.service.OrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RabbitListener(queues = {OrderConstant.ORDER_RELEASE_QUEUE})
public class OrderRabbitListener {

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void receiveMessage(OrderEntity order, Message message, Channel channel) throws IOException {
        orderService.closeOrder(order.getId());
    }
}
