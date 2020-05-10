package com.ljs.learn.myspringsecurity.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 认证：
    // http安全策略
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 指定规则：
        // 首页所有人都可以访问
        // 功能页面有权限的人才能访问

        // 设置请求规则
        // 不同的请求设置不同的访问权限
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasAnyRole("v1")
                .antMatchers("/level2/**").hasAnyRole("v2")
                .antMatchers("/level3/**").hasAnyRole("v3");

        // 没有权限时，默认跳转到login页面
        http.formLogin()
                .loginPage("/toLogin") //登录请求
                .loginProcessingUrl("/login"); //处理登录的请求

        // 开启页面的用户记忆功能 ，在cookies中添加一个kv对：remember-me
        http.rememberMe()
                .rememberMeParameter("remember") ;// 自定义接收前端的参数

        // 开启注销，默认跳转到 /logout，可以指定注销的url
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/");

    }

    // 授权：
    // 检查用户的账户密码，并设置用户的权限（）即为用户授权
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 在内存中认证
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("testuser").password(new BCryptPasswordEncoder().encode("1234")).roles("v1", "v2")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("1234")).roles("v1", "v2", "v3")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("1234")).roles("v1");
    }
}
