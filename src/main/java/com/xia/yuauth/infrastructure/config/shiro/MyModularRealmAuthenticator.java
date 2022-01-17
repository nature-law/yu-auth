package com.xia.yuauth.infrastructure.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

/**
 * description: 用于根据Token 的name 匹配相应的Realm
 *
 * @author wanghaoxin
 * date     2022/1/16 11:44
 * @version 1.0
 */
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        assertRealmsConfigured();
        DataAutoToken dataAutoToken = (DataAutoToken) authenticationToken;

        Realm realm = getRealm(dataAutoToken);
        return doSingleRealmAuthentication(realm, authenticationToken);
    }

    private Realm getRealm(DataAutoToken dataAutoToken) {
        for (Realm realm : getRealms()) {
            if (realm instanceof DataAutoToken) {
                // 根据定义的realm的name和dataAutoToken的name匹配相应的realm
                if (dataAutoToken.name().equals(((DataAutoToken) realm).name())) {
                    return realm;
                }
            }
        }
        return null;
    }
}
