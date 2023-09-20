package com.renchao.gulimall.order.controller;

import java.util.Arrays;
import java.util.Map;

import com.renchao.common.to.OrderTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.renchao.gulimall.order.entity.OrderEntity;
import com.renchao.gulimall.order.service.OrderService;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.R;


/**
 * 订单
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 16:18:58
 */
@RestController
@RequestMapping("order/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        OrderEntity order = orderService.getById(id);
        return R.ok().put("order", order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody OrderEntity order) {
        orderService.save(order);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody OrderEntity order) {
        orderService.updateById(order);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        orderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @GetMapping("/getOrderInfo")
    public R getOrderInfo(@RequestParam("id") Long id) {
        OrderEntity entity = orderService.getById(id);
        if (entity == null) {
            return R.error("没有查询到订单！");
        }
        return R.ok().put("status",entity.getStatus());
    }
}
