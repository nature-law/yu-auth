package com.xia.yuauth.controller;

import com.xia.yuauth.constants.enums.ResultStatusEnum;
import com.xia.yuauth.domain.model.user.User;
import com.xia.yuauth.domain.web.Result;
import com.xia.yuauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * description:
 *
 * @author wanghaoxin
 * @version 1.0
 * date 2021/11/28 10:02
 */
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user")
    public Result<Boolean> add(@RequestBody User user) {
        return new Result<Boolean>().code(ResultStatusEnum.SUCCESS.getCode()).data(Boolean.TRUE);
    }

    @GetMapping(value = "/user/{id}")
    public Result<User> getUser(@PathVariable(value = "id") Long id) {
        User user = new User();
        user.setId(id);
        return new Result<User>().code(ResultStatusEnum.SUCCESS.getCode()).data(user);
    }
}
