package com.xia.yuauth.controller.web.handler;

import com.xia.yuauth.common.enums.ResultStatusEnum;
import com.xia.yuauth.common.enums.converter.PagePageResultConverter;
import com.xia.yuauth.controller.web.vo.PageResult;
import com.xia.yuauth.controller.web.vo.Result;
import com.xia.yuauth.controller.web.annotation.ResultConvert;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageImpl;
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
            return convertToResult(body);
        }
        return body;
    }

    @SuppressWarnings("unchedked")
    private Result<Object> convertToResult(Object body) {
        if (body instanceof PageImpl) {
            PagePageResultConverter<Object> converter = new PagePageResultConverter<>();
            PageResult<Object> pageResult = converter.convert((PageImpl<Object>) body);
            return new Result<>().withCode(ResultStatusEnum.SUCCESS.getCode()).withData(pageResult);
        }

        if (body instanceof Result) {
            return (Result<Object>) body;
        } else {
            return new Result<>().withCode(ResultStatusEnum.SUCCESS.getCode()).withData(body);
        }
    }


}