package com.xia.yuauth.common.enums.converter;

import com.xia.yuauth.common.exception.ServiceException;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * description: 字符串转 LocalDateTime
 *
 * @author wanghaoxin
 * date     2021/12/4 12:08
 * @version 1.0
 */
public class LocalDateStringConverter implements Converter<LocalDate, String> {

    @Override
    public String convert(LocalDate date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return formatter.format(date);
        } catch (Exception e) {
            throw new ServiceException(new IllegalArgumentException(), "参数格式非法 [%s]， 正确格式为：yyyy-MM-dd", date);
        }
    }
}
