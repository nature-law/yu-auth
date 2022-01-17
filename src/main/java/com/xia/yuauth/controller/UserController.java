package com.xia.yuauth.controller;


import com.github.pagehelper.PageInfo;
import com.xia.yuauth.common.enums.converter.PageParamsPageableConverter;
import com.xia.yuauth.controller.web.annotation.ResultConvert;
import com.xia.yuauth.controller.web.annotation.VerifyPageParams;
import com.xia.yuauth.controller.web.vo.PageParams;
import com.xia.yuauth.domain.model.user.User;
import com.xia.yuauth.infrastructure.config.shiro.HashedCredentialsMatcherUtils;
import com.xia.yuauth.infrastructure.middleware.GlobalCache;
import com.xia.yuauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * description:
 *
 * @author wanghaoxin
 * @version 1.0
 * date 2021/11/28 10:02
 */
@RestController
@RequestMapping(value = "/v1/sys")
@ResultConvert
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GlobalCache globalCache;

    @PostMapping(value = "/user")
    public User add(@RequestBody User user) {
        user.setPassword(String.valueOf(HashedCredentialsMatcherUtils.encryptPassword(user.getPassword().toCharArray())));
        User saveUser = userService.createUser(user);
        saveUser.setPassword(null);
        return saveUser;
    }


    @GetMapping(value = "/user/{id}")
    public User getUser(@PathVariable(value = "id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping(value = "/users")
    public PageInfo<User> list(Pageable pageable) {
        return userService.list(pageable);
    }

    @VerifyPageParams
    @GetMapping(value = "/users/paging")
    public Page<User> listAll(@RequestBody PageParams pageParams) {
        Pageable pageable = new PageParamsPageableConverter().convert(pageParams);
        return userService.listAll(pageable);
    }
}
