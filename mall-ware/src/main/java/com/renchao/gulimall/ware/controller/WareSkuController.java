package com.renchao.gulimall.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.renchao.common.to.OrderTo;
import com.renchao.common.to.WareLockTo;
import com.renchao.gulimall.ware.feign.ProductFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.renchao.gulimall.ware.entity.WareSkuEntity;
import com.renchao.gulimall.ware.service.WareSkuService;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.R;



/**
 * 商品库存
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 17:48:45
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    @Autowired
    private ProductFeignService productFeignService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareSkuService.queryPageWrapper(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WareSkuEntity wareSku){
		wareSkuService.save(wareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WareSkuEntity wareSku){
		wareSkuService.updateById(wareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		wareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 查询是否有库存
     */
    @GetMapping("/getStock")
    public R getStock(@RequestParam Long skuId) {
        Integer stock = wareSkuService.hasStock(skuId);
        return R.ok().put("stock", stock);
    }

    /**
     * 查询是否有库存
     */
    @PostMapping("/hasStock")
    public Map<Long, Boolean> hasStock(@RequestBody List<Long> ids) {
        return wareSkuService.hasStock(ids);
    }

    @GetMapping("/testfeign")
    public Map<String,Object> test() {
        R r = productFeignService.skuInfo(11L);
        System.out.println(r);
        return r;
    }

    /**
     * 锁定库存
     */
    @PostMapping("/wareLock")
    public R wareLock(@RequestBody OrderTo orderTo) {
        wareSkuService.wareLock(orderTo);
        return R.ok();
    }

}
