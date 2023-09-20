package com.renchao.mall.seckill.controller;

import com.renchao.common.utils.R;
import com.renchao.mall.seckill.service.SeckillService;
import com.renchao.mall.seckill.to.SecKillSkuRedisTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 秒杀业务没有做，课程里面有很多不合理的地方，暂时略过，以后补充
 */
@RestController
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    /**
     * 首页获取秒杀商品进行展示
     * @return
     */
    @GetMapping("/getCurrentSeckillSkus")
    public R getCurrentSeckillSkus() {

        List<SecKillSkuRedisTo> skuRedisTos = seckillService.getCurrentSeckillSkus();
        return R.ok().put("data",skuRedisTos);
    }


    /**
     * 查询商品是否是秒杀商品，
     * 整个商城不是秒杀的商品是多数，每个商品都查询一次，显然不合理。需要改进
     * @param skuId
     * @return
     */
    @GetMapping("/seckill/sku/{skuId}")
    public R getSeckillSkuInfo(@PathVariable("skuId") String skuId) {
        SecKillSkuRedisTo skuRedisTo = seckillService.getSeckillSkuInfo(skuId);
        return R.ok().put("data",skuRedisTo);
    }


    /**
     * 订单确认
     */
    @GetMapping("/toTrade")
    public String toTrade(Model model) {
//        OrderConfirmVo confirmVo = seckillService.confirmOrder();
//        model.addAttribute("confirm", confirmVo);
        return "confirm";
    }
}
