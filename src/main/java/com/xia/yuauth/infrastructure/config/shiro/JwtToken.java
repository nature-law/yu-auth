package com.xia.yuauth.infrastructure.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * description: JWTToken
 *
 * @author wanghaoxin
 * date     2021/12/27 21:42
 * @version 1.0
 */
public class JwtToken implements AuthenticationToken, DataAutoToken {
    private String token;

    public JwtToken() {
    }

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public String name() {
        return TokenNameEnum.JWT.name();
    }
}
