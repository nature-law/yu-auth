package com.xia.yuauth.domain.model.user;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * description: 用户仓库
 *
 * @author Administrator
 * @date 2021/11/26 13:48
 */
@Mapper
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
