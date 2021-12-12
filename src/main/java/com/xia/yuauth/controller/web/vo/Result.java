package com.xia.yuauth.controller.web.vo;

import com.xia.yuauth.common.utils.SpringBeanUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * description: 返回页面结果
 *
 * @author wanghaoxin
 * date     2021/11/28 10:31
 * @version 1.0
 */
public class Result<T> {
    private String code;

    private T data;

    private String desc;

    public Result<T> withCode(String code) {
        this.code = code;
        MessageSource messageSource = SpringBeanUtil.getBeanByClass(MessageSource.class);
        String message = messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        this.desc = message;
        return this;
    }

    public Result<T> withData(T data) {
        this.data = data;
        return this;
    }

    public Result<T> withDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
