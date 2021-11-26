package com.xia.yuauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@ImportResource(value = "classpath:/dubbo/consumer.xml")
@EnableJdbcRepositories
@ComponentScan(value = "com.xia.yuauth.config")
@SpringBootApplication
public class YuAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(YuAuthApplication.class, args);
	}

}
