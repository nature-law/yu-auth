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
     * description: 创建一个用户
     *
     * @param user 用户
     * @return User 用户
     */
    User createUser(User user);

    /**
     * description: 通过id 获取用户信息
     *
     * @param id 用户id
     * @return : 用户
     */
    User getUserById(Long id);

    /**
     * description: 通过注册邮箱获取用户信息
     *
     * @param mail 注册邮箱
     * @return : 用户信息
     */
    User getUserByMail(String mail);

    User getUserByMailAndPassword(String mail, String password);

    PageInfo<User> list(Pageable pageable);

    Page<User> listAll(Pageable pageable);

    Integer isExists(String account, String property);

}
