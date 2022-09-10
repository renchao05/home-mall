package com.renchao.gulimall.product.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renchao.gulimall.product.entity.CategoryEntity;
import com.renchao.gulimall.product.service.CategoryService;
import com.renchao.common.utils.R;



/**
 * 商品三级分类
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 12:59:13
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查出所有分类及子分类，按照树形结构组装起来
     */
    @RequestMapping("/list/tree")
    public R list(){
        List<CategoryEntity> category = categoryService.listWithTree();

        return R.ok().put("data", category);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
//		categoryService.updateById(category);
		categoryService.updateDetails(category);


        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update/sort")
    public R updateSort(@RequestBody List<CategoryEntity> menus){
        categoryService.updateBatchById(menus);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
		categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }

}
