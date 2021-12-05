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
public class StringLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String datetime) {
        try {
            datetime = datetime.trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(datetime, formatter);
        } catch (Exception e) {
            throw new ServiceException(new IllegalArgumentException(), "参数格式非法 [%s]， 正确格式为：yyyy-MM-dd HH:mm:ss", datetime);
        }
    }
}
