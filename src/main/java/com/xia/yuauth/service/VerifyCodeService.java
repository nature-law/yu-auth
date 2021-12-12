package com.xia.yuauth.service;

/**
 * description: 验证码服务
 *
 * @author wanghaoxin
 * date     2021/12/12 12:15
 * @version 1.0
 */
public interface VerifyCodeService {
    String sendMessage(String account);

}
