package com.xia.yuauth.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xia.yuauth.domain.model.user.User;
import com.xia.yuauth.domain.model.user.UserRepository;
import com.xia.yuauth.mapper.UserMapper;
import com.xia.yuauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private UserMapper userMapper;

    @Override
    public User createUser(User user) {
        User saveUser = userRepository.save(user);
        return saveUser;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByMail(String mail) {
        return userRepository.getUserByMail(mail);
    }

    @Override
    public PageInfo<User> list(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<User> list = userMapper.list();
        return new PageInfo<>(list);
    }


    @Override
    public Page<User> listAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Integer isExists(String account, String property) {
        return userRepository.isExists(account);
    }
}
