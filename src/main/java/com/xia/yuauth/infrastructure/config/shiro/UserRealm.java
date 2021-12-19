package com.xia.yuauth.infrastructure.config.shiro;

import com.xia.yuauth.common.exception.ServiceException;
import com.xia.yuauth.controller.web.vo.Result;
import com.xia.yuauth.domain.model.user.User;
import com.xia.yuauth.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * description: Shiro realm 权限信息验证器
 *
 * @author wanghaoxin
 * date     2021/12/7 23:41
 * @version 1.0
 */

@Component(value = "userRealm")
public class UserRealm extends AuthorizingRealm {
    private static final Logger LOGGER = getLogger(UserRealm.class);

    @Autowired
    private UserService userService;

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
        if (token instanceof UsernamePasswordToken) {
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
            String username = usernamePasswordToken.getUsername();
            User user = userService.getUserByMail(username);

            if (user == null) {
                throw new ServiceException(new Result<>().withCode("A0201"));
            }
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            return info;
        } else {
            //todo 如果是BearerToken 则 另做处理
            return null;
        }
    }
}
