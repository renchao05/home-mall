package com.renchao.gulimall.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderApplicationTest {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void createExchange() {
        DirectExchange exchange = new DirectExchange("hello-exchange",true,false,null);
        amqpAdmin.declareExchange(exchange);
        log.info("Exchange[{}]创建成功！","hello-exchange");
    }

    @Test
    public void createQueue() {
        Queue queue = new Queue("hello-queue", true,false,false,null);
        amqpAdmin.declareQueue(queue);
        log.info("=========Queue[{}]创建成功！=============","hello-queue");
    }

@Test
public void createBinding() {
    Binding binding = new Binding("hello-queue", Binding.DestinationType.QUEUE, "hello-exchange", "hello.binding", null);
    amqpAdmin.declareBinding(binding);
    log.info("=========Binding[{}]创建成功！=============","hello.binding");
}
}
