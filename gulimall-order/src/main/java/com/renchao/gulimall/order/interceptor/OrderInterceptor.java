package com.renchao.gulimall.order.interceptor;

import com.renchao.common.constant.AuthConstant;
import com.renchao.common.to.UserTo;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderInterceptor implements HandlerInterceptor {
    public static ThreadLocal<UserTo> threadLocal = new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        UserTo user = (UserTo) request.getSession().getAttribute(AuthConstant.USER);
        if (user == null) {
            request.getSession().setAttribute("msg","请先登录！");
            response.sendRedirect("http://auth.gulimall.com/login.html");
            return false;
        }
        threadLocal.set(user);
        return true;
    }
}
