package com.xia.yuauth.infrastructure.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
public class ZooKeeperConfigTest {
    private CuratorFramework client;

    @Before
    public void init() {
        client = CuratorFrameworkFactory.builder()
                // Zookeeper 地址
                .connectString("localhost:2181")
                // 重连策略
                .retryPolicy(new RetryForever(10000))
                .build();
        // 开启会话
        client.start();
    }

    @Test
    public void testCreate() {
        try {
            String result = client.create().withMode(CreateMode.PERSISTENT).forPath("/test", "测试永久节点".getBytes(StandardCharsets.UTF_8));
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGet() throws Exception {
        byte[] tests = client.getData().forPath("/test");
        System.out.println(new String(tests, StandardCharsets.UTF_8));
    }

    @Test
    public void testCreateSubNode() throws Exception {
        System.out.println(client.create().forPath("/test/sub", "测试子节点".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void testTempNode() throws Exception {
        String s = client.create().withMode(CreateMode.EPHEMERAL).forPath("/test/temp", "测试临时节点".getBytes(StandardCharsets.UTF_8));
        System.out.println(s);
    }

    @Test
    public void testWatchNode() throws Exception{
        Watcher watcher = event -> System.out.println("监听到内容： " + event.getPath() + "  ->  " + event.getType());
        byte[] bytes = client.getData().usingWatcher(watcher).forPath("/test");
        System.out.println(new String(bytes));
        client.setData().forPath("/test", "watch demo 1".getBytes(StandardCharsets.UTF_8));
        client.setData().forPath("/test", "watch demo 2".getBytes(StandardCharsets.UTF_8));

    }

}