package com.renchao.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.to.OrderTo;
import com.renchao.common.utils.PageUtils;
import com.renchao.gulimall.order.entity.OrderEntity;
import com.renchao.gulimall.order.vo.OrderConfirmVo;
import com.renchao.gulimall.order.vo.PayAsyncVo;
import com.renchao.gulimall.order.vo.PayVo;

import java.util.Map;

/**
 * 订单
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 16:18:58
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVo confirmOrder();

    OrderConfirmVo changeAddress(Long addrId);

    OrderEntity submitOrder(String orderToken);

    void closeOrder(Long id);

    PayVo getOrderPay(String orderSn);

    OrderEntity getBySn(String orderSn);

    PageUtils queryPageWithItem(Map<String, Object> params);

    void successfulPayment(PayAsyncVo payAsyncVo);

    void updateOrderStatus(String orderSn, Integer code);
}

