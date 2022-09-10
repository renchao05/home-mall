package com.renchao.gulimall.product.feign;


import com.renchao.common.to.es.SkuEsModel;
import com.renchao.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Service
@FeignClient("gulimall-search") // 使用openfeign
public interface SearchFeignService {

    @PostMapping("/search/save/product")
    R saveProduct(@RequestBody List<SkuEsModel> models);
}
