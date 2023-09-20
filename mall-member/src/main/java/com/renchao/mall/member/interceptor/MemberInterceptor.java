package com.renchao.mall.member.interceptor;

import com.renchao.common.constant.AuthConstant;
import com.renchao.common.to.UserTo;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberInterceptor implements HandlerInterceptor {
    public static ThreadLocal<UserTo> threadLocal = new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        UserTo user = (UserTo) request.getSession().getAttribute(AuthConstant.USER);
        if (user == null) {
            request.getSession().setAttribute("msg","请先登录！");
            response.sendRedirect("http://auth.renchao05.top/login.html");
            return false;
        }
        threadLocal.set(user);
        return true;
    }
}
