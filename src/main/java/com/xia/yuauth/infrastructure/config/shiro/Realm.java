package com.xia.yuauth.infrastructure.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * description: Shiro realm 权限信息验证器
 *
 * @author wanghaoxin
 * date     2021/12/7 23:41
 * @version 1.0
 */
@Component(value = "authorizer")
public class Realm extends AuthorizingRealm {
    private static final Logger LOGGER = getLogger(Realm.class);

    /**
     * 巨坑 建议重写
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * description: 登录认证
     *
     * @param token 认证token
     * @return : org.apache.shiro.authc.AuthenticationInfo 登录信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        LOGGER.info("------------------登录认证-------------------");
        return null;
    }
}
