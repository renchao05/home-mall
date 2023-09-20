package com.renchao.gulimall.cart.service;

import com.renchao.gulimall.cart.vo.Cart;
import com.renchao.gulimall.cart.vo.CartItem;

import java.util.List;

public interface CartService {
    void addToCart(Long skuId, Integer count);

    CartItem getCartItem(Long skuId);

    Cart getCartList();

    void updateChecked(Integer checked, String skuId);

    void updateCount(Integer count, String skuId);

    void deleteCart(String skuId);

    List<CartItem> getCheckedCart();
}
