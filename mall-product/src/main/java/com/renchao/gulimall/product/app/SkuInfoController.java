package com.renchao.gulimall.product.app;

import java.util.Arrays;
import java.util.Map;

import com.renchao.common.to.CartItemTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.renchao.gulimall.product.entity.SkuInfoEntity;
import com.renchao.gulimall.product.service.SkuInfoService;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.R;



/**
 * sku信息
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 12:59:14
 */
@RestController
@RequestMapping("product/skuinfo")
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = skuInfoService.queryPage(params);
        PageUtils page = skuInfoService.queryPageWrapper(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{skuId}")
    public R info(@PathVariable("skuId") Long skuId){
		SkuInfoEntity skuInfo = skuInfoService.getById(skuId);

        return R.ok().put("skuInfo", skuInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SkuInfoEntity skuInfo){
		skuInfoService.save(skuInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SkuInfoEntity skuInfo){
		skuInfoService.updateById(skuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] skuIds){
		skuInfoService.removeByIds(Arrays.asList(skuIds));

        return R.ok();
    }

    /**
     * 获取购物车信息
     */
    @GetMapping("/getCarItem/{skuId}")
    public R getCarItem(@PathVariable("skuId") Long skuId) {
        CartItemTo cartItemTo = skuInfoService.getCarItem(skuId);
        return R.ok().put("cartItemTo",cartItemTo);
    }

    /**
     * 查询商品价格
     */
    @GetMapping("/getPrice/{skuId}")
    public String getPrice(@PathVariable Long skuId) {
        SkuInfoEntity skuInfo = skuInfoService.getById(skuId);
        return skuInfo.getPrice().toString();
    }

}
