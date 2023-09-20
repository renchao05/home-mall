package com.renchao.gulimall.order.web;

import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.R;
import com.renchao.gulimall.order.entity.OrderEntity;
import com.renchao.gulimall.order.service.OrderService;
import com.renchao.gulimall.order.vo.OrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class OrderWebController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{page}.html")
    public String page(@PathVariable String page) {
        return page;
    }

    /**
     * 提交订单
     */
    @PostMapping("submitOrder")
    public String submitOrder(@RequestParam("orderToken") String orderToken,
                              RedirectAttributes attributes) {
        OrderEntity order = orderService.submitOrder(orderToken);
        attributes.addFlashAttribute("order", order);
        return "redirect:http://order.renchao05.top/pay.html";
    }


    /**
     * 订单确认
     */
    @GetMapping("/toTrade")
    public String toTrade(Model model) {
        OrderConfirmVo confirmVo = orderService.confirmOrder();
        model.addAttribute("confirm", confirmVo);
        return "confirm";
    }

    /**
     * 变换收货地址
     */
    @GetMapping("/changeAddress")
    @ResponseBody
    public R changeAddress(@RequestParam("addrId") Long addrId) {
        OrderConfirmVo confirm = orderService.changeAddress(addrId);
        return R.ok().put("confirm", confirm);
    }


    /**
     * 订单列表
     * @param params
     * @param model
     * @return
     */
    @GetMapping("/orderList.html")
    public String orderList(@RequestParam Map<String, Object> params,Model model) {
        PageUtils page = orderService.queryPageWithItem(params);
        model.addAttribute("page", page);
        return "list";
    }
}
