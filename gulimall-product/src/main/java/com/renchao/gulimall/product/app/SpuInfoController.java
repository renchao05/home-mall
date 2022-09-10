package com.renchao.gulimall.product.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.renchao.common.to.SpuInfoTo;
import com.renchao.gulimall.product.vo.spuinfo.SpuInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.renchao.gulimall.product.entity.SpuInfoEntity;
import com.renchao.gulimall.product.service.SpuInfoService;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.R;



/**
 * spu信息
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 12:59:13
 */
@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = spuInfoService.queryPage(params);
        PageUtils page = spuInfoService.queryPageWrapper(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SpuInfoVo spuInfo){
		spuInfoService.saveSpuInfo(spuInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SpuInfoEntity spuInfo){
		spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 商品上架
     */
    @PostMapping("/{spuId}/up")
    public R up(@PathVariable Long spuId){
        return spuInfoService.spuUp(spuId);
    }

    /**
     * 批量获取spu信息
     */
    @PostMapping("/getSpuMap")
    public Map<Long, SpuInfoTo> getSpuMapBySkuIds(@RequestBody List<Long> skuIds) {
        return spuInfoService.getSpuMapBySkuIds(skuIds);
    }



}
