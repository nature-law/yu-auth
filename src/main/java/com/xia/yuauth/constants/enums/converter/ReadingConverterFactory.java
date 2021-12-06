package com.xia.yuauth.constants.enums.converter;

import com.xia.yuauth.constants.enums.EnumConvertible;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.data.convert.ReadingConverter;

/**
 * description: 整数转换枚举工厂
 *
 * @author wanghaoxin
 * date     2021/12/5 00:59
 * @version 1.0
 */
@ReadingConverter
public class ReadingConverterFactory implements ConverterFactory<Integer, EnumConvertible> {
	@Override
	public <T extends EnumConvertible> Converter<Integer, T> getConverter(Class<T> targetType) {
		return source -> {
			T[] enumConstants = targetType.getEnumConstants();
			for (T enumConstant : enumConstants) {
				// 整数型值转换为枚举
				Integer enumValue = enumConstant.getValue();
				if (enumValue.equals(source)) {
					return enumConstant;
				}
			}
			return null;
		};
	}
}
