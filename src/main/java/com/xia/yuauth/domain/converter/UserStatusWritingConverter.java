package com.xia.yuauth.domain.converter;

import com.xia.yuauth.constants.enums.UserStatusEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * description: 用户状态枚举转换器
 *
 * @author Administrator
 * @date 2021/11/29 15:07
 */
@WritingConverter
public class UserStatusWritingConverter implements Converter<UserStatusEnum, Integer> {
	@Override
	public Integer convert(UserStatusEnum statusEnum) {
		return statusEnum.getStatus();
	}

}
