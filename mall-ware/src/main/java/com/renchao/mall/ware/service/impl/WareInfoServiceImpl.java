package com.renchao.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.mall.ware.dao.WareInfoDao;
import com.renchao.mall.ware.entity.WareInfoEntity;
import com.renchao.mall.ware.service.WareInfoService;
import org.springframework.util.StringUtils;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                new QueryWrapper<WareInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageWrapper(Map<String, Object> params) {
        Object key = params.get("key");
        LambdaQueryWrapper<WareInfoEntity> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            wrapper.eq(WareInfoEntity::getId, key).or()
                    .like(WareInfoEntity::getName, key).or()
                    .like(WareInfoEntity::getAddress, key).or()
                    .like(WareInfoEntity::getAreacode, key);
        }
        IPage<WareInfoEntity> page = this.page(new Query<WareInfoEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

}