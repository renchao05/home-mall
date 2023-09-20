package com.renchao.mall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.renchao.common.to.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.renchao.mall.member.entity.MemberEntity;
import com.renchao.mall.member.service.MemberService;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.R;



/**
 * 会员
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 16:34:56
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 用户注册
     * @param member
     * @return
     */
    @PostMapping("/register")
    public R register(@RequestBody MemberEntity member) {
        memberService.register(member);
        return R.ok();
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public R login(@RequestBody MemberEntity member) {
        UserTo userTo = memberService.login(member);

        return R.ok().put("user", userTo);
    }

}
