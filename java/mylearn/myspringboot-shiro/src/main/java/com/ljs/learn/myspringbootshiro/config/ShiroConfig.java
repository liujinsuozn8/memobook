package com.ljs.learn.myspringbootshiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    // 创建 realm 对象
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    // DefaultWebSecurityManager
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    // ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        // 添加shiro的内置过滤器
        Map<String, String> map = new LinkedHashMap<>();

        // 设置请求过滤
        // 1. 为不同的请求设置认证方式
        // map.put("/user/add", "authc");
        // map.put("/user/update", "authc");

        // 3. 配置授权信息，授权需要写在认证上面，否则不生效
        map.put("/user/add", "perms[user:add]");
        map.put("/user/update", "perms[user:update]");

        // 2. 使用通配符设置认证方式
        map.put("/user/*", "authc");
        bean.setFilterChainDefinitionMap(map);

        //设置登录页面，如果没有进行认证，则自动跳转到该页面
        bean.setLoginUrl("/tologin");

        //设置没有授权时的401页面
        bean.setUnauthorizedUrl("/noauth");
        return bean;
    }

    // 整合 shiro-thymeleaf
    @Bean
    public ShiroDialect shiroDialect(){
         return new ShiroDialect();
    }
}
