package com.xia.yuauth.service;

import com.xia.yuauth.domain.model.user.User;
import org.springframework.lang.NonNull;

/**
 * description Token服务
 *
 * @author wanghaoxin
 * date     2021/12/23 22:34
 * @version 1.0
 */
public interface TokenService {
    String newRefreshToken(@NonNull User user);
}
