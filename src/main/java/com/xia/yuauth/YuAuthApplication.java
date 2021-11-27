package com.xia.yuauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@ImportResource(value = "classpath:/dubbo/consumer.xml")
@EnableJdbcRepositories
@SpringBootApplication
public class YuAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuAuthApplication.class, args);
    }

}
