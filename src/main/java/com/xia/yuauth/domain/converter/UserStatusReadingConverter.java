package com.xia.yuauth.domain.converter;

import com.xia.yuauth.constants.enums.UserStatusEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * description: 用户状态枚举转换器
 *
 * @author Administrator
 * @date 2021/11/29 15:07
 */
@ReadingConverter
public class UserStatusReadingConverter implements Converter<Integer, UserStatusEnum> {
	@Override
	public UserStatusEnum convert(Integer status) {
		return UserStatusEnum.get(status);
	}
}
