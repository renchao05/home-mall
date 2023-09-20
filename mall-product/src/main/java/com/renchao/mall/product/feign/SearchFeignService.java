package com.renchao.mall.product.feign;


import com.renchao.common.to.es.SkuEsModel;
import com.renchao.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
@FeignClient("mall-search") // 使用openfeign
public interface SearchFeignService {

    @PostMapping("/search/save/product")
    R saveProduct(@RequestBody List<SkuEsModel> models);
}
