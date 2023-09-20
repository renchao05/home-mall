package com.renchao.gulimall.order.config;

import com.renchao.common.constant.OrderConstant;
import com.renchao.gulimall.order.service.MyConfirmCallback;
import com.renchao.gulimall.order.service.MyReturnsCallback;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyRabbitMQConfig {

//    @Autowired
//    private MyReturnsCallback myReturnsCallback;
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//    @Autowired
//    private MyConfirmCallback myConfirmCallback;

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 注入 ConfirmCallback 回调接口
     */
//    @PostConstruct
//    public void initRabbitTemplate() {
//        rabbitTemplate.setConfirmCallback(myConfirmCallback);
//        rabbitTemplate.setReturnCallback(myReturnsCallback);
//    }

    /**
     * 订单服务交换机
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(OrderConstant.ORDER_EVENT_EXCHANGE, true, false);
    }

    /**
     * 延时队列
     * @return
     */
    @Bean
    public Queue delayQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", OrderConstant.ORDER_EVENT_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", OrderConstant.ORDER_RELEASE_BINDING);
        arguments.put("x-message-ttl", OrderConstant.ORDER_DELAY_TIME);
        return new Queue(OrderConstant.ORDER_DELAY_QUEUE, true, false, false, arguments);
    }

    /**
     * 取消订单队列
     * @return
     */
    @Bean
    public Queue releaseQueue() {
        return new Queue(OrderConstant.ORDER_RELEASE_QUEUE, true, false, false);
    }

    /**
     * 延时队列绑定到交换机
     * @return
     */
    @Bean
    public Binding delayQueueBinding() {
        return new Binding(
                OrderConstant.ORDER_DELAY_QUEUE,
                Binding.DestinationType.QUEUE,
                OrderConstant.ORDER_EVENT_EXCHANGE,
                OrderConstant.ORDER_CREATE_BINDING,
                null);
    }

    /**
     * 取消订单队列绑定到交换机
     * @param releaseQueue
     * @param directExchange
     * @return
     */
    @Bean
    public Binding releaseQueueBinding(Queue releaseQueue, DirectExchange directExchange) {
        // 上面构建绑定和这个方式构建都可以
        return BindingBuilder.bind(releaseQueue).to(directExchange).with(OrderConstant.ORDER_RELEASE_BINDING);
    }
}
