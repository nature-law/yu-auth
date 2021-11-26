package com.xia.yuauth.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: Mybatis配置
 *
 * @author Administrator
 * @date 2021/11/26 16:10
 */
@Configuration
public class MyBatisJdbcConfiguration {
	@Bean
	SqlSessionFactoryBean sqlSessionFactoryBean() {
		// Configure MyBatis here
	}
}
