package com.xia.yuauth.constants.enums.converter;

import com.xia.yuauth.exception.ServiceException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * description: 数值类型与枚举之间的转换
 *
 * @author wanghaoxin
 * date     2021/12/4 17:31
 * @version 1.0
 */
public class WebEnumConverterFactory implements ConverterFactory<String, Enum<?>> {
    @Override
    public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return source -> {
            T[] enumConstants = targetType.getEnumConstants();
            for (T enumConstant : enumConstants) {
                if (enumConstant.name().equals(source)) {
                    return enumConstant;
                }
            }
            throw new ServiceException("无法获取参数值对应的枚举：【%】s", source);
        };
    }
}
