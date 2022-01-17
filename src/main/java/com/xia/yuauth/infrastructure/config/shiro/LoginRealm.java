package com.xia.yuauth.infrastructure.config.shiro;

import com.xia.yuauth.domain.model.user.User;
import com.xia.yuauth.infrastructure.middleware.GlobalCache;
import com.xia.yuauth.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description:
 *
 * @author wanghaoxin
 * date     2022/1/14 16:24
 * @version 1.0
 */
public class LoginRealm extends AuthorizingRealm implements DataAutoToken {

    @Autowired
    private UserService userService;

    @Autowired
    private GlobalCache globalCache;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof LoginToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        LoginToken loginToken = (LoginToken) token;
        String username = loginToken.getUsername();
        String verifyCode = loginToken.getVerifyCode();

        String verifyCodeRedis = globalCache.get(username).toString();
        if (verifyCode.equals(verifyCodeRedis)) {
            User userByMail = userService.getUserByMail(username);
            if (userByMail != null) {
                return new SimpleAuthenticationInfo(userByMail.getMail(), userByMail.getPassword(), super.getName());
            }
        }
        return null;
    }


    @Override
    public String name() {
        return TokenNameEnum.LOGIN.name();
    }
}
