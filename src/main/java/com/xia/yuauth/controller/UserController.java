package com.xia.yuauth.controller;


import com.github.pagehelper.PageInfo;
import com.xia.yuauth.constants.enums.ResultStatusEnum;
import com.xia.yuauth.constants.enums.converter.PageParamsPageableConverter;
import com.xia.yuauth.controller.web.PageParams;
import com.xia.yuauth.controller.web.Result;
import com.xia.yuauth.controller.web.annotation.VerifyPageParams;
import com.xia.yuauth.domain.model.user.User;
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
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user")
    public Result<User> add(@RequestBody User user) {
        User saveUser = userService.createUser(user);
        return new Result<User>().withCode(ResultStatusEnum.SUCCESS.getCode()).withData(saveUser);
    }

    @GetMapping(value = "/user/{id}")
    public Result<User> getUser(@PathVariable(value = "id") Long id) {
        return new Result<User>().withCode(ResultStatusEnum.SUCCESS.getCode()).withData(userService.getUserById(id));
    }

    @GetMapping(value = "/users")
    public Result<PageInfo<User>> list(Pageable pageable) {
        return new Result<PageInfo<User>>().withCode(ResultStatusEnum.SUCCESS.getCode()).withData(userService.list(pageable));
    }

    @VerifyPageParams
    @GetMapping(value = "/users/paging")
    public Result<Page<User>> listAll(@RequestBody PageParams pageParams) {
        Pageable pageable = new PageParamsPageableConverter().convert(pageParams);
        return new Result<Page<User>>().withCode(ResultStatusEnum.SUCCESS.getCode()).withData(userService.listAll(pageable));
    }
}
