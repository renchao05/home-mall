package com.renchao.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.renchao.common.utils.PageUtils;
import com.renchao.gulimall.ware.entity.PurchaseEntity;
import com.renchao.gulimall.ware.vo.MergeVo;
import com.renchao.gulimall.ware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 17:48:45
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceive(Map<String, Object> params);

    void merge(MergeVo merge);

    void updateByReceived(List<Long> pIds);

    void updateByDone(PurchaseDoneVo doneVo);
}

