package com.xia.yuauth.infrastructure.config.shiro;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;

/**
 * description:
 *
 * @author wanghaoxin
 * date     2021/12/23 23:57
 * @version 1.0
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {
    @Override
    protected String getAuthzHeader(ServletRequest request) {
        return super.getAuthzHeader(request);
    }
}
