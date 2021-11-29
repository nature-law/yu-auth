package com.xia.yuauth.domain.generation;

import com.xia.yuapi.service.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * description: Id 生成器
 *
 * @author Administrator
 * @date 2021/11/29 14:07
 */
@Component
public class IdGeneration {
	private static IdService idService;

	@Autowired
	private IdService idServiceInject;

	@PostConstruct
	public void init() {
		idService = this.idServiceInject;
	}


	public static long getId() {
		return idService.getId();
	}

	public static long[] getIds(int num) {
		return idService.getIds(num);
	}
}
