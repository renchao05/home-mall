package com.renchao.gulimall.ware.exception;

import com.renchao.common.exception.SubmitOrderException;
import com.renchao.common.exception.WareLockException;
import com.renchao.common.utils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestControllerAdvice({"com.renchao.gulimall.ware"})
public class GulimallExceptionControllerAdvice {
    /**
     * 库存锁定异常
     */
    @ExceptionHandler(WareLockException.class)
    public R handleValidException(WareLockException e) {
        return R.error(1,e.getMessage());
    }
}
