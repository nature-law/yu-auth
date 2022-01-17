package com.xia.yuauth.infrastructure.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.*;

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
     * 哈希次数
     */
    static final int HASH_TIMES = 2;


    /**
     * 第一步
     * description: 定义密码的加密规则
     *
     * @return HashedCredentialsMatcher
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // set name of hash
        matcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        matcher.setHashIterations(HASH_TIMES);
        // Storage format is hexadecimal
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }


    /**
     * 第二步
     * description: 定义登录realm
     *
     * @param matcher 密码加密规则
     * @return : LoginRealm 登录realm
     */
    @Bean
    public LoginRealm loginRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        LoginRealm loginRealm = new LoginRealm();
        loginRealm.setCredentialsMatcher(matcher);
        return loginRealm;
    }

    /**
     * 第三步
     * description: 定义 jwt Realm
     *
     * @return : TokenRealm JwtReam
     */
    @Bean
    public JwtRealm tokenRealm() {
        return new JwtRealm();
    }

    /**
     * 第四步
     * description: 自定义 Realm 选择和使用策略
     */
    @Bean
    public MyModularRealmAuthenticator userModularRealmAuthenticator() {
        // rewrite ModularRealmAuthenticator
        MyModularRealmAuthenticator modularRealmAuthenticator = new MyModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }


    /**
     * 第五步
     * description: 创建具有Web特性的SecurityManager， SecurityManager 用来管理 Realm 和 Session( shiro 默认采用session 的方式进行鉴权)
     *
     * @return : org.apache.shiro.web.mgt.DefaultWebSecurityManager
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("loginRealm") LoginRealm loginRealm,
                                           @Qualifier("tokenRealm") JwtRealm tokenRealm,
                                           @Qualifier("userModularRealmAuthenticator") MyModularRealmAuthenticator authenticator) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setAuthenticator(authenticator);

        Collection<Realm> realms = Optional.ofNullable(securityManager.getRealms()).orElse(new ArrayList<>());
        // 给SecurityManager注入Realm
        realms.add(loginRealm);
        realms.add(tokenRealm);
        securityManager.setRealms(realms);

        // 关闭session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }


    /**
     * 第六步
     * description: 配置 Shiro 过滤规则
     *
     * @return : org.apache.shiro.spring.web.ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置 securityManager
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        // 配置公共资源和受限资源
        Map<String, String> publicResourceMap = new HashMap<>();
        publicResourceMap.put("/images/**", DefaultFilter.anon.name());
        publicResourceMap.put("/css/**", DefaultFilter.anon.name());
        publicResourceMap.put("/fonts/**", DefaultFilter.anon.name());
        publicResourceMap.put("/js/**", DefaultFilter.anon.name());

        // authc是过滤器的一种，表示除了设置公共资源和默认认证路径之外所有资源是受限资源
        publicResourceMap.put("/**", DefaultFilter.authc.name());

        // 添加自己的过滤器并且取名为jwt
        LinkedHashMap<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwt", jwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        // 过滤链定义，从上向下顺序执行，一般将放在最为下边
        publicResourceMap.put("/login/**", DefaultFilter.anon.name());
        publicResourceMap.put("/v1/**", "jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(publicResourceMap);
        return shiroFilterFactoryBean;
    }


    /**
     * description: 解决spring aop和注解配置一起使用的bug。如果您在使用shiro注解配置的同时，引入了spring
     * aop的starter，会有一个奇怪的问题，导致shiro注解的请求，不能被映射
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator creator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * description: 开启 AOP 的注解支持
     *
     * @param securityManager Security Manager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * SpringShiroFilter首先注册到spring容器
     * 然后被包装成FilterRegistrationBean
     * 最后通过FilterRegistrationBean注册到servlet容器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean delegatingFilterProxy() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilterFactoryBean");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }


    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

}
