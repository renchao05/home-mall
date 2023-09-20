package com.renchao.mall.member.dao;

import com.renchao.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 16:34:56
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
