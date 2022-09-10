package com.renchao.common.constant;

public class OrderConstant {
    public static final String USER_ORDER_TOKEN_PREFIX = "order:token:";
    public static final String USER_ORDER_CONFIRM_PREFIX = "order:confirm:";

    // RabbitMQ
    public static final String ORDER_EVENT_EXCHANGE = "order-event-exchange";
    public static final String ORDER_DELAY_QUEUE = "order.delay.queue";
    public static final String ORDER_RELEASE_QUEUE = "order.release.queue";
    public static final String ORDER_CREATE_BINDING = "order.create.order";
    public static final String ORDER_RELEASE_BINDING = "order.release.order";
    public static final int ORDER_DELAY_TIME = 60000;
}
