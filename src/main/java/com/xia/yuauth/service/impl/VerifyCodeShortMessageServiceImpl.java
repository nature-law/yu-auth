package com.xia.yuauth.service.impl;

import com.xia.yuauth.service.VerifyCodeService;
import org.springframework.stereotype.Service;

/**
 * description: 短信验证码服务
 *
 * @author wanghaoxin
 * date     2021/12/12 12:20
 * @version 1.0
 */
@Service(value = "verifyCodeShortMessageServiceImpl")
public class VerifyCodeShortMessageServiceImpl implements VerifyCodeService {

    @Override
    public String sendMessage(String account) {
        return null;
    }
}
