package com.renchao.mall.auth.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class AuthExceptionControllerAdvice {
    /**
     * 数据校验异常
     */
    @ExceptionHandler(BindException.class)
    public String handleValidException(BindException e,
                                       RedirectAttributes attributes) {
        BindingResult result = e.getBindingResult();
        Map<String, String> errors = result.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        attributes.addFlashAttribute("errors", errors);
        return "redirect:http://auth.renchao05.top/reg.html";
    }
}
