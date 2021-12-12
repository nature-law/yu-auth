package com.xia.yuauth.common.enums.converter;

import com.xia.yuauth.controller.web.vo.PageParams;
import com.xia.yuauth.common.exception.ServiceException;
import org.apache.commons.collections.CollectionUtils;
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
        return convertTo(params);
    }

    private Pageable convertTo(PageParams params) {
        Pageable pageable = null;
        PageParams.Sort sort = params.getSort();
        if (sort != null) {
            List<PageParams.Sort.Order> orders = params.getSort().getOrders();
            if (CollectionUtils.isNotEmpty(orders)) {
                List<Sort.Order> springOrders = new ArrayList<>();
                orders.forEach(order -> {
                    String property = order.getProperty();
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
            } else {
                pageable = defaultPageable(params.getPageNum(), params.getPageSize());
            }
        } else {
            pageable = defaultPageable(params.getPageNum(), params.getPageSize());
        }
        return pageable;
    }

    private Pageable defaultPageable(Integer pageNum, Integer pageSize) {
        return PageRequest.of(pageNum + DECREASE_ONE, pageSize);
    }
}
