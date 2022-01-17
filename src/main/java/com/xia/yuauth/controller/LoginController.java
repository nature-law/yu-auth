package com.xia.yuauth.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.xia.yuauth.common.exception.ServiceException;
import com.xia.yuauth.controller.params.LoginParams;
import com.xia.yuauth.controller.web.annotation.ResultConvert;
import com.xia.yuauth.controller.web.vo.Result;
import com.xia.yuauth.domain.model.user.User;
import com.xia.yuauth.infrastructure.config.shiro.HashedCredentialsMatcherUtils;
import com.xia.yuauth.infrastructure.config.shiro.LoginToken;
import com.xia.yuauth.infrastructure.middleware.GlobalCache;
import com.xia.yuauth.infrastructure.utils.JWTUtils;
import com.xia.yuauth.service.TokenService;
import com.xia.yuauth.service.UserService;
import com.xia.yuauth.service.VerifyCodeService;
import com.xia.yuauth.service.impl.RefreshTokenServiceImpl;
import com.xia.yuauth.service.impl.VerifyCodeMailServiceImpl;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@ResultConvert
@RequestMapping("/login")
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Resource(type = VerifyCodeMailServiceImpl.class)
    private VerifyCodeService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private GlobalCache globalCache;

    @PostMapping("/index")
    public Map<String, String> login(@RequestBody LoginParams params) {
        Map<String, String> result = new HashMap<>(2);
        try {
            Subject subject = SecurityUtils.getSubject();
            LoginToken token = new LoginToken(params);
            subject.login(token);
            User user = getUser(token.getUsername());
            String refreshToken = tokenService.newRefreshToken(user);
            String accessToken = JWTUtils.getToken(user);
            result.put("refresh_token", refreshToken);
            result.put("access_token", accessToken);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(new Result<>().withCode("A0240"), e.getMessage());
        }
        return result;
    }




    @GetMapping(value = "/mail/verify_code")
    public String send(@Param(value = "account") String account) {
        Integer exists = userService.isExists(account, "mail");
        if (exists != null && exists == 1) {
            return mailService.sendMessage(account.trim());
        } else {
            throw new ServiceException(new Result<>().withCode("A0201"));
        }
    }

    private User getUser(String username) {
        User userByMail = userService.getUserByMail(username);
        return userByMail;
    }

    @GetMapping("/access_token")
    public String refreshAccessToken(String refreshToken) {
        int val = (int) globalCache.get(RefreshTokenServiceImpl.REFRESH_TOKEN_PREFIX + refreshToken);
        if (val == RefreshTokenServiceImpl.EXIST) {
            boolean isAllow = JWTUtils.validateToken(refreshToken);
            if (isAllow) {
                DecodedJWT decodedJWT = JWTUtils.decodeToken(refreshToken);
                String username = decodedJWT.getClaim(JWTUtils.USER_ID).asString();
                User user = getUser(username);
                return JWTUtils.getToken(user);
            } else {
                throw new ServiceException(new Result<>().withCode("A0310"));
            }
        } else {
            throw new ServiceException(new Result<>().withCode("A0230"));
        }
    }
}
