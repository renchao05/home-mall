package com.renchao.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renchao.mall.coupon.entity.SeckillSkuRelationEntity;
import com.renchao.mall.coupon.service.SeckillSkuRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.mall.coupon.dao.SeckillSessionDao;
import com.renchao.mall.coupon.entity.SeckillSessionEntity;
import com.renchao.mall.coupon.service.SeckillSessionService;


@Service("seckillSessionService")
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity> implements SeckillSessionService {

    @Autowired
    private SeckillSkuRelationService skuRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeckillSessionEntity> page = this.page(
                new Query<SeckillSessionEntity>().getPage(params),
                new QueryWrapper<SeckillSessionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SeckillSessionEntity> getLast3DaysSession() {
        LambdaQueryWrapper<SeckillSessionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(SeckillSessionEntity::getStartTime, startTimeOfTheDay(), endTimeAfter3Days());
        List<SeckillSessionEntity> list = this.list(wrapper);
        for (SeckillSessionEntity session : list) {
            List<SeckillSkuRelationEntity> relations = skuRelationService.listBySessionId(session.getId());
            session.setRelationSkus(relations);
        }
        return list;
    }

    private String startTimeOfTheDay() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return time.format(pattern);
    }

    private String endTimeAfter3Days() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.MAX);
        return time.format(pattern);
    }
}