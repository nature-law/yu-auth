package com.xia.yuauth.infrastructure.middleware;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * description:
 *
 * @author wanghaoxin
 * date     2021/12/9 23:16
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigurationTest {
    @Autowired
    private GlobalCache globalCache;

    @Test
    public void test() {
        globalCache.set("key2", "value3");
        globalCache.lSetAll("list", Arrays.asList("hello", "redis"));
        List<Object> list = globalCache.lGet("list", 0, -1);
        System.out.println(globalCache.get("key2"));
    }
}
