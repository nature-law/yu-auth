package com.xia.yuauth.mapper;

import com.xia.yuauth.domain.model.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * description:
 *
 * @author wanghaoxin
 * date     2021/12/5 17:35
 * @version 1.0
 */
@Mapper
public interface UserMapper {
    List<User> list();
}
