package com.xia.yuauth.controller;

import com.xia.yuauth.common.exception.ServiceException;
import com.xia.yuauth.controller.web.vo.Result;
import com.xia.yuauth.domain.model.user.User;
import com.xia.yuauth.infrastructure.middleware.GlobalCache;
import com.xia.yuauth.infrastructure.utils.JWTUtils;
import com.xia.yuauth.service.TokenService;
import com.xia.yuauth.service.UserService;
import com.xia.yuauth.service.impl.RefreshTokenServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * description: 登录
 *
 * @author wanghaoxin
 * date     2021/12/23 22:48
 * @version 1.0
 */
@RestController
@RequestMapping("/v1")
public class LoginController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private GlobalCache globalCache;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, String> login(String account, String password, boolean rememberMe, @NonNull String verifyCode) {
        String code = (String) globalCache.get(account);
        Map<String, String> result = new HashMap<>(2);
        if (verifyCode.equals(code)) {
            Subject subject = SecurityUtils.getSubject();
            AuthenticationToken token = new UsernamePasswordToken(account, password, rememberMe);
            subject.login(token);
            User user = getUser(account);
            String refreshToken = tokenService.newRefreshToken();
            String accessToken = JWTUtils.getToken(user);
            result.put("refresh_token", refreshToken);
            result.put("access_token", accessToken);
        } else {
            throw new ServiceException(new Result<>().withCode("A0240"));
        }
        return result;
    }

    private User getUser(String username) {
        User user = new User();
        User userByMail = userService.getUserByMail(username);
        return userByMail;
    }

    @GetMapping("/access_token")
    public String refreshAccessToken(String refreshToken, String username) {
        int val = (int) globalCache.get(RefreshTokenServiceImpl.REFRESH_TOKEN_PREFIX + refreshToken);
        if (val == RefreshTokenServiceImpl.EXIST) {
            User user = getUser(username);
            return JWTUtils.getToken(user);
        } else {
            throw new ServiceException(new Result<>().withCode("A0230").withDesc("请重新登录。"));
        }
    }
}
