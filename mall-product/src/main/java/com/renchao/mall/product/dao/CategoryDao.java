package com.renchao.mall.product.dao;

import com.renchao.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 11:37:42
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
