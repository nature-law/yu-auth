package com.xia.yuauth.controller;

import com.xia.yuauth.common.exception.ServiceException;
import com.xia.yuauth.controller.web.annotation.ResultConvert;
import com.xia.yuauth.controller.web.vo.Result;
import com.xia.yuauth.service.UserService;
import com.xia.yuauth.service.VerifyCodeService;
import com.xia.yuauth.service.impl.VerifyCodeMailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * description: 邮件发送 验证码
 *
 * @author wanghaoxin
 * date     2021/12/12 12:11
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/v1/sys")
@ResultConvert
public class VerifyCodeController {

    @Resource(type = VerifyCodeMailServiceImpl.class)
    private VerifyCodeService mailService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/mail/verify_code")
    public String send(@Param(value = "account") String account) {
        Integer exists = userService.isExists(account, "mail");
        if (exists != null && exists == 1) {
            return mailService.sendMessage(account.trim());
        } else {
            throw new ServiceException(new Result<>().withCode("A0201"));
        }
    }
}
