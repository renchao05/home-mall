package com.renchao.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.utils.PageUtils;
import com.renchao.mall.ware.entity.WareInfoEntity;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 17:48:45
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageWrapper(Map<String, Object> params);
}

