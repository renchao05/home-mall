package com.renchao.mall.order.exception;

import com.renchao.common.exception.SubmitOrderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@ControllerAdvice({"com.renchao.mall.order"})
public class MallExceptionControllerAdvice {
    /**
     * 提交订单异常
     */
    @ExceptionHandler(SubmitOrderException.class)
    public String handleValidException(SubmitOrderException e,
                                       RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", e.getMessage());
        return "redirect:http://order.renchao05.top/toTrade";
    }
}
