package com.renchao.gulimall.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.renchao.common.constant.OrderConstant;
import com.renchao.common.constant.WareConstant;
import com.renchao.common.exception.SubmitOrderException;
import com.renchao.common.to.OrderTo;
import com.renchao.common.to.SpuInfoTo;
import com.renchao.common.to.UserTo;
import com.renchao.common.to.WareLockTo;
import com.renchao.common.utils.R;
import com.renchao.gulimall.order.config.AlipayTemplate;
import com.renchao.gulimall.order.entity.OrderItemEntity;
import com.renchao.gulimall.order.entity.PaymentInfoEntity;
import com.renchao.gulimall.order.enume.OrderStatusEnum;
import com.renchao.gulimall.order.feign.CartFeignService;
import com.renchao.gulimall.order.feign.MemberFeignService;
import com.renchao.gulimall.order.feign.ProductFeignService;
import com.renchao.gulimall.order.feign.WareFeignService;
import com.renchao.gulimall.order.interceptor.OrderInterceptor;
import com.renchao.gulimall.order.service.OrderItemService;
import com.renchao.gulimall.order.service.PaymentInfoService;
import com.renchao.gulimall.order.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.gulimall.order.dao.OrderDao;
import com.renchao.gulimall.order.entity.OrderEntity;
import com.renchao.gulimall.order.service.OrderService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private MemberFeignService memberFeignService;

    @Autowired
    private CartFeignService cartFeignService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private WareFeignService wareFeignService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AlipayTemplate alipayTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public OrderConfirmVo confirmOrder() {
        OrderConfirmVo confirmVo = new OrderConfirmVo();
        UserTo userTo = OrderInterceptor.threadLocal.get();
        // 多线程向子线程同步请求数据request
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(requestAttributes,true);
        // 收货地址 ums_member_receive_address
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            List<MemberAddressVo> address = memberFeignService.getAddress(userTo.getUserId());
            address.forEach(a -> {
                // 设置收货地址
                if (a.getDefaultStatus() == 1) {
                    confirmVo.setSa(a);
                }
            });
            confirmVo.setAddress(address);
        }, executor).thenRunAsync(() -> {
            // 获取运费
            MemberAddressVo addressVo = null;
            for (MemberAddressVo address : confirmVo.getAddress()) {
                if (address.getDefaultStatus().equals(1)) {
                    addressVo = address;
                    break;
                }
            }
            String fare = wareFeignService.getFare(addressVo);
            confirmVo.setFare(new BigDecimal(fare));
        }, executor);

        // 所有选中的购物车项
        CompletableFuture<Void> itemFuture = CompletableFuture.runAsync(() -> {
            List<OrderItemVo> itemVos = cartFeignService.getCheckedCart();
            // 检查库存
            List<Long> ids = itemVos.stream().map(OrderItemVo::getSkuId).collect(Collectors.toList());
            Map<Long, Boolean> stocks = wareFeignService.hasStock(ids);
            for (OrderItemVo itemVo : itemVos) {
                itemVo.setHasStock(stocks.get(itemVo.getSkuId()));
            }
            confirmVo.setItems(itemVos);
        }, executor);

        // 优惠信息。。。。。

        // 防重令牌
        String token = UUID.randomUUID().toString().replace("-", "");
        String tokenKey = OrderConstant.USER_ORDER_TOKEN_PREFIX + userTo.getUserId();
        redisTemplate.opsForValue().set(tokenKey, token);
        confirmVo.setOrderToken(token);

        try {
            CompletableFuture.allOf(addressFuture, itemFuture).get();
        } catch (InterruptedException | ExecutionException e) {
            // TODO 异常需要统一处理
            e.printStackTrace();
        }
        redisTemplate.opsForValue().set(OrderConstant.USER_ORDER_CONFIRM_PREFIX + userTo.getUserId(), JSON.toJSONString(confirmVo),30, TimeUnit.MINUTES);
        return confirmVo;
    }

    @Override
    public OrderConfirmVo changeAddress(Long addrId) {
        UserTo userTo = OrderInterceptor.threadLocal.get();
        String s = redisTemplate.opsForValue().get(OrderConstant.USER_ORDER_CONFIRM_PREFIX + userTo.getUserId());
        OrderConfirmVo confirm = JSON.parseObject(s,OrderConfirmVo.class);
        if (confirm == null) {
            return null;
        }
        List<MemberAddressVo> address = confirm.getAddress();
        for (MemberAddressVo addressVo : address) {
            addressVo.setDefaultStatus(0);
            if (Objects.equals(addressVo.getId(), addrId)) {
                addressVo.setDefaultStatus(1);
                confirm.setSa(addressVo);
            }
        }
        // 更新运费
        String fare = wareFeignService.getFare(confirm.getSa());
        confirm.setFare(new BigDecimal(fare));
        return confirm;
    }

    /**
     * 提交订单
     */
    @Override
    @Transactional
    public OrderEntity submitOrder(String orderToken) {
        UserTo userTo = OrderInterceptor.threadLocal.get();
        // 令牌校验
        String tokenKey = OrderConstant.USER_ORDER_TOKEN_PREFIX + userTo.getUserId();
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Long result = redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Collections.singletonList(tokenKey), orderToken);
        if (result == null || result == 0) {
            throw new SubmitOrderException("订单超时，重新提交！");
        }
        // 准备数据
        String orderId = IdWorker.getTimeId().substring(0, 15) + userTo.getUserId();
        String s = redisTemplate.opsForValue().get(OrderConstant.USER_ORDER_CONFIRM_PREFIX + userTo.getUserId());
        OrderConfirmVo confirm = JSON.parseObject(s,OrderConfirmVo.class);
        // 获取购物车最新商品
        List<OrderItemVo> items = cartFeignService.getCheckedCart();
        if (confirm == null) {
            throw new SubmitOrderException("页面超时，请刷新页面重新提交！");
        }
        if (CollectionUtils.isEmpty(items)) {
            throw new SubmitOrderException("请在购物车重新选择需要支付的商品！");
        }
        // 创建订单
        OrderEntity orderEntity = getOrderEntity(userTo, orderId,confirm);
        // 创建订单项
        List<OrderItemEntity> orderItems = getOrderItemEntities(orderId,items);
        // 校验价格 checkPrice
        checkPrice(confirm, items);
        // 保存订单
        this.save(orderEntity);
        orderItemService.saveBatch(orderItems);
        // 锁定库存
        wareLock(orderEntity,orderItems);

        // 创建延时队列，超时关闭订单
        rabbitTemplate.convertAndSend(OrderConstant.ORDER_EVENT_EXCHANGE,OrderConstant.ORDER_CREATE_BINDING,orderEntity);
        return orderEntity;
    }

    /**
     * 关闭订单
     * @param id
     */
    @Override
    @Transactional
    public void closeOrder(Long id) {
        OrderEntity order = this.getById(id);
        // 如果是未支付状态则关闭订单
        if (!order.getStatus().equals(OrderStatusEnum.CREATE_NEW.getCode())) {
            return;
        }
        // 支付宝收单，交易关闭。必须买家登录支付宝后才能生成订单，才能关闭交易
        try {
            alipayTemplate.close(order.getOrderSn());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(id);
        orderEntity.setStatus(OrderStatusEnum.CANCLED.getCode());
        this.updateById(orderEntity);
        // 发送解锁库存任务到MQ
        rabbitTemplate.convertAndSend(WareConstant.WARE_EVENT_EXCHANGE,WareConstant.WARE_RELEASE_STOCK_BINDING,id);
    }

    @Override
    public PayVo getOrderPay(String orderSn) {
        OrderEntity order = this.getBySn(orderSn);
        PayVo payVo = new PayVo();
        payVo.setOutTradeNo(orderSn);
        BigDecimal totalAmount = order.getTotalAmount().setScale(2, RoundingMode.HALF_UP);
        payVo.setTotalAmount(totalAmount.toString());
        List<OrderItemEntity> orderItems = orderItemService.listByOrderSn(orderSn);
        OrderItemEntity item = orderItems.get(0);
        payVo.setSubject(item.getSkuName());
        payVo.setBody(item.getSkuAttrsVals());
        return payVo;
    }

    @Override
    public OrderEntity getBySn(String orderSn) {
        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderEntity::getOrderSn, orderSn);
        return this.getOne(wrapper);
    }

    @Override
    public PageUtils queryPageWithItem(Map<String, Object> params) {
        UserTo userTo = OrderInterceptor.threadLocal.get();
        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderEntity::getMemberId, userTo.getUserId()).orderByDesc(OrderEntity::getCreateTime);
        IPage<OrderEntity> page = this.page(new Query<OrderEntity>().getPage(params), wrapper);
        for (OrderEntity orderEntity : page.getRecords()) {
            List<OrderItemEntity> items = orderItemService.listByOrderSn(orderEntity.getOrderSn());
            orderEntity.setItems(items);
        }
        return new PageUtils(page);
    }

    /**
     * 订单支付成功
     * @param vo
     */
    @Override
    public void successfulPayment(PayAsyncVo vo) {
        PaymentInfoEntity paymentInfo = new PaymentInfoEntity();
        paymentInfo.setOrderSn(vo.getOut_trade_no());
        paymentInfo.setAlipayTradeNo(vo.getTrade_no());
        paymentInfo.setTotalAmount(new BigDecimal(vo.getBuyer_pay_amount()));
        paymentInfo.setPaymentStatus(vo.getTrade_status());
        paymentInfo.setCreateTime(vo.getGmt_create());
        paymentInfo.setConfirmTime(vo.getGmt_payment());
        paymentInfo.setCallbackTime(vo.getNotify_time());
        paymentInfoService.save(paymentInfo);
        String status = vo.getTrade_status();
        if (status.equals("TRADE_SUCCESS") || status.equals("TRADE_FINISHED")) {
            String orderSn = vo.getOut_trade_no();
            this.updateOrderStatus(orderSn, OrderStatusEnum.PAYED.getCode());
        }
    }

    @Override
    public void updateOrderStatus(String orderSn, Integer code) {
        this.getBaseMapper().updateOrderStatus(orderSn, code);
    }

    /**
     * 锁定库存
     */
    private void wareLock(OrderEntity order,List<OrderItemEntity> orderItems) {
        // 订单主体
        OrderTo orderTo = new OrderTo();
        orderTo.setOrderId(order.getId());
        orderTo.setConsignee(order.getReceiverName());
        orderTo.setConsigneeTel(order.getReceiverPhone());
        String deliveryAddress = order.getReceiverProvince()
                + order.getReceiverCity()
                + order.getReceiverRegion()
                + order.getReceiverDetailAddress();
        orderTo.setDeliveryAddress(deliveryAddress);
        orderTo.setOrderComment(order.getNote());

        // 订单详情
        List<WareLockTo> wareLockTos = orderItems.stream().map(item -> {
            WareLockTo wareLockTo = new WareLockTo();
            wareLockTo.setOrderId(item.getOrderId());
            wareLockTo.setSkuId(item.getSkuId());
            wareLockTo.setSkuName(item.getSkuName());
            wareLockTo.setSkuQuantity(item.getSkuQuantity());
            return wareLockTo;
        }).collect(Collectors.toList());
        orderTo.setWareLockTos(wareLockTos);
        R r = wareFeignService.wareLock(orderTo);
        if (r.getCode() != 0) {
            throw new SubmitOrderException(r.getMsg());
        }
    }

    private void checkPrice(OrderConfirmVo confirm, List<OrderItemVo> items) {
        BigDecimal newTotal = new BigDecimal("0");
        for (OrderItemVo item : items) {
            BigDecimal multiply = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));
            newTotal = newTotal.add(multiply);
        }
        double sub = confirm.getTotal().subtract(newTotal).doubleValue();
        if (Math.abs(sub) >= 0.01) {
            throw new SubmitOrderException("价格有变化，请重新提交订单！");
        }
    }

    /**
     * 构建订单项
     * @param orderId
     * @return
     */
    private List<OrderItemEntity> getOrderItemEntities(String orderId,List<OrderItemVo> items) {
        List<OrderItemEntity> orderItems = new ArrayList<>();

        List<Long> skuIds = items.stream().map(OrderItemVo::getSkuId).collect(Collectors.toList());
        Map<Long, SpuInfoTo> spuMaps = productFeignService.getSpuMapBySkuIds(skuIds);
        for (OrderItemVo item : items) {
            SpuInfoTo spuInfoTo = spuMaps.get(item.getSkuId());
            OrderItemEntity itemEntity = new OrderItemEntity();
            itemEntity.setOrderSn(orderId);
            itemEntity.setSpuId(spuInfoTo.getId());
            itemEntity.setSpuName(spuInfoTo.getSpuName());
            itemEntity.setSpuPic("");
            itemEntity.setSpuBrand(spuInfoTo.getBrandId().toString());
            itemEntity.setCategoryId(spuInfoTo.getCatalogId());
            itemEntity.setSkuId(item.getSkuId());
            itemEntity.setSkuName(item.getTitle());
            itemEntity.setSkuPic(item.getImage());
            itemEntity.setSkuPrice(item.getPrice());
            itemEntity.setSkuQuantity(item.getCount());
            itemEntity.setSkuAttrsVals(StringUtils.join(item.getSkuAttr(),";"));

            // 商品优惠信息，功能没有做，默认都是0
            BigDecimal bigDecimal = new BigDecimal("0");
            itemEntity.setPromotionAmount(bigDecimal);
            itemEntity.setCouponAmount(bigDecimal);
            itemEntity.setIntegrationAmount(bigDecimal);
            itemEntity.setRealAmount(bigDecimal);
            itemEntity.setGiftIntegration(0);
            itemEntity.setGiftGrowth(0);
            orderItems.add(itemEntity);
        }
        return orderItems;
    }

    /**
     * 构建订单
     * @param userTo
     * @param orderId
     * @param confirm
     * @return
     */
    private OrderEntity getOrderEntity(UserTo userTo, String orderId,OrderConfirmVo confirm) {
        OrderEntity order = new OrderEntity();
        order.setMemberId(userTo.getUserId());
        order.setOrderSn(orderId);
        order.setCreateTime(new Date());
        order.setMemberUsername(userTo.getUsername());

        // 设置价格和运费
        order.setTotalAmount(confirm.getTotal());
        order.setPayAmount(confirm.getPayPrice());
        order.setFreightAmount(confirm.getFare());

        // 设置优惠信息。因为没有做这个功能，都是默认值0
        BigDecimal bigDecimal = new BigDecimal("0");
        order.setPromotionAmount(bigDecimal);
        order.setIntegrationAmount(bigDecimal);
        order.setCouponAmount(bigDecimal);
        order.setDiscountAmount(bigDecimal);

        // 支付方式、订单来源、订单状态、自动确认时间
        order.setPayType(confirm.getPayType());
        order.setSourceType(confirm.getSourceType()); // 功能没有做，这里默认是PC
        order.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        order.setAutoConfirmDay(7); // 默认是7天

        // 成长值、积分。功能没有做，默认0
        order.setIntegration(0);
        order.setUseIntegration(0);
        order.setGrowth(0);

        // 默认不开发票
        order.setBillType(0);

        // 收件人信息
        MemberAddressVo sa = confirm.getSa();
        order.setReceiverName(sa.getName());
        order.setReceiverPhone(sa.getPhone());
        order.setReceiverPostCode(sa.getPostCode());
        order.setReceiverProvince(sa.getProvince());
        order.setReceiverCity(sa.getCity());
        order.setReceiverRegion(sa.getRegion());
        order.setReceiverRegion(sa.getRegion());
        order.setReceiverDetailAddress(sa.getDetailAddress());

        // 订单备注,功能没有做
        order.setNote("");

        // 收货状态、删除状态
        order.setConfirmStatus(0);
        order.setDeleteStatus(0);

        // 修改时间
        order.setModifyTime(new Date());

        return order;
    }

}