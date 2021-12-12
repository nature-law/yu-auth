package com.xia.yuauth.common.enums.converter;

import com.xia.yuauth.controller.web.vo.PageResult;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageImpl;

/**
 * description: Page 到 PageResult Converter
 *
 * @author wanghaoxin
 * date     2021/12/7 21:43
 * @version 1.0
 */
public class PagePageResultConverter<T> implements Converter<PageImpl<T>, PageResult<T>> {
    @Override
    public PageResult<T> convert(PageImpl<T> source) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setFirstPage(source.isFirst());
        pageResult.setLastPage(source.isLast());
        pageResult.setList(source.getContent());
        pageResult.setPageNum(source.getPageable().getPageNumber());
        pageResult.setPageSize(source.getPageable().getPageSize());
        pageResult.setPages(source.getTotalPages());
        pageResult.setTotal(Integer.parseInt(String.valueOf(source.getTotalElements())));
        return pageResult;
    }
}
