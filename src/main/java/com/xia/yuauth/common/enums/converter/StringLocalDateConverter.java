package com.xia.yuauth.common.enums.converter;

import com.xia.yuauth.common.exception.ServiceException;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * description: String 转 LocalDate
 *
 * @author wanghaoxin
 * date     2021/12/4 12:09
 * @version 1.0
 */
public class StringLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = date.trim();
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            throw new ServiceException(new IllegalArgumentException(), "参数格式非法 [%s]， 正确格式为：yyyy-MM-dd", date);
        }
    }

}
