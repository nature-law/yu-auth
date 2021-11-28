package com.xia.yuauth.service;

import com.xia.yuauth.domain.model.user.User;

/**
 * description: User 业务处理
 *
 * @author wanghaoxin
 * date     2021/11/28 22:00
 * @version 1.0
 */
public interface UserService {

    /**
     * 创建一个用户
     */
    User createUser(User user);

}
