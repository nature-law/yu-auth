package com.xia.yuauth.service;

import com.github.pagehelper.PageInfo;
import com.xia.yuauth.domain.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


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

    User getUserById(Long id);

    PageInfo<User> list(Pageable pageable);

    Page<User> listAll(Pageable pageable);

}
