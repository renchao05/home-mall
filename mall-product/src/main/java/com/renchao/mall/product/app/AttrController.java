package com.renchao.mall.product.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.renchao.mall.product.entity.ProductAttrValueEntity;
import com.renchao.mall.product.service.ProductAttrValueService;
import com.renchao.mall.product.vo.AttrPathVo;
import com.renchao.mall.product.vo.AttrRespVo;
import com.renchao.mall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.renchao.mall.product.service.AttrService;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.R;



/**
 * 商品属性
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 12:59:14
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService attrValueService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/base/listforspu/{spuId}")
    public R baseListForspu(@PathVariable Long spuId){
        List<ProductAttrValueEntity> list = attrValueService.listBySpuId(spuId);

        return R.ok().put("page", list);
    }

    /**
     * 列表
     */
    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseList(@RequestParam Map<String, Object> params,@PathVariable String attrType,@PathVariable Long catelogId){
        PageUtils page = attrService.queryPage(params,attrType,catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
//		AttrEntity attr = attrService.getById(attrId);
        AttrPathVo attr = attrService.getAttr(attrId);
        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attr){

//		attrService.save(attr);
		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrRespVo attr){
//		attrService.updateById(attr);
		attrService.updateDetails(attr);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update/{spuId}")
    public R updateBySpuId(@PathVariable Long spuId,@RequestBody List<ProductAttrValueEntity> attrEntities){
        attrValueService.updateBySpuId(spuId,attrEntities);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
