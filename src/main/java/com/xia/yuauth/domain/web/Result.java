package com.xia.yuauth.domain.web;

import com.xia.yuauth.util.SpringBeanUtil;
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

}
