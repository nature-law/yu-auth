package com.xia.yuauth.controller.web.handler;

import com.xia.yuauth.constants.enums.ResultStatusEnum;
import com.xia.yuauth.controller.web.Result;
import com.xia.yuauth.controller.web.annotation.ResultConvert;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * description: 分页结果转换
 *
 * @author Administrator
 * @date 2021/12/7 12:41
 */
@RestControllerAdvice
public class ResultHandler implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}


	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
	                              Class<? extends HttpMessageConverter<?>> selectedConverterType,
	                              ServerHttpRequest request, ServerHttpResponse response) {
		boolean isMethodDeclared = Objects.requireNonNull(returnType.getMethod()).isAnnotationPresent(ResultConvert.class);
		boolean isClassDeclared = Objects.requireNonNull(returnType.getMethod().getDeclaringClass()).isAnnotationPresent(ResultConvert.class);
		if (isMethodDeclared || isClassDeclared) {
			if (!(body instanceof Result)) {
				return new Result<>().withCode(ResultStatusEnum.SUCCESS.getCode()).withData(body);
			}
		}
		return body;
	}


}