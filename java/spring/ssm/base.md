<span id="catalog"></span>

### 目录
- [SSM框架整合的总体步骤](#SSM框架整合的总体步骤)
- [说明示例的整体结构](#说明示例的整体结构)
- [mybatis配置](#mybatis配置)
- [service配置](#service配置)
- [springMVC配置](#springMVC配置)
- [](#)
- [](#)


# SSM框架整合的总体步骤
[top](#catalog)
1. 导入maven依赖
2. 创建数据库配置文件：`db.properties`
3. mybatis配置（dao配置）
    - 创建bean包及实体类
    - 创建 `mybatis-config.xml` ，负责日志设置和实体类包的别名设置
    - 创建 `spring-dao.xml`，由Spring接管mybatis
        - 关联数据库配置文件
        - 配置数据库连接池
        - 创建sqlSessionFactory
        - 使用MapperScannerConfigurer 动态扫描dao包
4. service配置
    - 创建 `spring-service.xml`，由Spring接管service
    - 配置自动扫描 Service 包
    - 配置声明式事务，并将事务通过aop织入
5. springMVC配置
    -  添加web application
    - 添加 `springmvc-servlet.xml`
        - 配置自动扫描 Controller 包
        - 过滤静态资源
        - 配置注解驱动
        - 配置视图解析器（可选）
    - 配置 `web.xml`
        - 添加DispatcherServlet
            - <label style="color:red">需要绑定总的Spring配置：applicationConte.xml</label>
        - 添加字符过滤
6. 将 mybatis配置（dao配置）、service配置、springMVC配置全部引入总的配置 `applicationContext.xml` 中统一管理

# 说明示例的整体结构
[top](#catalog)
```
myssm
├── myssm.iml
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── ljs
│   │   │           └── learn
│   │   │               └── myssm
│   │   │                   ├── bean
│   │   │                   │   └── User.java
│   │   │                   ├── controller
│   │   │                   │   └── UserController.java
│   │   │                   ├── dao
│   │   │                   │   ├── UserMapper.java
│   │   │                   │   └── UserMapper.xml
│   │   │                   └── service
│   │   │                       ├── UserService.java
│   │   │                       └── UserServiceImpl.java
│   │   └── resources
│   │       ├── applicationContext.xml
│   │       ├── db.properties
│   │       ├── mybatis-config.xml
│   │       ├── spring-dao.xml
│   │       ├── spring-service.xml
│   │       └── springmvc-servlet.xml
│   └── test
│       └── java
└── web
    ├── WEB-INF
    │   ├── jsp
    │   │   └── result.jsp
    │   └── web.xml
    └── index.jsp
```

# mybatis配置
[top](#catalog)
- 创建实体类
    - 因为回使用扫描包的方式统一管理类，所以可以使用注解 `@Alias` 来声明别名
    - 实体类参考
        - [/java/mylearn/myssm/src/main/java/com/ljs/learn/myssm/bean/User.java](/java/mylearn/myssm/src/main/java/com/ljs/learn/myssm/bean/User.java)
    - 实体类代码
        ```java
        @Alias("user")
        public class User {
            public int id;
            public String name;
            public String pwd;
            // getter、setter
        }
        ```

- mybatis基本配置
    - 参考配置
        - [/java/mylearn/myssm/src/main/resources/mybatis-config.xml](/java/mylearn/myssm/src/main/resources/mybatis-config.xml)
    - 配置内容
        ```xml
        <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE configuration
                PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-config.dtd">
        <!--核心配置内容-->
        <configuration>

            <settings>
                <!--添加日志-->
                <!--<setting name="logImpl" value="STDOUT_LOGGING"/>-->
                <!--<setting name="logImpl" value="LOG4J"/>-->

                <!--开启数据库自动名 与 java属性名 命名规则的自动转换-->
                <setting name="mapUnderscoreToCamelCase" value="true"/>

                <!--显示开启mybatis二级缓存-->
                <!--<setting name="cacheEnabled" value="true"/>-->
            </settings>

            <!--扫描包来添加别名-->
            <typeAliases>
                <package name="com.ljs.learn.myssm.bean"/>
            </typeAliases>

        </configuration>
        ```

- 通过spring-mybatis，由spring接管mybatis
    - 配置内容
        - 关联数据库配置文件
        - 配置数据库连接池
        - 创建sqlSessionFactory
        - 使用MapperScannerConfigurer 动态扫描dao包
            - 通过这种动态扫描的方式可以不同创建Mapper对应的实现类
    - 参考配置
        - [/java/mylearn/myssm/src/main/resources/spring-dao.xml](/java/mylearn/myssm/src/main/resources/spring-dao.xml)
    - 配置内容
        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:context="http://www.springframework.org/schema/context"
            xsi:schemaLocation="http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context
            https://www.springframework.org/schema/context/spring-context.xsd">

            <!-- 关联数据库配置文件 -->
            <context:property-placeholder location="classpath:db.properties"/>
            <!--配置数据库连接池-->
            <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
                <property name="driverClass" value="${driver}"/>
                <property name="jdbcUrl" value="${url}"/>
                <property name="user" value="${username}"/>
                <property name="password" value="${password}"/>
            </bean>

            <!--配置sqlSessionFactory-->
            <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
                <property name="dataSource" ref="dataSource"/>
                <!--绑定mybatis配置文件-->
                <property name="configLocation" value="classpath:mybatis-config.xml"/>
            </bean>

            <!--配置dao接口扫描包，将Dao接口动态导入Spring容器-->
            <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
                <!--注入sqlSessionFactory-->
                <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
                <!--扫描dao包-->
                <property name="basePackage" value="com.ljs.learn.myssm.dao"/>
            </bean>

        </beans>
        ```

- Mapper 示例
    - 参考代码
        - [/java/mylearn/myssm/src/main/java/com/ljs/learn/myssm/dao/UserMapper.java](/java/mylearn/myssm/src/main/java/com/ljs/learn/myssm/dao/UserMapper.java)
        - [/java/mylearn/myssm/src/main/java/com/ljs/learn/myssm/dao/UserMapper.xml](/java/mylearn/myssm/src/main/java/com/ljs/learn/myssm/dao/UserMapper.xml)
    - 代码内容
        ```java
        public interface UserMapper {
            List<User> getAllUser();
        }
        ```

        ```xml
        <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE mapper
                PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
        <mapper namespace="com.ljs.learn.myssm.dao.UserMapper">
            <select id="getAllUser" resultType="user">
                select * from user;
            </select>
        </mapper>
        ```
        
# service配置
[top](#catalog)
- service配置
    - 配置内容
        - 配置自动扫描 Service 包
        - 配置声明式事务，并将事务通过aop织入

    - 参考配置
        - [/java/mylearn/myssm/src/main/resources/spring-service.xml](/java/mylearn/myssm/src/main/resources/spring-service.xml)

    - 配置内容
        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:context="http://www.springframework.org/schema/context"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

            <!--扫描Service下的包-->
            <context:component-scan base-package="com.ljs.learn.myssm.service"/>

            <!--手动配置某个类-->
            <bean id="userServiceImpl" class="com.ljs.learn.myssm.service.UserServiceImpl">
                <property name="userMapper" ref="userMapper"/>
            </bean>

            <!--配置事务-->
            <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
                <!--注入数据源-->
                <property name="dataSource" ref="dataSource"/>
            </bean>

            <!-- aop事务 -->

        </beans>
        ```

- Service 示例
    - 参考代码
        - [/java/mylearn/myssm/src/main/java/com/ljs/learn/myssm/service/UserService.java](/java/mylearn/myssm/src/main/java/com/ljs/learn/myssm/service/UserService.java)
        [/java/mylearn/myssm/src/main/java/com/ljs/learn/myssm/service/UserServiceImpl.java](/java/mylearn/myssm/src/main/java/com/ljs/learn/myssm/service/UserServiceImpl.java)
    - 代码内容
        ```java
        public interface UserService {
            List<User> getAllUser();
        }
        ```

        ```java
        @Service
        public class UserServiceImpl implements UserService {

            private UserMapper userMapper;

            public void setUserMapper(UserMapper userMapper) {
                this.userMapper = userMapper;
            }

            @Override
            public List<User> getAllUser() {
                return userMapper.getAllUser();
            }
        }
        ```

# springMVC配置
[top](#catalog)
- 添加 `springmvc-servlet.xml`
    - 配置内容
        - 自动扫描包
        - 过滤静态资源
        - 配置注解驱动
        - 配置视图解析器（可选）
    - 参考配置
        - [/java/mylearn/myssm/src/main/resources/springmvc-servlet.xml](/java/mylearn/myssm/src/main/resources/springmvc-servlet.xml)
    - 配置内容
        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:context="http://www.springframework.org/schema/context"
            xmlns:mvc="http://www.springframework.org/schema/mvc"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">

            <!--自动扫描包-->
            <context:component-scan base-package="com.ljs.learn.myssm.controller"/>
            <!--过滤静态资源-->
            <mvc:default-servlet-handler/>
            <!--配置注解驱动-->
            <mvc:annotation-driven>
                <!--配置消息转换方式-->
                <mvc:message-converters>
                    <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                        <constructor-arg value="UTF-8"/>
                    </bean>
                    <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                        <property name="objectMapper">
                            <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                                <property name="failOnEmptyBeans" value="false"/>
                            </bean>
                        </property>
                    </bean>
                </mvc:message-converters>
            </mvc:annotation-driven>


            <!--配置视图解析器-->
            <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
                <property name="prefix" value="/WEB-INF/jsp/"/>
                <property name="suffix" value=".jsp"/>
            </bean>
        </beans>
        ```

- 配置 `web.xml`
    - 配置内容
        - 添加DispatcherServlet
            - <label style="color:red">需要绑定总的Spring配置：applicationConte.xml</label>
        - 添加字符过滤
    - 参考配置
        - [/java/mylearn/myssm/web/WEB-INF/web.xml](/java/mylearn/myssm/web/WEB-INF/web.xml)
    - 配置内容
        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
                version="4.0">
            <!-- 添加DispatcherServlet -->
            <servlet>
                <servlet-name>springmvc</servlet-name>
                <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
                <init-param>
                    <param-name>contextConfigLocation</param-name>
                    <param-value>classpath:applicationContext.xml</param-value>
                </init-param>
            </servlet>

            <servlet-mapping>
                <servlet-name>springmvc</servlet-name>
                <url-pattern>/</url-pattern>
            </servlet-mapping>
            
            <!-- 添加字符过滤 -->
            <filter>
                <filter-name>encoding</filter-name>
                <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
                <init-param>
                    <param-name>encoding</param-name>
                    <param-value>utf-8</param-value>
                </init-param>
            </filter>
            <filter-mapping>
                <filter-name>encoding</filter-name>
                <url-pattern>/</url-pattern>
            </filter-mapping>

        </web-app>
        ```

- Controller 示例
    - 参考代码
        - [/java/mylearn/myssm/src/main/java/com/ljs/learn/myssm/controller/UserController.java](/java/mylearn/myssm/src/main/java/com/ljs/learn/myssm/controller/UserController.java)
    - 代码内容
        ```java
        @Controller
        public class UserController {
            @Autowired
            @Qualifier("userServiceImpl")
            private UserService userService;

            @RequestMapping("/user/all")
            public String getAllUser(Model model){
                List<User> allUser = userService.getAllUser();
                model.addAttribute("users", allUser);
                return "result";
            }
        }
        ```