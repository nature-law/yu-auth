package com.xia.yuauth.infrastructure.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: ZooKeeper 配置类
 *
 * @author wanghaoxin
 * date     2022/2/13 00:05
 * @version 1.0
 */

public class ZooKeeperConfig {
    @Value("${zookeeper.host}")
    private String host;

    @Value("${zookeeper.port}")
    private int port;

    /**
     * 构建 CuratorFramework 客户端，并开启会话
     *
     * @return CuratorFramework
     */
    @Bean(name = "curatorClient")
    public CuratorFramework buildCuratorClient() {
        // 使用 CuratorFrameworkFactory 构建 CuratorFramework
        CuratorFramework client = CuratorFrameworkFactory.builder()
                // Zookeeper 地址
                .connectString(host + ":" +port)
                // 重连策略
                .retryPolicy(new RetryForever(10000))
                .build();
        // 开启会话
        client.start();
        return client;
    }
}
