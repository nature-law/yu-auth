package com.xia.yuauth.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.xia.yuauth.domain.model.user.User;
import com.xia.yuauth.domain.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description:
 *
 * @author wanghaoxin
 * date     2022/4/10 20:40
 * @version 1.0
 */
@DgsComponent
public class UserFetcher {

    @Autowired
    private UserRepository userRepository;

    @DgsQuery
    public Iterable<User> users() {
        return userRepository.findAll();
    }
}
