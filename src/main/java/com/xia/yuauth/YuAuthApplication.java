package com.xia.yuauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource(value = {"classpath:/dubbo/consumer.xml", "classpath:/dubbo/provider.xml"})
@SpringBootApplication
public class YuAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuAuthApplication.class, args);
    }

}
