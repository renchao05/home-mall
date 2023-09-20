package com.renchao.mall.cart.controller;

import com.renchao.common.utils.R;
import com.renchao.mall.cart.service.CartService;
import com.renchao.mall.cart.vo.Cart;
import com.renchao.mall.cart.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart.html")
    public String cartList(Model model) {
        Cart cart = cartService.getCartList();
        model.addAttribute("cart", cart);
        return "cartList";
    }

    @GetMapping("/addCart.html")
    public String addCart(@RequestParam Long skuId,
                          @RequestParam Integer count) {
        cartService.addToCart(skuId, count);
        return "redirect:http://cart.renchao05.top/addCartSuccess.html?skuId=" + skuId + "&count=" + count;
    }


    @GetMapping("/addCartSuccess.html")
    public String success(@RequestParam Long skuId,
                          @RequestParam Integer count,
                          Model model) {
        CartItem item = cartService.getCartItem(skuId);
        item.setCount(count);
        model.addAttribute("item", item);
        return "success";
    }

    @GetMapping("/checkCart")
    @ResponseBody
    public R updateChecked(Integer checked, String skuId) {

        cartService.updateChecked(checked, skuId);
        Cart cart = cartService.getCartList();

        return R.ok().put("cart",cart);
    }

    @GetMapping("/countCart")
    @ResponseBody
    public R updateCount(Integer count, String skuId) {
        cartService.updateCount(count, skuId);
        Cart cart = cartService.getCartList();
        return R.ok().put("cart",cart);
    }

    @GetMapping("/deleteCart")
    public String deleteCart(String skuId) {
        cartService.deleteCart(skuId);
        return "redirect:http://cart.renchao05.top/cart.html";
    }

    /**
     * 获取购物车里面选中的商品
     */
    @GetMapping("/getCheckedCart")
    @ResponseBody
    public List<CartItem> getCheckedCart() {
        return cartService.getCheckedCart();
    }

}
