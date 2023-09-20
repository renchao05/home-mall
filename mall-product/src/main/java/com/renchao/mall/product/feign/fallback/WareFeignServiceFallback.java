package com.renchao.mall.product.feign.fallback;

import com.renchao.common.utils.R;
import com.renchao.mall.product.feign.WareFeignService;
import org.springframework.stereotype.Service;

@Service
public class WareFeignServiceFallback implements WareFeignService {
    @Override
    public R getStock(Long skuId) {
        return R.error(444,"请求量过大，或服务端异常！");
    }
}
