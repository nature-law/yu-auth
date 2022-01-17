package com.xia.yuauth.infrastructure.config.shiro;

import com.xia.yuauth.infrastructure.utils.JWTUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * description:
 *
 * @author wanghaoxin
 * date     2022/1/14 16:24
 * @version 1.0
 */
public class JwtRealm extends AuthorizingRealm implements DataAutoToken {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        // verify token
        String tokenValue = jwtToken.getCredentials().toString();
        if (JWTUtils.validateToken(tokenValue)) {
            return new SimpleAuthenticationInfo(jwtToken, tokenValue, super.getName());
        }
        return null;
    }

    /**
     * description 名称
     *
     * @return 名称
     */
    @Override
    public String name() {
        return TokenNameEnum.JWT.name();
    }
}
