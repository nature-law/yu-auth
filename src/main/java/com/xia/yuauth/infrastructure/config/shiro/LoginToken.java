package com.xia.yuauth.infrastructure.config.shiro;

import com.xia.yuauth.controller.params.LoginParams;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * description: 登录认证token
 *
 * @author wanghaoxin
 * date     2022/1/14 23:21
 * @version 1.0
 */
public class LoginToken extends UsernamePasswordToken implements DataAutoToken {

    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    /**
     * JavaBeans compatible no-arg constructor.
     */
    public LoginToken() {
    }

    /**
     * JavaBeans compatible no-arg constructor.
     */
    public LoginToken(LoginParams params) {
        super(params.getUsername(), params.getPassword(), params.isRememberMe(), params.getHost());
        assert params.getVerifyCode() != null;
        this.verifyCode = params.getVerifyCode();
    }

    @Override
    public String name() {
        return TokenNameEnum.LOGIN.name();
    }
}
