package com.xia.yuauth.controller.web.handler;

import com.xia.yuauth.common.enums.ResultStatusEnum;
import com.xia.yuauth.controller.web.vo.PageParams;
import com.xia.yuauth.controller.web.vo.Result;
import com.xia.yuauth.controller.web.annotation.VerifyPageParams;
import com.xia.yuauth.common.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

/**
 * description: 分页参数处理器
 *
 * @author wanghaoxin
 * date     2021/12/6 22:08
 * @version 1.0
 */
@RestControllerAdvice
public class PageParamsHandler implements RequestBodyAdvice {

	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
	                                       Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		return inputMessage;
	}


	/**
	 * 验证必填请求参数
	 *
	 * @param params 请求参数
	 */
	private void verifyRequiredPageParams(PageParams params) {
		Result<Object> objectResult = new Result<>().withCode(ResultStatusEnum.PARAMS_NOT_MATCHES.getCode());
		if (params == null) {
			throw new ServiceException(objectResult, "参数为空!");
		}
		Integer pageNum = params.getPageNum();
		Integer pageSize = params.getPageSize();
		if (pageNum == null || pageSize == null) {
			throw new ServiceException(objectResult, "参数错误: [pageNum:%s], [pageSize:%s]", pageNum, pageSize);
		}

		if (params.getPageSize() <= 0 || params.getPageNum() <= 0) {
			throw new ServiceException(objectResult, "参数错误: [pageNum:%s], [pageSize:%s]", pageNum, pageSize);
		}

		PageParams.Sort sort = params.getSort();
		if (sort != null) {
			List<PageParams.Sort.Order> orders = params.getSort().getOrders();
			if (!orders.isEmpty()) {
				orders.forEach(order -> {
					String property = order.getProperty();
					if (StringUtils.isEmpty(property)) {
						throw new ServiceException(objectResult.withDesc("如果需要排序， 排序字段必填！"));
					}
				});
			}
		}
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
	                            Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		boolean isVerify = Objects.requireNonNull(parameter.getMethod()).isAnnotationPresent(VerifyPageParams.class);
		if (isVerify) {
			if (targetType == PageParams.class) {
				if (body instanceof PageParams) {
					verifyRequiredPageParams((PageParams) body);
				}
			}
		}
		return body;
	}

	@Override
	public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return body;
	}
}
