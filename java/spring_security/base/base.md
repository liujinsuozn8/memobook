<span id="catalog"></span>

### 目录
- [SpringSecurity概述](#SpringSecurity概述)
- [SpringSecurity的maven依赖](#SpringSecurity的maven依赖)
- [SpringSecurity的使用方法](#SpringSecurity的使用方法)
    - [说明示例](#说明示例)
    - [使用方法说明](#使用方法说明)
- [](#)
- [](#)

# SpringSecurity概述
[top](#catalog)
- 官网
    - spring.io/projects/spring-security
- 什么是Spring Security
    - 一款针对Spring项目的安全框架
    - 可用于身份认证和权限控制
    - 可以实现很好的Web安全控制
    - 是SpringBoot底层安全模块默认的技术选型

- SpringSecurity的两个主要目标
    - 认证（Authentication）
    - 授权，即访问控制（Authorization）

- SpringSecurity与传统方式的对比    
    - 传统安全控制的实现方式
        - 拦截器、过滤器：大量的代码与冗余
    - SpringSecurity的实现方式
        - 引入 `spring-boot-starter-security` 模块
        - 进行相关配置
 
- 几个重要的类
    - WebSecurityConfigurerAdapter: 自定义Security策略
    - AuthenticationManagerBuilder: 自定义认证策略
    - @EnableWebSecurity: 开启WebSecurity模式

# SpringSecurity的maven依赖
[top](#catalog)
- SpringSecurity的maven依赖
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency> 
    ```

- thymeleaf的整合依赖
    ```xml
    <!-- https://mvnrepository.com/artifact/org.thymeleaf.extras/thymeleaf-extras-springsecurity5 -->
            <dependency>
                <groupId>org.thymeleaf.extras</groupId>
                <artifactId>thymeleaf-extras-springsecurity5</artifactId>
                <version>3.0.4.RELEASE</version>
            </dependency>
    ```

# SpringSecurity的使用方法
## 说明示例
[top](#catalog)
- 说明示例：- [/java/mylearn/myspring-security](/java/mylearn/myspring-security)
- 页面结构
    ```
    ├── index.html
    └── views
        ├── level1
        │   ├── 1.html
        │   ├── 2.html
        │   └── 3.html
        ├── level2
        │   ├── 1.html
        │   ├── 2.html
        │   └── 3.html
        ├── level3
        │   ├── 1.html
        │   ├── 2.html
        │   └── 3.html
        └── login.html
    ```
- 结构说明
    - index.html，首页
    - views/level1，具有 v1 权限的用户可以访问
    - views/level2，具有 v2 权限的用户可以访问
    - views/level3，具有 v3 权限的用户可以访问
    - login.html，未登录时，通过该页面登录

## 使用方法说明
[top](#catalog)
- 自定义类的方法
    - 语法
        ```java
        @EnableWebSecurity
        public class 自定义类 extends WebSecurityConfigurerAdapter {
            // 认证配置方法
            protected void configure(HttpSecurity http){...}
            // 授权配置方法
            protected void configure(AuthenticationManagerBuilder auth){...}
        }
        ```
    - 继承 `WebSecurityConfigurerAdapter` 类来实现相关配置
    - 通过 `@EnableWebSecurity` 使Spring接管
- 认证：不同的请求设置不同的访问权限
    ```java
    http.authorizeRequests()
            // 任何权限都可以
            .antMatchers("url01").permitAll() 
            //设置只有某些权限可以，可以设置多个权限
            .antMatchers("url02").hasAnyRole("role1", "role2", ...) 

     ```
- 授权
    - 不同的用户给予不同的权限
    - 使用时需要进行加密
        - Spring Security 5.0+ 新增了加密方式，密码需要配置加密方式，否则会报错
    - 多个用户可使用 `and()` 来连接
    - 一个用户可以授予多个权限
    - 语法
        ```java
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("用户1").password(new BCryptPasswordEncoder().encode("密码")).roles("role1", "role2",...)
                .and()
                .withUser("用户2").password(new BCryptPasswordEncoder().encode("密码")).roles("role1", "role2",...)
                ...
        ```
- 开启登录验证
    - 没有权限则自动调整到登录页面
    - 语法
        ```java
        http.formLogin()
                .loginPage("/登录请求") //
                .loginProcessingUrl("/处理登录的请求"); //
    
        ```
    - 默认情况下使用 `/login` 进行登录 
    - 如果没有自定义页面，则只写`http.formLogin()`即可
    - 如果需要使用自定义登录页面，则需要指定 `loginPage`，`loginProcessingUrl`

- 开启注销
    - 默认跳转到 `/logout`
    - 因为 `/logout` 请求默认接收的是 POST 请求，所以如果使用了自定义页面，需要指定登出请求的方式为 `GET`
    - 语法
        ```java
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/");
        ```

- 开启页面的用户记忆功能 
    - 本质是在cookies中添加一个kv对：remember-me
    - 如果使用默认页面，会自动在页面中添加记忆选项
    - 如果使用自定义登录页面，需要使用 `rememberMeParameter()` 配置接收参数
    - 语法
        ```java
        http.rememberMe()
                .rememberMeParameter("自定义参数");
        ```

- 示例
    - 参考代码
        - [/java/mylearn/myspring-security/src/main/java/com/ljs/learn/myspringsecurity/config/SecurityConfig.java](/java/mylearn/myspring-security/src/main/java/com/ljs/learn/myspringsecurity/config/SecurityConfig.java)
    - 代码内容
        ```java
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
        ```