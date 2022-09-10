package com.renchao.gulimall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.gulimall.coupon.dao.SeckillSkuRelationDao;
import com.renchao.gulimall.coupon.entity.SeckillSkuRelationEntity;
import com.renchao.gulimall.coupon.service.SeckillSkuRelationService;


@Service("seckillSkuRelationService")
public class SeckillSkuRelationServiceImpl extends ServiceImpl<SeckillSkuRelationDao, SeckillSkuRelationEntity> implements SeckillSkuRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<SeckillSkuRelationEntity> wrapper = new LambdaQueryWrapper<>();
        String id = (String) params.get("promotionSessionId");
        wrapper.eq(!StringUtils.isEmpty(id),SeckillSkuRelationEntity::getPromotionSessionId, params.get("promotionSessionId"));
        IPage<SeckillSkuRelationEntity> page = this.page(new Query<SeckillSkuRelationEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public List<SeckillSkuRelationEntity> listBySessionId(Long id) {
        LambdaQueryWrapper<SeckillSkuRelationEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(id != null, SeckillSkuRelationEntity::getPromotionSessionId, id);
        return this.list(wrapper);
    }

}