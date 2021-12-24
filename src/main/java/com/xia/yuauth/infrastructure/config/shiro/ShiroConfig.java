package com.xia.yuauth.infrastructure.config.shiro;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

      /*  // 设置Hash凭证校验匹配器，用来完成密码加密校验
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 设置加密算法MD5
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 设置散列次数 2
        hashedCredentialsMatcher.setHashIterations(2);

        // 注入凭证校验匹配器
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);*/

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

        // 关闭session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);


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
        shiroFilterFactoryBean.setLoginUrl("/v1/login");
        // 配置公共资源和受限资源
        Map<String, String> map = new LinkedHashMap<>();
        // anon是过滤器的一种，表示该资源是公共资源，需要设置在authc上面
        map.put("/index", "anon");
        map.put("/v1/sys/user/register", "anon");
        map.put("/v1/sys/user/login", "anon");
        map.put("/v1/sys/mail/verify_code", "anon");
        // authc是过滤器的一种，表示除了设置公共资源和默认认证路径之外所有资源是受限资源
        map.put("/**", "authc");


        // 添加自己的过滤器并且取名为jwt
        LinkedHashMap<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwt", jwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        // 过滤链定义，从上向下顺序执行，一般将放在最为下边
        map.put("/**", "jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

}
