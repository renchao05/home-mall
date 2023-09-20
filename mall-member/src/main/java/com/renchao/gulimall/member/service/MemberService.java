package com.renchao.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.to.UserTo;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.R;
import com.renchao.gulimall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 16:34:56
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(MemberEntity member);

    void checkPhoneUnique(String phone);

    void checkUsernameUnique(String username);

    UserTo login(MemberEntity member);
}

