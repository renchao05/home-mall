package com.renchao.mall.ware.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.renchao.mall.ware.vo.MergeVo;
import com.renchao.mall.ware.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.renchao.mall.ware.entity.PurchaseEntity;
import com.renchao.mall.ware.service.PurchaseService;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.R;



/**
 * 采购信息
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 17:48:45
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/unreceive/list")
    public R unreceiveList(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPageUnreceive(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PurchaseEntity purchase){
        purchase.setCreateTime(new Date());
        purchase.setUpdateTime(new Date());
		purchaseService.save(purchase);
        return R.ok();
    }

    /**
     * 合并采购单
     */
    @RequestMapping("/merge")
    public R merge(@RequestBody MergeVo merge){
		purchaseService.merge(merge);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PurchaseEntity purchase){
		purchaseService.updateById(purchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 领取采购单
     */
    @RequestMapping("/received")
    public R received(@RequestBody List<Long> pIds){
		purchaseService.updateByReceived(pIds);
        return R.ok();
    }


    /**
     * 完成采购
     */
    @RequestMapping("/done")
    public R done(@RequestBody PurchaseDoneVo doneVo){
		purchaseService.updateByDone(doneVo);
        return R.ok();
    }

}
