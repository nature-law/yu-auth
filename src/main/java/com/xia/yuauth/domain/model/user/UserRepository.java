package com.xia.yuauth.domain.model.user;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * description: 用户仓库
 *
 * @author Administrator
 * @date 2021/11/26 13:48
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    /**
     * description:
     *
     * @param mail 邮箱
     * @return : 用户
     */
    @Query("select 1 from t_sys_user where mail = :mail")
    Integer isExists(@Param("mail") String mail);

    @Query("select * from t_sys_user where mail = :mail")
    User getUserByMail(@Param("mail") String mail);
}
