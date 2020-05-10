<span id="catalog"></span>

### 目录
- [Shiro概述](#Shiro概述)
- [Maven依赖](#Maven依赖)
- [HelloWorld程序](#HelloWorld程序)
- [subject的常用方法](#subject的常用方法)
- [shiro的内置过滤器](#shiro的内置过滤器)
- [与SpringBoot整合](#与SpringBoot整合)
    - [shiro-spring整合Maven依赖](#shiro-spring整合Maven依赖)
    - [整合示例](#整合示例)
    - [shiro配置文件](#shiro配置文件)
    - [添加shiro配置类](#添加shiro配置类)
    - [认证检测](#认证检测)
        - [在Shiro配置类中配置认证过滤](#在Shiro配置类中配置认证过滤)
        - [在Shiro配置类中配置登录页面跳转](#在Shiro配置类中配置登录页面跳转)
        - [用户认证的实现](#用户认证的实现)
    - [授权检测](#授权检测)
        - [配置授权信息](#配置授权信息)
        - [在realm中实现授权检测](#在realm中实现授权检测)
- [与Mybatis整合](#与Mybatis整合)
    - [构建测试数据库](#构建测试数据库)
    - [与Mybatis整合的步骤](#与Mybatis整合的步骤)
- [整合Thymeleaf](#整合Thymeleaf)
    - [整合Thymeleaf的maven配置](#整合Thymeleaf的maven配置)
    - [整合Thymeleaf的方法](#整合Thymeleaf的方法)
- [](#)

# Shiro概述
[top](#catalog)
- 什么是Shiro
    - 一款java安全框架
    - Shiro可以完成：认证、授权、加密、绘画管理、web集成、缓存等功能
    - 开发容易，可以使用在JavaSE和JavaEE环境
- Shiro的功能
    - 权限管理
        - Authentication，身份认证
        - Authorizetion：授权
        - Session Manager：会话管理，会话可以是葡萄糖的JavaSE胡娜家，也可以是Web环境
        - Cryptography：加密
    - 其他特性
        - Caching：缓存，如用户登录后，用户信息、权限都保存在内存中，不用每次都检索，提高效率
        - Concurrency：并发验证，如在一个线程中开启另一个线程，能把权限自动传播过去
        - Testing：测试支持
        - Run As：允许一个用户伪装成另一个用户进行访问
        - Remember Me：登录维持
- Shiro架构
    - 主要概念
        - 架构图
            - ![outter.png](/java/spring_security/base/imgs/structure/outter.png)
        - subject
            - 代表当前用户
            - 与subject的所有交互都会委托给 SecurityManager
        - SecurityManager
            - 安全管理，所有与安全有关的操作都会与 SecurityManager 交互
            - 负责管理所有的Subject，负责与其他组件进行交互
        - Realm
            - shiro从Realm获取安全数据
            - SecurityManager响应请求时，需要从Realm中获取数据进行比较
            - 相当于 DataSource
    - 整体架构
        - 架构图
            - ![inner.png](/java/spring_security/base/imgs/structure/inner.png)

- 认证与授权的触发
    - 认证：用户未登录时
    - 授权：用户已登录，并且进入某个页面时

# Maven依赖
[top](#catalog)
- 单独使用
    ```xml
    <!-- https://mvnrepository.com/artifact/org.apache.shiro/shiro-core -->
    <dependency>
      <groupId>org.apache.shiro</groupId>
      <artifactId>shiro-core</artifactId>
      <version>1.5.3</version>
    </dependency>

    <!-- configure logging -->
    <!-- https://mvnrepository.com/artifact/org.slf4j/jcl-over-slf4j -->
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>jcl-over-slf4j</artifactId>
      <version>1.7.30</version>
      <scope>runtime</scope>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12 -->
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
      <version>1.7.30</version>
      <scope>runtime</scope>
    </dependency>

    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>1.2.17</version>
      <scope>runtime</scope>
    </dependency>
    ```

# HelloWorld程序
[top](#catalog)
- 参考
    - https://github.com/apache/shiro/blob/master/samples/quickstart/

- 示例路径
    - [/java/mylearn/myshiro](/java/mylearn/myshiro)

- 创建步骤
    1. 创建maven工程
    2. 添加配置文件
        - log4j.properties
        - shiro.ini （安全数据配置文件）
    3. 添加启动类：Quickstart

- 基本结构
    ```
    myshiro
    ├── pom.xml
    └── src
        └── main
            ├── java
            │   └── com
            │       └── ljs
            │           └── learn
            │               └── myshiro
            │                   └── Quickstart.java
            └── resources
                ├── log4j.properties
                └── shiro.ini       <----安全数据配置文件
    ```

- 入口类
    - 参考代码
        - [/java/mylearn/myshiro/src/main/java/com/ljs/learn/myshiro/Quickstart.java](/java/mylearn/myshiro/src/main/java/com/ljs/learn/myshiro/Quickstart.java)
    - 代码内容
        ```java
        public class Quickstart {
        
            private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);
            
            public static void main(String[] args) {
                // 固定代码，初始化shiro环境，导入配置文件
                Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
                SecurityManager securityManager = factory.getInstance();
                SecurityUtils.setSecurityManager(securityManager);
        
                // 获取当前的用户对象
                Subject currentUser = SecurityUtils.getSubject();
        
                // 通过当前用户对象获取session
                Session session = currentUser.getSession();
                // 在session中存取数据
                session.setAttribute("someKey", "aValue");
                String value = (String) session.getAttribute("someKey");
                if (value.equals("aValue")) {
                    log.info("Retrieved the correct value! [" + value + "]");
                }
        
                // let's login the current user so we can check against roles and permissions:
                // 判断当前用户是否已认证
                if (!currentUser.isAuthenticated()) {
                    // 如果没有认证，则使用用户名和密码生成一个Token
                    UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
                    // 设置登录维持
                    token.setRememberMe(true);
        
                    // 执行登录操作
                    try {
                        currentUser.login(token);
                    } catch (UnknownAccountException uae) {
                        log.info("There is no user with username of " + token.getPrincipal());
                    } catch (IncorrectCredentialsException ice) {
                        log.info("Password for account " + token.getPrincipal() + " was incorrect!");
                    } catch (LockedAccountException lae) {
                        log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                                "Please contact your administrator to unlock it.");
                    }
                    // ... catch more exceptions here (maybe custom ones specific to your application?
                    catch (AuthenticationException ae) {
                        //unexpected condition?  error?
                    }
                }
        
                //say who they are:
                //print their identifying principal (in this case, a username):
                log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");
        
                //test a role:
                if (currentUser.hasRole("schwartz")) {
                    log.info("May the Schwartz be with you!");
                } else {
                    log.info("Hello, mere mortal.");
                }
        
                // 粗粒度的检查
                //test a typed permission (not instance-level)
                if (currentUser.isPermitted("lightsaber:wield")) {
                    log.info("You may use a lightsaber ring.  Use it wisely.");
                } else {
                    log.info("Sorry, lightsaber rings are for schwartz masters only.");
                }
        
                // 细粒度的检查
                //a (very powerful) Instance Level permission:
                if (currentUser.isPermitted("winnebago:drive:eagle5")) {
                    log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                            "Here are the keys - have fun!");
                } else {
                    log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
                }
        
                //all done - log out!
                currentUser.logout();
        
                System.exit(0);
            }
        }
        ```

# subject的常用方法
[top](#catalog)

|方法|描述|
|-|-|
|Subject currentUser = SecurityUtils.getSubject();|获取subject|
|Session session = currentUser.getSession();|从subject中获取session|
|currentUser.isAuthenticated();|判断用户是否认证|
|currentUser.login(token);|执行用户登录|
|currentUser.getPrincipal();|获取当前用户的认证|
|currentUser.hasRole("角色名");|判断用户是否拥有某种角色|
|currentUser.isPermitted(...);|判断用户权限|
|currentUser.logout();|注销subject|

# shiro的内置过滤器 
[top](#catalog)

|过滤器|过滤内容|
|-|-|
|anon|无需认证就可以访问|
|authc|必须认证才能访问|
|user|必须有 登录维持功能才能用|
|perms|拥有对某个资源的权限才能访问|
|role|拥有某个角色权限才能访问|

# 与SpringBoot整合
## shiro-spring整合Maven依赖
[top](#catalog)
```xml
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring</artifactId>
    <version>1.5.3</version>
</dependency>
```

## 整合示例
[top](#catalog)
- 示例路径：[/java/mylearn/myspringboot-shiro](/java/mylearn/myspringboot-shiro)
- 工程结构
    ```
    ├── java
    │   └── com
    │       └── ljs
    │           └── learn
    │               └── myspringbootshiro
    │                   ├── MyspringbootShiroApplication.java
    │                   ├── config
    │                   │   ├── ShiroConfig.java
    │                   │   └── UserRealm.java
    │                   ├── controller
    │                   │   └── MyController.java
    │                   ├── dao
    │                   │   └── UserMapper.java
    │                   ├── pojo
    │                   │   └── User.java
    │                   └── service
    │                       ├── UserService.java
    │                       └── UserServiceImpl.java
    └── resources
        ├── application.yaml
        ├── dao
        │   └── UserMapper.xml
        ├── static
        └── templates
            ├── index.html
            ├── login.html
            └── user
                ├── add.html
                └── update.html
    ```

## shiro配置文件
[top](#catalog)
- shiro与spring整合可以不添加以下两个配置文件
    - log4j.properties
    - shiro.ini （安全数据配置文件）

## 添加shiro配置类
[top](#catalog)
- shiro主要组件的配置
    - subject: ShiroFilterFactoryBean
    - securityManager: DefaultWebSecurityManager
    - realm: 创建 realm 对象，需要自定义类
- 自定义 realm 对象
    - 参考代码
        - [/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/UserRealm.java](/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/UserRealm.java) 
    - 需要继承 `AuthorizingRealm`，并实现授权和认证两个方法
        ```java
        public class UserRealm extends AuthorizingRealm {
            // 授权
            @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
                return null;
            }
        
            // 认证
            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
                return null;
            }
        }
        ```

- 在配置类中配置shiro的组件
    - 配置顺序
        1. realm
        2. securityManager
        3. subject
    - 配置示例
        - 参考代码
            - [/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/ShiroConfig.java](/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/ShiroConfig.java)
        - 代码内容
            ```java
            @Configuration
            public class ShiroConfig {
                // 1. 创建 realm 对象
                @Bean
                public UserRealm userRealm(){
                    return new UserRealm();
                }
            
                // 2. DefaultWebSecurityManager
                @Bean
                public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm){
                    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
                    //关联UserRealm
                    securityManager.setRealm(userRealm);
                    return securityManager;
                }
            
                // 3. ShiroFilterFactoryBean
                @Bean
                public ShiroFilterFactoryBean getShiroFilterFactoryBean(
                        @Qualifier("securityManager") DefaultWebSecurityManager securityManager){
                    ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
                    bean.setSecurityManager(securityManager);
                    return bean;
                }
            }
            ```

## 认证检测
### 在Shiro配置类中配置认证过滤
[top](#catalog)
- 设置方法
    1. 创建Map，添加请求url与 [shiro的内置过滤器](#shiro的内置过滤器) 的kv对
        - 可以逐一配置，也可以在路径中使用通配符
    2. 通过`ShiroFilterFactoryBean.setFilterChainDefinitionMap`将map添加到subject中
        
- 配置示例
    - 参考代码
        - [/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/ShiroConfig.java](/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/ShiroConfig.java)
    - 代码内容
        ```java
        @Bean
        public ShiroFilterFactoryBean getShiroFilterFactoryBean(
                @Qualifier("securityManager") DefaultWebSecurityManager securityManager){
            ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
            bean.setSecurityManager(securityManager);

            // 添加shiro的内置过滤器
            Map<String, String> map = new LinkedHashMap<>();
            // 为不同的请求设置认证方式
            // map.put("/user/add", "authc");
            // map.put("/user/update", "authc");
            map.put("/user/*", "authc");
            bean.setFilterChainDefinitionMap(map);

            //设置登录页面，如果没有进行认证，则自动跳转到该页面
            bean.setLoginUrl("/tologin");

            return bean;
        }
        ```

### 在Shiro配置类中配置登录页面跳转
[top](#catalog)
- 请求被过滤之后，可以配置登录页面，来自动跳转
- 配置示例
    - 参考代码
        - [/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/ShiroConfig.java](/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/ShiroConfig.java)
    - 代码内容
        ```java
        @Bean
        public ShiroFilterFactoryBean getShiroFilterFactoryBean(
              @Qualifier("securityManager") DefaultWebSecurityManager securityManager){
          ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
          bean.setSecurityManager(securityManager);
        
          // 配置过滤器
          // ...
          
          //设置登录页面，如果没有权限，则自动跳转到该页面
          bean.setLoginUrl("/tologin");
      
          return bean;
        }
        ```

### 用户认证的实现
[top](#catalog)
- 实现认证的方法
    1. 创建Shiro配置类，配置各请求的授权信息，与页面跳转
    2. 在 controller 中封装用户信息，通过 `SecurityUtils` 获取当前的subject，并执行登录
    3. 在 realm 类的 `doGetAuthenticationInfo` 方法中执行认证
        - 手动执行用户名认证
        - 通过shiro执行密码认证

- 角色分工
    - controller/其他业务模块
        - 负责封装用户信息，执行登录
    - realm
        - 响应登录操纵，并执行登录信息的认证
    - Shiro配置类
        - 负责三个组件之间的关联
        - 设置授权信息与登录、异常页面的跳转

- 工作流程
    1. 启动工程，spring读取Shiro配置类，设置授权信息、页面跳转
    2. 页面发送请求，被Shiro配置类中的请求过滤拦截
    3. 页面跳转到配置好的登录页面
    4. 输入用户名密码，并发送登录请求
    5. 登录请求到达controller
    6. controller将用户名密码封装成token
    7. 使用subjec，通过token进行登录
    8. subject的登录触发 reaml执行认证

- 认证示例
    - 登录页面
        - 参考代码
            - [/java/mylearn/myspringboot-shiro/src/main/resources/templates/login.html](/java/mylearn/myspringboot-shiro/src/main/resources/templates/login.html)
        - 页面内容
            ```html
            <form th:action="@{/login}">
                user:<input type="text" name="user">
                pwd:<input type="text" name="pwd">
                <p style="color:red">[[${msg}]]</p>
                <input type="submit" value="submit">
            </form>
            ```
            
    - controller执行登录
        - 参考代码
            - [/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/controller/MyController.java](/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/controller/MyController.java)
        - 代码内容
            ```java
            @RequestMapping("/login")
            public String login(String user, String pwd, Model model){
                // 获取当前的用户
                Subject subject = SecurityUtils.getSubject();
                // 封装用户的登录数据
                UsernamePasswordToken token = new UsernamePasswordToken(user, pwd);
                // 执行登录，如果没有异常
                try{
                    subject.login(token);
                } catch (UnknownAccountException e) {
                    // 用户名不存在
                    model.addAttribute("msg", "用户不存在");
                    return "login";
                }catch (IncorrectCredentialsException e){
                    // 密码不正确
                    model.addAttribute("msg", "密码不正确");
                    return "login";
                }

                return "index";
            }
            ```
    - realm 执行认证          
        - 参考代码
            - [/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/UserRealm.java](/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/UserRealm.java)
        - 代码内容
            ```java
            // 认证
            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
                System.out.println("认证");
        
                // 假数据
                String name = "root";
                String pwd = "1234";
        
                // 从token中获取用户密码
                UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        
                // 手动执行用户认证
                if (!name.equals(userToken.getUsername())){
                    // 如果用户名不存在，返回null
                    // 将会自动抛出 UnknownAccountException
                    return null;
                }
        
                // 通过shiro执行密码认证
                return new SimpleAuthenticationInfo("", pwd, "");
            }
            ```

## 授权检测
### 配置授权信息
[top](#catalog)
- 配置方法
    1. 在 Shiro配置类的 `getShiroFilterFactoryBean` 方法中，为请求路径添加授权信息
    2. 可以配置检测到未授权时的 401 页面
         - 在Contorller中配置请求路径
         - 通过 `ShiroFilterFactoryBean.setUnauthorizedUrl("url");` 设置401页面的地址
         
- 配置示例
    - Shiro配置类
        - 参考代码
            - [/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/ShiroConfig.java](/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/ShiroConfig.java)
        - 代码内容
            ```java
            @Configuration
            public class ShiroConfig {
                @Bean
                public ShiroFilterFactoryBean getShiroFilterFactoryBean(
                        @Qualifier("securityManager") DefaultWebSecurityManager securityManager){
                    ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
                    bean.setSecurityManager(securityManager);
            
                    // 添加shiro的内置过滤器
                    Map<String, String> map = new LinkedHashMap<>();
            
                    // 3. 配置授权信息，授权需要写在认证上面，否则不生效
                    map.put("/user/add", "perms[user:add]");
                    map.put("/user/update", "perms[user:update]");
                    
                    //....
            
                    //设置没有授权时的401页面
                    bean.setUnauthorizedUrl("/noauth");
                    return bean;
                }
            }
            ```
    - 在Controller中配置 401 页面
        - 参考代码
            - [/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/controller/MyController.java](/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/controller/MyController.java)
        - 代码内容
            ```java
            @Controller
            public class MyController {
                // 设置检测到未授权时的401页面
                @RequestMapping("/noauth")
                @ResponseBody
                public String unAuthorized(){
                    return "unAuthorized";
                }
            }
            ```

### 在realm中实现授权检测
[top](#catalog)
- 实现方法  
    1. 在 `doGetAuthenticationInfo` 中，进行认证检测时，将用户信息作为参数添加到`SimpleAuthenticationInfo`中
    2. 执行1之后，在 `doGetAuthorizationInfo` 中，执行授权时，就可以取出用户信息
        ```java
        // 获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        ```
    3. 创建 `SimpleAuthorizationInfo` 对象，并将添加授权信息，然后作为返回值返回，shiro将自动进行授权检测
        ```java
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                info.addStringPermission(currentUser.getPerms());
        ```

- 示例
    - UserRealm
        - 参考代码
            - [/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/UserRealm.java](/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/UserRealm.java)
        - 代码内容
            ```java
            public class UserRealm extends AuthorizingRealm {
                // 授权
                @Override
                protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
                    System.out.println("授权");
            
                    // 2. 通过数据库检索的结构来进行检测
                    // 2.1 获取当前页面登录的这个对象的用户信息
                    // （new SimpleAuthenticationInfo 时的第一个参数）
                    Subject subject = SecurityUtils.getSubject();
                    User currentUser = (User) subject.getPrincipal();
            
                    // 2.2 从用户信息中获取授权信息，然后进行检测
                    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                    info.addStringPermission(currentUser.getPerms());
                    return info;
                }
            
                // 认证
                // 2. 整合持久层，通过查询数据库来获取用户信息并进行验证
                @Override
                protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
                    // .....
                    // .....
                    // 传递用户信息，在授权检测时，可以继续使用
                    return new SimpleAuthenticationInfo(userInfo, userInfo.getPwd(), "");
                }
            }
            ```


# 与Mybatis整合
## 构建测试数据库
[top](#catalog)
- 使用docker配置一个mysql容器(接入自定义网桥testbr中)
    ```sql
    docker run -d -p 3307:3306 --name mysqllearn --network testbr \
    -v $HOME/mydocker/myvolumes/mysql/learn/mysql/conf:/etc/mysql/conf.d \
    -v $HOME/mydocker/myvolumes/mysql/learn/mysql/logs:/logs \
    -v $HOME/mydocker/myvolumes/mysql/learn/mysql/data:/var/lib/mysql \
    -e MYSQL_ROOT_PASSWORD=1234 \
    mysql:latest
    ```
- 创建数据库
    ```sql
    -- 创建数据库
    create database test01;
    ```
  
- 创建表user及其对应的java实体类
    - sql内容
        ```sql
        -- 创建表
        create table user (
            `id` int(20) not null primary key,
            `name` varchar(30) default null,
            `pwd` varchar(30) default null,
            `perms` varchar(30) default null
        )default charset=utf8
        
        -- 添加数据
        insert into user 
        values
        (1, "aaa", "aaapwd","user:add"),
        (2, "bbb", "bbbpwd","user:add"),
        (3, "ccc", "cccpwd",),
        ```
    - 实体类
        - 参考代码
            - [/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/pojo/User.java](/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/pojo/User.java)
        - 代码内容
            ```java
            public class User {
                private int id;
                private String name;
                private String pwd;
                private String perms;
                // getter、setter
            }
            ```


## 与Mybatis整合的步骤
[top](#catalog)
- 整合步骤
    1. 添加maven配置
        - mysql
        - spring-boot-starter-jdbc
        - 数据源
    2. 添加用户信息对应的Bean
    3. 创建Mapper
    4. 创建Service来操作操作Mapper
    5. 在 realm 中自动装配Service实现类
    6. 通过Service实现类中的方法从db获取数据，并用于认证

- 整合示例
    1. 创建Bean

    2. 创建Mapper
        - 参考代码
            - [/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/dao/UserMapper.java](/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/dao/UserMapper.java)
            - [/java/mylearn/myspringboot-shiro/src/main/resources/dao/UserMapper.xml](/java/mylearn/myspringboot-shiro/src/main/resources/dao/UserMapper.xml)
        - 代码内容
            ```java
            @Mapper
            @Repository
            public interface UserMapper {
                User getUserByName(String name);
            }
            ```
        - 配置内容
            ```xml
            <mapper namespace="com.ljs.learn.myspringbootshiro.dao.UserMapper">
                <select id="getUserByName" parameterType="String" resultType="user">
                    select * from test01.user where name = #{name};
                </select>
            </mapper>
            ```
    3. 创建Service
        - 参考代码
            - [/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/service/UserServiceImpl.java](/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/service/UserServiceImpl.java)
        - 代码内容
            ```java
            @Service
            public class UserServiceImpl implements UserService {
                @Autowired
                private UserMapper userMapper;
            
                @Override
                public User getUserByName(String name) {
                    return userMapper.getUserByName(name);
                }
            }
            ```
    4. 在realm中通过Service来获取数据进行认证
        - 参考代码
            - [/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/UserRealm.java](/java/mylearn/myspringboot-shiro/src/main/java/com/ljs/learn/myspringbootshiro/config/UserRealm.java)
        - 代码内容
            ```java
            // 2. 整合持久层，通过查询数据库来获取用户信息并进行验证
            @Autowired
            private UserService userService;
        
            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
                System.out.println("认证");
        
                // 从token中获取用户密码
                UsernamePasswordToken userToken = (UsernamePasswordToken) token;
                // 通过token中的信息，检索数据库中的用户数据
                User userInfo = userService.getUserByName(userToken.getUsername());
        
                // 手动执行用户认证
                if (userInfo == null){
                    // 如果用户名不存在，返回null
                    // 将会自动抛出 UnknownAccountException
                    return null;
                }
        
                // 通过shiro执行密码认证
                return new SimpleAuthenticationInfo("", userInfo.getPwd(), "");
            }
            ```

# 整合Thymeleaf
## 整合Thymeleaf的maven配置
[top](#catalog)
```xml
<!--shiro + thymeleay-->
<!-- https://mvnrepository.com/artifact/com.github.theborakompanioni/thymeleaf-extras-shiro -->
<dependency>
    <groupId>com.github.theborakompanioni</groupId>
    <artifactId>thymeleaf-extras-shiro</artifactId>
    <version>2.0.0</version>
</dependency>
```

## 整合Thymeleaf的方法
[top](#catalog)
- 整合方法
    1. 导入maven依赖：`thymeleaf-extras-shiro`
    2. 在shiro配置类中，添加 Shiro-Thymeleaf整合配置
        ```java
        @Bean
        public ShiroDialect shiroDialect(){
             return new ShiroDialect();
        }
        ```
    3. 在页面中添加shiro的约束
        - `xmlns:shiro="http://www.thymeleaf.org/thymeleaf-extras-shiro"`
    4. 通过shiro标签来控制页面

- 页面整合示例
    - 参考代码
        - [/java/mylearn/myspringboot-shiro/src/main/resources/templates/login.html](/java/mylearn/myspringboot-shiro/src/main/resources/templates/login.html)
    - 代码内容
        ```html
        <!DOCTYPE html>
        <html xmlns:th="http://www.thymeleaf.org" xmlns:shiro="http://www.thymeleaf.org/thymeleaf-extras-shiro">
        <head>
            <meta charset="UTF-8">
            <title>test</title>
        </head>
        <body>
            <h1>首页</h1>
            <p>[[${msg}]]</p>
            <a shiro:notAuthenticated="" th:href="@{/tologin}">login</a>
        
            <div shiro:hasPermission="user:add">
                <a th:href="@{/user/add}" >add</a>
            </div>
            <div shiro:hasPermission="user:update">
                <a th:href="@{/user/update}">update</a>
            </div>
        
        </body>
        </html>
        ```