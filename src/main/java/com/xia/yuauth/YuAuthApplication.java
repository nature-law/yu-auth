package com.xia.yuauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource(value = "classpath:/dubbo/dubbo-consumer.xml")
@SpringBootApplication
public class YuAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(YuAuthApplication.class, args);
	}

}
