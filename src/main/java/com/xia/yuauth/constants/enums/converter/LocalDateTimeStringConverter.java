package com.xia.yuauth.constants.enums.converter;

import com.xia.yuauth.exception.ServiceException;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * description: 字符串转 LocalDateTime
 *
 * @author wanghaoxin
 * date     2021/12/4 12:08
 * @version 1.0
 */
public class LocalDateTimeStringConverter implements Converter<LocalDateTime, String> {

    @Override
    public String convert(LocalDateTime datetime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return formatter.format(datetime);
        } catch (Exception e) {
            throw new ServiceException(new IllegalArgumentException(), "参数格式非法 [%s]， 正确格式为：yyyy-MM-dd HH:mm:ss", datetime);
        }
    }
}
