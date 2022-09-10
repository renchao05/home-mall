package com.renchao.gulimall.product.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.renchao.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.renchao.gulimall.product.entity.AttrEntity;
import com.renchao.gulimall.product.service.AttrAttrgroupRelationService;
import com.renchao.gulimall.product.service.CategoryService;
import com.renchao.gulimall.product.vo.AttrGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.renchao.gulimall.product.entity.AttrGroupEntity;
import com.renchao.gulimall.product.service.AttrGroupService;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.R;


/**
 * 属性分组
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 12:59:13
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrAttrgroupRelationService relationService;

    @PostMapping("attr/relation")
    public R attrRelation(@RequestBody List<AttrAttrgroupRelationEntity> relations) {
        relationService.saveBatch(relations);
        return R.ok();
    }

    /**
     * 获取关联属性列表
     */
    @PostMapping("/attr/relation/delete")
    public R deleteAttrRelation(@RequestBody List<AttrAttrgroupRelationEntity> relations) {
        relationService.removeByRelations(relations);
        return R.ok();
    }


    /**
     * 获取关联属性列表
     */
    @GetMapping("/{groupId}/noattr/relation")
    public R noAttrRelation(@RequestParam Map<String, Object> params,@PathVariable Long groupId) {
        PageUtils page = relationService.noAttrList(params,groupId);
        return R.ok().put("page",page);
    }

    /**
     * 获取关联属性列表
     */
    @GetMapping("/{groupId}/attr/relation")
    public R attrRelation(@PathVariable Long groupId) {
        List<AttrEntity> attrs = relationService.attrListByGroupId(groupId);
        return R.ok().put("data",attrs);
    }


    /**
     * 获取关联属性列表
     */
    @GetMapping("/{CatId}/withattr")
    public R attrGroupWithAttr(@PathVariable Long CatId) {
        List<AttrGroupVo> attrs = attrGroupService.listAttrGroupWithAttrByCatId(CatId);
        return R.ok().put("data",attrs);
    }


    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params, @PathVariable Long catelogId) {
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params, catelogId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        if (attrGroup != null) {
            Long[] catelogPath = categoryService.findCatelogPath(attrGroup.getCatelogId());
            attrGroup.setCatelogPath(catelogPath);
        }
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
