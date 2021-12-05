package com.xia.yuauth.constants.enums.converter;

import com.xia.yuauth.constants.enums.EnumConvertible;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * description: 写数据库转换器
 *
 * @author wanghaoxin
 * date     2021/12/5 14:07
 * @version 1.0
 */
public class WritingConverterFactory implements ConverterFactory<EnumConvertible, Object> {


    @Override
    @SuppressWarnings("unchecked")
    public <T> Converter<EnumConvertible, T> getConverter(Class<T> targetType) {
        return source -> (T) source.getValue();
    }
}
