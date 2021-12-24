package com.xia.yuauth.service.impl;

import com.xia.yuauth.domain.model.user.User;
import com.xia.yuauth.infrastructure.middleware.GlobalCache;
import com.xia.yuauth.infrastructure.utils.JWTUtils;
import com.xia.yuauth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * description: 刷新token
 *
 * @author wanghaoxin
 * date     2021/12/23 22:35
 * @version 1.0
 */
@Service(value = "refreshTokenServiceImpl")
public class RefreshTokenServiceImpl implements TokenService {

	@Autowired
	private GlobalCache globalCache;

	public static final int EXIST = 1;

	private static final int EXPIRE_TIME_MILLS = 15 * 24 * 60 * 60;

	public static final String REFRESH_TOKEN_PREFIX = "REFRESH:TOKEN:";

	@Override
	public String newRefreshToken(@NonNull User user) {
		String token = JWTUtils.getToken(user, EXPIRE_TIME_MILLS);
		globalCache.set(REFRESH_TOKEN_PREFIX + token, EXIST, EXPIRE_TIME_MILLS);
		return token;
	}
}
