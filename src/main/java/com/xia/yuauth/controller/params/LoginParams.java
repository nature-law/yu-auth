package com.xia.yuauth.controller.params;

/**
 * description: 登录参数
 *
 * @author wanghaoxin
 * date     2022/1/16 12:27
 * @version 1.0
 */
public class LoginParams {
    private String username;
    private char[] password;

    private String verifyCode;
    private boolean rememberMe;

    private String host;

    public LoginParams() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
