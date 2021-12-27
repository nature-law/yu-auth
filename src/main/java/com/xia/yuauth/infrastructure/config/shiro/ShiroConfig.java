package com.xia.yuauth.infrastructure.config.shiro;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.Md5CredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.Arrays;
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

    private static final String MD5 = "md5";

    private static final String SALT = "yu-auth@whx.com";
    /**
     * description: 获取用户的 realm
     *
     * @return : com.xia.yuauth.infrastructure.config.shiro.UserRealm
     */
    @Bean
    public UserRealm getRealm() {
        UserRealm userRealm = new UserRealm();
        // 设置Hash凭证校验匹配器，用来完成密码加密校验
       /* HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 设置加密算法MD5
        hashedCredentialsMatcher.setHashAlgorithmName(MD5);
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
    public SecurityManager securityManager() {
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
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置 securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 配置公共资源和受限资源
        Map<String, String> publicResourceMap = new HashMap<>();
        publicResourceMap.put("/image/**","anon");
        publicResourceMap.put("/css/**", "anon");
        publicResourceMap.put("/fonts/**","anon");
        publicResourceMap.put("/js/**","anon");

        publicResourceMap.put("/v1/sys/user/register", "anon");
        publicResourceMap.put("/v1/sys/user/login", "anon");
        publicResourceMap.put("/logout","logout");
        publicResourceMap.put("/v1/sys/mail/verify_code", "anon");

        // authc是过滤器的一种，表示除了设置公共资源和默认认证路径之外所有资源是受限资源
        publicResourceMap.put("/**", "authc");

        // 添加自己的过滤器并且取名为jwt
        LinkedHashMap<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwt", jwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        // 过滤链定义，从上向下顺序执行，一般将放在最为下边
        publicResourceMap.put("/**", "jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(publicResourceMap);
        return shiroFilterFactoryBean;
    }

    /**
     * SpringShiroFilter首先注册到spring容器
     * 然后被包装成FilterRegistrationBean
     * 最后通过FilterRegistrationBean注册到servlet容器
     * @return
     */
    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilterFactoryBean");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {//@Qualifier("hashedCredentialsMatcher")
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 初始化Authenticator
     */
    @Bean
    public Authenticator authenticator( ) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        //设置两个Realm，一个用于用户登录验证和访问权限获取；一个用于jwt token的认证
        authenticator.setRealms(Arrays.asList(getRealm()));
        //设置多个realm认证策略，一个成功即跳过其它的
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

}
