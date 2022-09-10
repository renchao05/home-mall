package com.renchao.gulimall.ware.config;

import com.renchao.common.constant.OrderConstant;
import com.renchao.common.constant.WareConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class MyRabbitMQConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 库存服务交换机
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(WareConstant.WARE_EVENT_EXCHANGE, true, false);
    }

    /**
     * 延时队列
     * @return
     */
    @Bean
    public Queue delayQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", WareConstant.WARE_EVENT_EXCHANGE);
        args.put("x-dead-letter-routing-key", WareConstant.WARE_RELEASE_STOCK_BINDING);
        args.put("x-message-ttl", WareConstant.WARE_DELAY_UNLOCK_TIME);
        return new Queue(WareConstant.WARE_DELAY_QUEUE, true, false, false,args);
    }

    /**
     * 解锁库存队列
     * @return
     */
    @Bean
    public Queue releaseQueue() {
        return new Queue(WareConstant.WARE_RELEASE_STOCK_QUEUE, true, false, false);
    }

    /**
     * 解锁库存队列绑定到交换机
     * @return
     */
    @Bean
    public Binding releaseQueueBinding() {
        return new Binding(
                WareConstant.WARE_RELEASE_STOCK_QUEUE,
                Binding.DestinationType.QUEUE,
                WareConstant.WARE_EVENT_EXCHANGE,
                WareConstant.WARE_RELEASE_STOCK_BINDING,
                null);
    }

    /**
     * 延时存队列绑定到交换机
     * @return
     */
    @Bean
    public Binding delayQueueBinding() {
        return new Binding(
                WareConstant.WARE_DELAY_QUEUE,
                Binding.DestinationType.QUEUE,
                WareConstant.WARE_EVENT_EXCHANGE,
                WareConstant.WARE_DELAY_BINDING,
                null);
    }

}
