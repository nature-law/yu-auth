package com.xia.yuauth.interceptor;

import com.xia.yuauth.util.IpUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description: ip拦截器
 *
 * @author Administrator
 * @date 2021/2/10 10:47
 */
public class IpInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String realIP = IpUtil.getRealIP(request);
        if (IpUtil.isRegisterIp(realIP)) {
            return true;
        }
        // todo 不做过滤处理
        return true;
    }
}
