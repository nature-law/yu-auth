package com.xia.yuauth.domain.generation;

import com.xia.yuapi.service.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * description: Id 生成器
 *
 * @author Administrator
 * @date 2021/11/29 14:07
 */
@Component
public class IdGeneration {
	@Autowired
	private static IdService idService;

	public static long getId() {
		return idService.getId();
	}

	public static long[] getIds(int num) {
		return idService.getIds(num);
	}
}
