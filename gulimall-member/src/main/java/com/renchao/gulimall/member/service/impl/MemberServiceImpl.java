package com.renchao.gulimall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renchao.common.exception.GulimallLoginException;
import com.renchao.common.exception.GulimallRegisterException;
import com.renchao.common.to.UserTo;
import com.renchao.common.utils.R;
import com.renchao.gulimall.member.entity.MemberReceiveAddressEntity;
import com.renchao.gulimall.member.service.MemberLevelService;
import com.renchao.gulimall.member.service.MemberReceiveAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.gulimall.member.dao.MemberDao;
import com.renchao.gulimall.member.entity.MemberEntity;
import com.renchao.gulimall.member.service.MemberService;
import org.springframework.transaction.annotation.Transactional;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    private MemberLevelService memberLevelService;

    @Autowired
    private MemberReceiveAddressService memberReceiveAddressService;

    @Override
    public void checkPhoneUnique(String phone) {
        LambdaQueryWrapper<MemberEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberEntity::getMobile, phone);
        int count = this.count(wrapper);
        if (count > 0) {
            throw new GulimallRegisterException("手机已被使用！");
        }
    }

    @Override
    public void checkUsernameUnique(String username) {
        LambdaQueryWrapper<MemberEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberEntity::getUsername, username);
        int count = this.count(wrapper);
        if (count > 0) {
            throw new GulimallRegisterException("用户名已存在！");
        }
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 用户注册
     * @param member
     */
    @Transactional
    @Override
    public void register(MemberEntity member) {
        // 检查手机和用户名是否唯一，如果不是唯一，直接抛异常，由异常处理
        this.checkPhoneUnique(member.getMobile());
        this.checkUsernameUnique(member.getUsername());
        Long levelId = memberLevelService.getDefaultMemberLevelId();

        // 设置会员默认等级
        member.setLevelId(levelId);
        // 设置默认昵称
        member.setNickname("商城宝贝");

        // 密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        member.setPassword(encoder.encode(member.getPassword()));

        this.save(member);

        // TODO 设置默认收货地址，这里直接写死，有需要再修改
        MemberReceiveAddressEntity address = new MemberReceiveAddressEntity();
        address.setMemberId(member.getId());
        address.setName(member.getNickname());
        address.setPhone(member.getMobile());
        address.setProvince("江苏省");
        address.setCity("南通市");
        address.setRegion("海门区");
        address.setDetailAddress("三星镇");
        address.setDefaultStatus(1);
        memberReceiveAddressService.save(address);
    }


    /**
     * 用户登录
     * @param member
     */
    @Override
    public UserTo login(MemberEntity member) {
        LambdaQueryWrapper<MemberEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberEntity::getUsername, member.getUsername()).or().eq(MemberEntity::getMobile, member.getUsername());
        MemberEntity one = this.getOne(wrapper);
        if (one == null) {
            throw new GulimallLoginException("用户不存在！");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(member.getPassword(), one.getPassword())) {
            throw new GulimallLoginException("用户名或者密码错误！");
        }
        UserTo userTo = new UserTo();
        userTo.setUserId(one.getId());
        userTo.setUsername(one.getUsername());
        userTo.setNickname(one.getNickname());
        return userTo;
    }

}