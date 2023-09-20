package com.renchao.gulimall.product.feign;

import com.renchao.common.to.MemberPriceTo;
import com.renchao.common.to.SkuFullReductionTo;
import com.renchao.common.to.SkuLadderTo;
import com.renchao.common.to.SpuBoundsTo;
import com.renchao.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@FeignClient("mall-coupon") // 使用openfeign
public interface CouponFeignService {

    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundsTo spuBounds);

    @PostMapping("/coupon/skuladder/save")
    R saveSkuLadder(@RequestBody SkuLadderTo skuLadder);

    @PostMapping("/coupon/skufullreduction/save")
    R saveSkuFullReduction(@RequestBody SkuFullReductionTo skuFullReduction);

    @PostMapping("/coupon/memberprice/save/batch")
    R saveMemberPrices(@RequestBody List<MemberPriceTo> memberPrices);
}
