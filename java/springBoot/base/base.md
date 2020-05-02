<span id="catalog"></span>

### 目录
- [SpringBoot概述](#SpringBoot概述)
    - [SpringBoot概述的基本知识](#SpringBoot概述的基本知识)
    - [SpringBoot与微服务架构](#SpringBoot与微服务架构)
- [创建helloWorld程序](#创建helloWorld程序)
- [Maven配置分析](#Maven配置分析)
- [SpringBoot的自动装配原理](#SpringBoot的自动装配原理)
- [SpringBoot的启动原理](#SpringBoot的启动原理)
- [通过配置文件来装配bean](#通过配置文件来装配bean)
    - [使用YAML配置文件](#使用YAML配置文件)
    - [使用properties配置文件](#使用properties配置文件)
- [多环境配置及配置文件位置](#多环境配置及配置文件位置)
- [JSP303校验](#JSP303校验)
- [](#)
- [其他](#其他)

# SpringBoot概述
## SpringBoot概述的基本知识
[top](#catalog)
- SpringBoot是javaWeb的开发框架，和SpringMVC类似
- SpringBoot与Spring
    - SpringBoot基于Spring开发
    - SpringBoot本身<label style="color:red">并不提供</label>Spring框架的核心特性以及扩展功能
    - SpringBoot只用于快速开发基于Spring框架的应用，能够进一步简化开发步骤
    - SpringBoot<label style="color:red">不是用来替代Spring的</label>，而是和Spring结合来提升开发体验的工具
- SpringBoot的核心思想：<label style="color:red">更加强调约定大于配置</label>
- SpringBoot的优势
    - 快速开发
    - 提供了许多默认设置，使得很多基于SpringBoot的应用只需要很少的**Spring配置**
    - 集成了大量常用的第三方配置，如：redis，mongoDB，JPA，RabbitMQ等等
        - 这些第三方库几乎可以零配置，并且开箱即用
    - 内置式容器简化 Web项目 ?????
    - 没有冗余代码生成
    - 不要求xml配置
- SpringBoot的本质
    - 一个整合了很多开发常用组件及配置的Spring开发框架
        
## SpringBoot与微服务架构
[top](#catalog)
- 微服务架构的本质
    - 应用拆分，构建成一系列**小服务的组合**，多个小服务之间通过http的方式进行交互
- 单体应用架构（all in one）
    - 单体架构是指：将一个应用中的所有服务都封装在一个应用中
    - 无论是什么类型的系统，都需要将数据库、服务功能放到一个war包内
    - 扩展方式
        - 整个应用水平复制 + 负载均衡
    - 优点
        - 易于开发和测试
        - 方便部署
        - 需要扩展时，复制多个war包，部署到多个服务器上并执行负载均衡即可
    - 缺点
        - 无论修改什么内容，都需要关闭整个服务，重新执行打包部署
        - 对于大型应用，无法将所有功能都放在一个应用里
        - 不利于分工和维护
- 微服务架构
    - 将 单体应用架构 中的功能拆分成模块，执行业务时组合需要的模块
    - 扩展方式
        - 根据需要复制模块，相对于单体应用只复制了部分 

# 创建helloWorld程序
[top](#catalog)
- 官网参考：https://start.spring.io
- 创建helloWorld程序
    - 创建处理工程的两种方式
        - 方式1：从官网创建，下载zip包，解压后导入开发工具
        - 方式2：从idea的spring initializer 创建
            - 创建时选择依赖：Web
    - 自定义的包目录下创建开发结构：controller、service、dao、bean(pojo)
        - 创建后，通过 `MyspringbootApplication.java` 中的main程序即可直接运行
            - 该程序<label style="color:red">不能删除</label>
        - 自定义目录需要在 `XXXApplication.java` 的<label style="color:red">同级目录</label>下创建
- 使用 `mvn package` 指令打包后，可以直接通过 `java -jar xxx.jar` 来直接启动项目
- 创建后的程序结构
    ```
    myspringboot
    ├── .mvn
    │   └── wrapper
    │       ├── MavenWrapperDownloader.java
    │       ├── maven-wrapper.jar
    │       └── maven-wrapper.properties
    ├── myspringboot.iml
    ├── pom.xml
    └── src
        ├── main
        │   ├── java
        │   │   └── com
        │   │       └── ljs
        │   │           └── learn
        │   │               └── myspringboot
        │   │                   ├── MyspringbootApplication.java
        │   │                   ├── bean
        │   │                   ├── controller
        │   │                   │   └── HelloController.java
        │   │                   ├── dao
        │   │                   └── service
        │   └── resources
        │       ├── application.properties
        │       ├── static
        │       └── templates
        └── test
            └── java
                └── com
                    └── ljs
                        └── learn
                            └── myspringboot
                                └── MyspringbootApplicationTests.java
    ```


# Maven配置分析
[top](#catalog)
- pom.xml文件分析
    - 默认继承 `spring-boot-starter-parent` 的依赖管理，控制版本与打包等任务
    - `spring-boot-starter-web`
        - 这个包中默认包含了 SpringMVC
        - 内部使用 Tomcat 作为默认的**嵌入式容器**
    - `spring-boot-starter-test`
        - 单元测试依赖包
    - 构建工具：`spring-boot-maven-plugin`
        - 配合父工程 `spring-boot-starter-parent`，可以把Spring Boot应用打成jar包直接运行

- 使用 `mvn package` 指令打包后，可以直接通过 `java -jar xxx.jar` 来直接启动项目

- 配置内容
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
          
        <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>2.2.6.RELEASE</version>
            <relativePath/> <!-- lookup parent from repository -->
        </parent>
    
        <groupId>com.ljs.learn</groupId>
        <artifactId>myspringboot</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <name>myspringboot</name>
        <description>Demo project for Spring Boot</description>
    
        <properties>
            <java.version>1.8</java.version>
        </properties>
    
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
    
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
                <scope>test</scope>
                <exclusions>
                    <exclusion>
                        <groupId>org.junit.vintage</groupId>
                        <artifactId>junit-vintage-engine</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
        </dependencies>
    
        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>
            </plugins>
        </build>
    
    </project>
    ```

# SpringBoot的自动装配原理
[top](#catalog)
- maven依赖关系
    - maven依赖tree
        - spring-boot-dependencies
            - spring-boot-starter-parent
                - 自定义SpringBoot工程的pom
    - 所有功能的jar包依赖都写在顶级父工程 `spring-boot-dependencies`中
        - 在SpringBoot中引入依赖的时候，可以不指定版本，应为版本已经在父工程中写好了
- 启动器
    - 基本启动器
        ```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        ```
    - 所有的启动器都以：`spring-boot-starter` 开头
    - SpringBoot中有多种启动器，每个启动器都对应一种功能场景
        - 如 `spring-boot-starter-web` 应用与web环境
            - 使用该启动器后，将会自定导入与web环境相关的所有依赖
    - 需要某些功能时，只需要找到对应的启动器就可以了

- 主程序
    - 主程序分析
        ```java
        @SpringBootApplication // 标注该类是一个SpringBoot的应用
        public class XXXXXApplication {
        
            public static void main(String[] args) {
                // 启动SpringBoot应用
                SpringApplication.run(XXXXXApplication.class, args);
            }
        }
        ```
- 注解 `@SpringBootApplication`分析
    - 注解间的关系
        ```
        @SpringBootApplication  SpringBoot应用配置
           │
           ├── @SpringBootConfiguration SpringBoot配置
           │      │
           │      └── @Configuration Spring的JavaConfig配置类
           │            │
           │            └── @Component Spring组件
           │
           └── @EnableAutoConfiguration 自动配置
                  │
                  ├── @AutoConfigurationPackage 自动导入包
                  │
                  └── @Import({AutoConfigurationImportSelector.class}) 导入选择器          
        ```

    - 主要的配置功能由 `@SpringBootConfiguration`、`@EnableAutoConfiguration` 负责
    - 由`@ComponentScan`负责扫描包进行bean的装配，一定程度上摆脱了xml配置
- SpringBoot如何加载自动配置
    - 工程启动后，从 `/META-INF/spring.factories`中获取指定的全类名
    - 所有需要导入的组件，以类名的形式返回，并将组件添加到容器，使自动配置生效
    - javaEE、解决方案、自动配置的内容都保存在：`spring-boot-autoconfigure.jar` 下
    - 所有自动配置不一定生效，需要判断其条件成立。导入相应的启动器后，会使一些配置的条件成立，则配置成功
    - 内部存在大量的 `XXXAutoConfiguration` 类，通过这些类向容器中导入某种启动器需要的所有组件，并自动配置
        - 通过这些自动配置类，去除了复杂的xml配置

# SpringBoot的启动原理
[top](#catalog)
- 启动代码
    ```java
    public static void main(String[] args) {
        SpringApplication.run(MyspringbootApplication.class, args);
    }
    ```
- SpringApplication 类的 run 方法的功能
    - 推断应用的类型：普通项目、web项目
    - 查找、加载所以可用初始化器，设置到initializers属性中
    - 找到所有的应用给程序监听器，设置到listeners属性中
    - 推断并设置main方法的定义类，找到运行的主类

# 通过配置文件来装配bean
## 使用YAML配置文件
[top](#catalog)
- SpringBoot使用一个全局的配置文件，配置文件的名称固定为：`application`
- 默认生成的是 properties 配置文件，但是官方推荐使用 yaml 配置文件
- yaml相比properties的优势
    1. 简洁
    2. 可以设置类的值
    3. 松散绑定
        - 当属性名是`xxx-yyy-zzz` 的形式时，会自动转换为驼峰命名并与类中的属性匹配
    4. 可以进行JSR303校验
- 类与配置绑定的方式
    - 为类添加注解
        - `@Component`，将类注册为bean
        - `@ConfigurationProperties(prefix = "yaml中的名字")`，绑定类与配置中的属性值
    
- `@ConfigurationProperties`的作用
    - 将yaml配置文件中的每一个属性，映射到这个组件中
    - 将当前类中的属性与配置文件中的相关配置进行绑定
    
- 使用 ConfigurationProperties 时，需要添加maven依赖
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
    </dependency>
    ```
    
- 示例
    - bean
        - 参考代码
            - [/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/bean/Dog.java](/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/bean/Dog.java) 
            - [/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/bean/Person.java](/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/bean/Person.java)
        - 代码内容
            ```java
            @Component
            @ConfigurationProperties(prefix = "person")
            public class Person {
                private String name;
                private String lastName;
                private int age;
                private boolean gender;
                private LocalDate birth;
                private Map<String, Object> maps;
                private List<Object> list;
                private Dog dog;
                // getter setter
            }
            ```
           
            ```java
            @Component
            public class Dog {
                private String name;
                private int age;
                // getter setter
            }
            ```
    - yaml配置
        - 参考配置
            - [/java/mylearn/myspringboot/src/main/resources/application.yaml](/java/mylearn/myspringboot/src/main/resources/application.yaml) 
        - 配置内容
            ```yaml
            # 配置person类
            person:
              name: testName
              last-name: testLaseName
              age: 18
              gender: false
              maps: {k1: v1, K2: v2}
              list:
                - aaa
                - bbb
                - ccc
                - ddd
                - eee
              birth: 2020/01/01
              dog:
                name: testDog
                age: 5
            ```
          
## 使用properties配置文件
[top](#catalog)
- 使用方法：
    1. 使用注解 `@PropertySource(value="classpath:配置文件")`，将配置文件与类绑定
    2. 使用 `@Value("${配置属性名}")`，来为类的属性设值
- 示例
    - properties配置
        - 参考配置
            - [/java/mylearn/myspringboot/src/main/resources/person02.properties](/java/mylearn/myspringboot/src/main/resources/person02.properties)
        - 配置内容
            ```properties
            name=testName02
            age=20
            gender=false
            ```
     - bean
        - 参考代码
            - [/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/bean/Person02.java](/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/bean/Person02.java)
        - 代码内容
            ```java
            @Component
            // 加载指定配置文件
            @PropertySource(value="classpath:person02.properties")
            public class Person02 {
                @Value("${name}")
                private String name;
            
                @Value("${age}")
                private int age;
            
                @Value("${gender}")
                private boolean gender;
          
                //getter setter
            }
            ```

# 多环境配置及配置文件位置
[top](#catalog)
- 可以添加配置文件的4种位置，优先级从大到小
    1. `file:./config/`
    2. `file:./`
    3. `classpath:/config`
    4. `classpath:/`，默认配置
- 文件名必须是 `applicatio.yaml`

- 多环境配置的快速切换
    1. 使用properties文件：使用比较繁琐，不推荐
        - 配置文件列表示例
            ```
            application.yaml
            #文件后缀是test
            application-test.yaml
            #文件后缀是dev
            application-dev.yaml
            ```
        - 优先级最高的是 application.yaml
        - 在 application.yaml 中，通过 `spring.profiles.active=文件后缀`，来激活不同的

    2. 使用yaml的文档分割
        - 在子配置中，使用 `spring: profiles: 配置名` 来标识当前配置
        - 在主配置中，通过 `spring: profiles: active: 子配置名` 的方式来激活某个配置
        

# JSP303校验
[top](#catalog)
- 进行校验的方法
    1. 在类上使用注解`@Validated`，来标识注入时需要进行校验
    2. 在类的属性上添加注解来进行校验

- 常用的属性校验注解
    - 空检查
        
        |注解|功能|
        |-|-|
        |@Null|验证对象是否为null|
        |@NotNull|验证对象不为null，无法检查长度为0的字符串|
        |@NotBlank|检查约束字符串是不是Null、被Trim的长度是否大于0。只对字符串有效，并且会却掉前后的空格|
        |@NotEmpty|元素不能为Null或Empty|

    - Boolean检查
    
        |注解|功能|
        |-|-|
        |@AssertTrue|Boolean对象是否为true|
        |@AssertFalse|Boolean对象是否为false|

    - 长度检查
    
        |注解|功能|
        |-|-|
        |@Size(min= , max=)|验证 Array、Collection、Map、String 的长度是否在给定的范围之内|
        |@Length(min= , max=)|验证 String 的长度是否在给定的范围之内|
        
    - 日期检查
    
        |注解|功能|
        |-|-|
        |@Past|验证Date和Calendar是否但指定之前之前|
        |@Future|验证Date和Calendar是否但指定之前之后|
        |@Pattern|验证 String 对象是否复合正则表达式的规则|

- 示例
    - bean
        - 参考代码
            - [/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/bean/Person.java](/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/bean/Person.java)
        - 代码内容
            ```java
          
            @Component
            @ConfigurationProperties(prefix = "person")
            @Validated
            public class Person {
                // ...            
                
                @Email
                private String email;
                
                // getter setter
            }
            ```          


# 其他
- 修改tomcat的启动端口
    - `resources/application.properties` 
- 修改banner
    - 添加banner文本文件：`resources/banner.txt`
    - 启动工程后，会自动使用自定义的 banner.txt 文件
