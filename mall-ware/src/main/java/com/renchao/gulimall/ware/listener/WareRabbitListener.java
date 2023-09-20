package com.renchao.gulimall.ware.listener;

import com.rabbitmq.client.Channel;
import com.renchao.common.constant.OrderConstant;
import com.renchao.common.constant.WareConstant;
import com.renchao.gulimall.ware.service.WareSkuService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RabbitListener(queues = {WareConstant.WARE_RELEASE_STOCK_QUEUE})
public class WareRabbitListener {

    @Autowired
    private WareSkuService wareSkuService;

    @RabbitHandler
    public void receiveMessage(Long orderId, Message message, Channel channel) throws IOException {
        wareSkuService.unlockStock(orderId);
    }
}
