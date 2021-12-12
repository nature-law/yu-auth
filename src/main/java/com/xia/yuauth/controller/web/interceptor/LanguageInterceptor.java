package com.xia.yuauth.controller.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description: 语言拦截器
 *
 * @author wanghaoxin
 * date     2021/11/28 20:20
 * @version 1.0
 */
public class LanguageInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
}
