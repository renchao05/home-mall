package com.renchao.gulimall.product.exception;

import com.renchao.common.utils.R;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice({"com.renchao.gulimall.product.app"})
public class GulimallExceptionControllerAdvice {
    /**
     * 数据校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        Map<String, String> map = new HashMap<>();
        result.getFieldErrors().forEach(item -> map.put(item.getField(), item.getDefaultMessage()));
        return R.error(400, "数据不合法！！").put("data", map);
    }
}
