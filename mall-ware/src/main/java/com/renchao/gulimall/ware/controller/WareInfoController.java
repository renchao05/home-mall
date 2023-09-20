package com.renchao.gulimall.ware.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.renchao.gulimall.ware.entity.WareInfoEntity;
import com.renchao.gulimall.ware.service.WareInfoService;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 仓库信息
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 17:48:45
 */
@RestController
@RequestMapping("ware/wareinfo")
public class WareInfoController {
    @Autowired
    private WareInfoService wareInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
//        PageUtils page = wareInfoService.queryPage(params);
        PageUtils page = wareInfoService.queryPageWrapper(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        WareInfoEntity wareInfo = wareInfoService.getById(id);

        return R.ok().put("wareInfo", wareInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WareInfoEntity wareInfo) {
        wareInfoService.save(wareInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WareInfoEntity wareInfo) {
        wareInfoService.updateById(wareInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        wareInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 测试
     */
    @ResponseBody
    @PostMapping("/getFare")
    public String getFare(@RequestBody Map<String,String> address) {
        // 使用手机号最后一位数模拟运费
        String phone = address.get("phone");
        return phone.substring(phone.length() - 1);
    }
}
