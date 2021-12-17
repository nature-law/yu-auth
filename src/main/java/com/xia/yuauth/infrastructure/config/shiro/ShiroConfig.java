package com.xia.yuauth.infrastructure.config.shiro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: shiro config
 *
 * @author wanghaoxin
 * date     2021/12/7 23:37
 * @version 1.0
 */
@Configuration
public class ShiroConfig {

    @Bean
    public Realm realm() {
        Realm realm = new Realm();
        return realm;
    }
}
