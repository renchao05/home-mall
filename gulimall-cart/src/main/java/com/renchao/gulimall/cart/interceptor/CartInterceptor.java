package com.renchao.gulimall.cart.interceptor;


import com.renchao.common.constant.AuthConstant;
import com.renchao.common.to.UserTo;
import com.renchao.gulimall.cart.constant.CartConstant;
import com.renchao.gulimall.cart.vo.UserInfoTo;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public class CartInterceptor implements HandlerInterceptor {

    public static ThreadLocal<UserInfoTo> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        UserTo user =(UserTo) session.getAttribute(AuthConstant.USER);
        UserInfoTo userInfo = new UserInfoTo();
        // 如果登录
        if (user != null) {
            userInfo.setUserId(user.getUserId());
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CartConstant.TEMP_USER_COOKIE_NAME)) {
                    userInfo.setUserKey(cookie.getValue());
                }
            }
        }
        if (StringUtils.isEmpty(userInfo.getUserKey())) {
            String userKey = UUID.randomUUID().toString();
            userInfo.setUserKey(userKey);
            Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME, userKey);
            cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIMEOUT);
            cookie.setDomain(CartConstant.TEMP_USER_COOKIE_DOMAIN);
            response.addCookie(cookie);
        }
        threadLocal.set(userInfo);
        return true;
    }
}
