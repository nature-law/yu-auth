package com.xia.yuauth.infrastructure.config.shiro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xia.yuauth.controller.web.vo.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * description:
 *
 * @author wanghaoxin
 * date     2021/12/23 23:57
 * @version 1.0
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    private static final String AUTHORIZATION = "Authorization";

    @Value("auth.enable")
    private static boolean ENABLE_AUTH;

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个 option请求，这里我们给 option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        if (!ENABLE_AUTH) {
            return true;
        }

        if (isLoginAttempt(request, response)) {
            return executeLogin(request, response);
        } else {
            return false;
        }
    }

    /**
     * description: 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可, 如果没有token 证明是需要登录
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(AUTHORIZATION);
        return token != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 得到token
        String token = httpServletRequest.getHeader(AUTHORIZATION);
        try {
            JwtToken jwtToken = new JwtToken(token);
            SecurityUtils.getSubject().login(jwtToken);
            // 提交给realm进行登入，如果错误他会抛出异常并被捕获
            // 如果没有抛出异常则代表登入成功，返回true
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        LOGGER.debug("Authentication required: sending 401 Authentication challenge response.");
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
//        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpResponse.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = httpResponse.getWriter()) {
            Result<Object> result = new Result<>().withCode("A0220").withData(null);
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(result));
            out.print(jsonObject);
        } catch (IOException e) {
            LOGGER.error("sendChallenge error：", e);
        }
        return false;
    }

    @Override
    protected String getAuthzHeader(ServletRequest request) {
        return super.getAuthzHeader(request);
    }
}
