package com.renchao.mall.member.exception;

import com.renchao.common.exception.MallLoginException;
import com.renchao.common.exception.MallRegisterException;
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
    @ExceptionHandler(MallRegisterException.class)
    public R registerException(MallRegisterException e) {

        return R.error(e.getMessage());
    }

    /**
     * 登录异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(MallLoginException.class)
    public R loginException(MallLoginException e) {

        return R.error(e.getMessage());
    }
}
