package com.renchao.mall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.renchao.common.to.CartItemTo;
import com.renchao.common.utils.R;
import com.renchao.mall.cart.constant.CartConstant;
import com.renchao.mall.cart.feign.ProductFeignService;
import com.renchao.mall.cart.interceptor.CartInterceptor;
import com.renchao.mall.cart.service.CartService;
import com.renchao.mall.cart.vo.Cart;
import com.renchao.mall.cart.vo.CartItem;
import com.renchao.mall.cart.vo.UserInfoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProductFeignService productFeignService;

    @Override
    public void addToCart(Long skuId, Integer count) {
        CartItem item = new CartItem();
        item.setSkuId(skuId);
        item.setCount(count);
        item.setCheck(true);

        // 远程获取商品信息
        R r = productFeignService.getCarItem(skuId);
        CartItemTo cartItemTo = r.getData("cartItemTo", CartItemTo.class);
        item.setTitle(cartItemTo.getTitle());
        item.setPrice(cartItemTo.getPrice());
        item.setImage(cartItemTo.getImage());
        item.setSkuAttr(cartItemTo.getSkuAttr());
        addToCart(item);
    }

    /**
     * 添加到购物车
     */
    private void addToCart(CartItem item) {
        BoundHashOperations<String, Object, Object> cartOps = getCart();
        String skuId = item.getSkuId().toString();
        Object o = cartOps.get(skuId);
        if (o == null) {
            cartOps.put(skuId, JSON.toJSONString(item));
        } else {
            // 如果购物车已经有该商品，则只添加数量
            CartItem oldItem = JSON.parseObject(o.toString(), CartItem.class);
            oldItem.setCount(oldItem.getCount() + item.getCount());
            cartOps.put(skuId,JSON.toJSONString(oldItem));
        }
    }

    @Override
    public CartItem getCartItem(Long skuId) {
        Object o = getCart().get(skuId.toString());
        if (o == null) {
            return null;
        }
        return JSON.parseObject(o.toString(), CartItem.class);
    }

    @Override
    public Cart getCartList() {
        Cart cart = new Cart();
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        List<CartItem> userKeyCartItems = getCartList(userInfoTo.getUserKey());

        if (userInfoTo.getUserId() == null) {   // 如果没有登录
            cart.setItems(userKeyCartItems);
        } else {    // 如果登录了
            // 合并购物车
            if (!CollectionUtils.isEmpty(userKeyCartItems)) {
                userKeyCartItems.forEach(this::addToCart);
                // 清空临时购物车
                clearCart(userInfoTo.getUserKey());
            }
            List<CartItem> cartList = getCartList(userInfoTo.getUserId().toString());
            cart.setItems(cartList);
        }
        return cart;
    }

    @Override
    public void updateChecked(Integer checked, String skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCart();
        Object o = cartOps.get(skuId);
        CartItem item = JSON.parseObject(Objects.requireNonNull(o).toString(), CartItem.class);
        item.setCheck(checked.equals(1));
        cartOps.put(skuId, JSON.toJSONString(item));
    }

    @Override
    public void updateCount(Integer count, String skuId) {
        if (count <= 0) {
            deleteCart(skuId);
            return;
        }
        BoundHashOperations<String, Object, Object> cartOps = getCart();
        Object o = cartOps.get(skuId);
        CartItem item = JSON.parseObject(Objects.requireNonNull(o).toString(), CartItem.class);
        item.setCount(count);
        cartOps.put(skuId, JSON.toJSONString(item));
    }

    @Override
    public void deleteCart(String skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCart();
        cartOps.delete(skuId);
    }

    @Override
    public List<CartItem> getCheckedCart() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        if (userInfoTo.getUserId() == null) {
            return null;
        }
        List<CartItem> cartList = getCartList(userInfoTo.getUserId().toString());
        if (cartList == null) {
            return null;
        }
        return cartList.stream().filter(CartItem::getCheck)
                .peek(cartItem -> {
                    // TODO 远程调用有异常需要处理
                    String price = productFeignService.getPrice(cartItem.getSkuId());
                    cartItem.setPrice(new BigDecimal(price));
                }).collect(Collectors.toList());
    }

    /**
     * 清理购物车
     */
    public void clearCart(String key) {
        String cartKey = CartConstant.CART_PREFIX + key;
        redisTemplate.delete(cartKey);
    }

    private List<CartItem> getCartList(String key) {
        String cartKey = CartConstant.CART_PREFIX + key;
        List<Object> userKeyValues = redisTemplate.boundHashOps(cartKey).values();
        if (CollectionUtils.isEmpty(userKeyValues)) {
            return null;
        }
        return userKeyValues.stream()
                .map(o -> JSON.parseObject(o.toString(), CartItem.class))
                .collect(Collectors.toList());
    }

    /**
     * 获取需要操作的购物车
     * @return
     */
    private BoundHashOperations<String, Object, Object> getCart() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        String cartKey;
        if (userInfoTo.getUserId() == null) {
            cartKey = CartConstant.CART_PREFIX + userInfoTo.getUserKey();
        } else {
            cartKey = CartConstant.CART_PREFIX + userInfoTo.getUserId();
        }
        return redisTemplate.boundHashOps(cartKey);
    }
}
