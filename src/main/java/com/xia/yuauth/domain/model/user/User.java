package com.xia.yuauth.domain.model.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * description: 用户
 *
 * @author Administrator
 * @date 2021/11/25 9:37
 */

public class User {
	@Id
	private long id;
	private String code;
}
