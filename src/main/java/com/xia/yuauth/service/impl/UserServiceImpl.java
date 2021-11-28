package com.xia.yuauth.service.impl;

import com.xia.yuauth.domain.model.user.User;
import com.xia.yuauth.domain.model.user.UserRepository;
import com.xia.yuauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description:
 *
 * @author wanghaoxin
 * date     2021/11/28 23:00
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        User saveUser = userRepository.save(user);
        return saveUser;
    }
}
