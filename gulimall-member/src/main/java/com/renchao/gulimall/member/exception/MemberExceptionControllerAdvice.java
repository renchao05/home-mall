package com.renchao.gulimall.member.exception;

import com.renchao.common.exception.GulimallLoginException;
import com.renchao.common.exception.GulimallRegisterException;
import com.renchao.common.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionControllerAdvice {

    /**
     * 注册异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(GulimallRegisterException.class)
    public R registerException(GulimallRegisterException e) {

        return R.error(e.getMessage());
    }

    /**
     * 登录异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(GulimallLoginException.class)
    public R loginException(GulimallLoginException e) {

        return R.error(e.getMessage());
    }
}
