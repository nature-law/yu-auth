package com.xia.yuauth.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * description: 管理Spring Bean
 *
 * @author wanghaoxin
 * date     2021/11/28 21:11
 * @version 1.0
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    /**
     * 这里使用的是根据class类型来获取bean 当然你可以根据名称或者其他之类的方法 主要是有applicationContext你想怎么弄都可以
     */
    public static <T> T getBeanByClass(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
