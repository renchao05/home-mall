package com.renchao.mall.ware.exception;

import com.renchao.common.exception.WareLockException;
import com.renchao.common.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice({"com.renchao.mall.ware"})
public class MallExceptionControllerAdvice {
    /**
     * 库存锁定异常
     */
    @ExceptionHandler(WareLockException.class)
    public R handleValidException(WareLockException e) {
        return R.error(1,e.getMessage());
    }
}
