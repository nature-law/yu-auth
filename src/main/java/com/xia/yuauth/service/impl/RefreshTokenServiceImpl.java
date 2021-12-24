package com.xia.yuauth.service.impl;

import com.xia.yuauth.infrastructure.generation.IdGeneration;
import com.xia.yuauth.infrastructure.middleware.GlobalCache;
import com.xia.yuauth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final long EXPIRE_TIME_MILLS = 15 * 24 * 60 * 60;

    public static final String REFRESH_TOKEN_PREFIX = "REFRESH:TOKEN:";

    private static final String RT = "RT";

    @Override
    public String newRefreshToken() {
        long id = IdGeneration.getId();
        globalCache.set(REFRESH_TOKEN_PREFIX + RT + id, EXIST, EXPIRE_TIME_MILLS);
        return RT + id;
    }
}
