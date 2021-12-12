package com.xia.yuauth.service.impl;

import com.xia.yuauth.common.exception.ServiceException;
import com.xia.yuauth.infrastructure.utils.VerifyCodeUtils;
import com.xia.yuauth.service.VerifyCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * description: 邮件验证码服务
 *
 * @author wanghaoxin
 * date     2021/12/12 12:16
 * @version 1.0
 */
@Service(value = "verifyCodeMailServiceImpl")
public class VerifyCodeMailServiceImpl implements VerifyCodeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyCodeMailServiceImpl.class);

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Override
    public String sendMessage(String account) {
        String code = String.valueOf(VerifyCodeUtils.getRandomChars());
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(mailUsername);
            //邮件接收人
            messageHelper.setTo(account);
            //邮件主题
            message.setSubject("【Yu 验证码】");
            //邮件内容
            String content = "尊敬的用户:<br /><hr>" + "您的邮箱验证代码为:<h3>" + code + "</h3>请在 5分钟 内完成验证!";
            messageHelper.setText(content, true);
            //发送
            sender.send(message);
        } catch (Exception e) {
            throw new ServiceException(e, "发送邮件失败");
        }
        return "成功";
    }


}
