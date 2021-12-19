package com.xia.yuauth.infrastructure.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * description: shiro config
 *
 * @author wanghaoxin
 * date     2021/12/7 23:37
 * @version 1.0
 */
@Configuration
public class ShiroConfig {
    /**
     * description: 获取用户的 realm
     *
     * @return : com.xia.yuauth.infrastructure.config.shiro.UserRealm
     */
    @Bean
    public UserRealm getRealm() {
        UserRealm userRealm = new UserRealm();

        // 设置Hash凭证校验匹配器，用来完成密码加密校验
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 设置加密算法MD5
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 设置散列次数1024
        hashedCredentialsMatcher.setHashIterations(1024);

        // 注入凭证校验匹配器
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        return userRealm;
    }

    /**
     * description: 创建具有Web特性的SecurityManager
     *
     * @return : org.apache.shiro.web.mgt.DefaultWebSecurityManager
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        // 给SecurityManager注入Realm
        defaultWebSecurityManager.setRealm(getRealm());
        return defaultWebSecurityManager;
    }

    /**
     * description: 创建ShiroFilter
     *
     * @return : org.apache.shiro.spring.web.ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 给ShiroFilter注入SecurityManager
        shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());

        // 设置默认认证路径，认证失败后会调用该接口，也算是公共资源
        shiroFilterFactoryBean.setLoginUrl("/v1/sys/user/login");
        // 配置公共资源和受限资源
        Map<String, String> map = new HashMap<>();
        // anon是过滤器的一种，表示该资源是公共资源，需要设置在authc上面
        map.put("/index", "anon");
        map.put("/v1/sys/user/register", "anon");
        map.put("/v1/sys/user/login", "anon");
        map.put("/v1/sys/mail/verify_code", "anon");
        // authc是过滤器的一种，表示除了设置公共资源和默认认证路径之外所有资源是受限资源
        map.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

}
