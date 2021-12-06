package com.xia.yuauth.constants.enums.converter;

import com.xia.yuauth.controller.web.PageParams;
import com.xia.yuauth.exception.ServiceException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * description:PageParams 转换为 PageRequest
 *
 * @author Administrator
 * @date 2021/12/6 15:07
 */
public class PageParamsPageableConverter implements Converter<PageParams, Pageable> {

	/**
	 * Spring Pageable 默认从 第 0 页开始
	 */
	private static final int DECREASE_ONE = -1;

	@Override
	public Pageable convert(PageParams params) throws ServiceException {
		verifyRequiredPageParams(params);
		return convertTo(params);
	}

	/**
	 * 验证必填请求参数
	 *
	 * @param params 请求参数
	 */
	private void verifyRequiredPageParams(PageParams params) {
		if (params == null) {
			throw new ServiceException("参数为空!");
		}
		Integer pageNum = params.getPageNum();
		Integer pageSize = params.getPageSize();
		if (pageNum == null || pageSize == null) {
			throw new ServiceException("参数错误: [pageNum:%s], [pageSize:%s]", pageNum, pageSize);
		}

		if (params.getPageSize() <= 0 || params.getPageNum() <= 0) {
			throw new ServiceException("参数错误: [pageNum:%s], [pageSize:%s]", pageNum, pageSize);
		}
	}

	private Pageable convertTo(PageParams params) {
		Pageable pageable = null;
		PageParams.Sort sort = params.getSort();
		if (sort != null) {
			List<PageParams.Sort.Order> orders = params.getSort().getOrders();
			if (!orders.isEmpty()) {
				List<Sort.Order> springOrders = new ArrayList<>();
				orders.forEach(order -> {
					String property = order.getProperty();
					if (property == null || "".equals(property)) {
						throw new ServiceException("如果需要排序， 排序字段必填！");
					}

					Sort.Order springOrder = Sort.Order.by(property);
					Sort.Direction direction = Optional.ofNullable(order.getDirection()).map(Enum::name)
							.map(Sort.Direction::fromString).orElseGet(() -> Sort.Direction.ASC);
					Sort.NullHandling nullHandling = Optional.ofNullable(order.getNullHandling()).map(Enum::name)
							.map(Sort.NullHandling::valueOf).orElseGet(() -> Sort.NullHandling.NATIVE);
					springOrder.with(direction);
					springOrder.with(nullHandling);
					springOrders.add(springOrder);
				});
				Sort springSort = Sort.by(springOrders);
				pageable = PageRequest.of(params.getPageNum() + DECREASE_ONE, params.getPageSize(), springSort);
			}
		} else {
			pageable = PageRequest.of(params.getPageNum() + DECREASE_ONE, params.getPageSize());
		}
		return pageable;
	}
}
