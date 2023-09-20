package com.renchao.mall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.mall.product.dao.SpuInfoDescDao;
import com.renchao.mall.product.entity.SpuInfoDescEntity;
import com.renchao.mall.product.service.SpuInfoDescService;
import org.springframework.util.CollectionUtils;


@Service("spuInfoDescService")
public class SpuInfoDescServiceImpl extends ServiceImpl<SpuInfoDescDao, SpuInfoDescEntity> implements SpuInfoDescService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoDescEntity> page = this.page(
                new Query<SpuInfoDescEntity>().getPage(params),
                new QueryWrapper<SpuInfoDescEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSpuDecript(List<String> spuDecript, Long spuId) {
        if (CollectionUtils.isEmpty(spuDecript)) {
            return;
        }
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(spuId);
        String decript = String.join(",", spuDecript);
        descEntity.setDecript(decript);
        this.save(descEntity);
    }

}