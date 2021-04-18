<span id="catalog"></span>

### 目录
- [SpringBoot概述](#SpringBoot概述)
    - [SpringBoot概述的基本知识](#SpringBoot概述的基本知识)
    - [SpringBoot与微服务架构](#SpringBoot与微服务架构)
- [创建helloWorld程序](#创建helloWorld程序)
- [maven依赖分析](#maven依赖分析)
- [SpringBoot的自动装配原理](#SpringBoot的自动装配原理)
    - [主程序分析](#主程序分析)
    - [自动装配过程分析](#自动装配过程分析)
    - [关键类AutoConfigurationImportSelector的分析](#关键类AutoConfigurationImportSelector的分析)
    - [自动配置类与配置文件](#自动配置类与配置文件)
    - [判断注解Conditional的说明](#判断注解Conditional的说明)
- [SpringBoot的启动原理](#SpringBoot的启动原理)
- [通过配置文件来装配bean](#通过配置文件来装配bean)
    - [使用YAML配置文件](#使用YAML配置文件)
    - [使用properties配置文件](#使用properties配置文件)
    - [多环境配置及配置文件位置](#多环境配置及配置文件位置)
- [JSP303校验](#JSP303校验)
- [持久层整合](#持久层整合)
    - [创建测试数据库](#创建测试数据库)
    - [整合JDBC](#整合JDBC)
    - [整合数据源-Druid](#整合数据源-Druid)
    - [Mybatis整合](#Mybatis整合)
- [任务](#任务)
    - [异步任务](#异步任务)
    - [定时任务](#定时任务)
- [web开发示例](#web开发示例)
    - [导入静态资源](#导入静态资源)
        - [静态资源的导入位置](#静态资源的导入位置)
        - [静态资源导入原理](#静态资源导入原理)
        - [静态资源导入示例](#静态资源导入示例)
    - [首页定制](#首页定制)
        - [定制方法](#定制方法)
        - [首页定制的原理](#首页定制的原理)
    - [模版引擎](#模版引擎)
        - [为什么使用Thymeleaf而不是jsp](#为什么使用Thymeleaf而不是jsp)
        - [Thymeleaf的maven依赖](#Thymeleaf的maven依赖)
        - [Thymeleaf的使用方法](#Thymeleaf的使用方法)
        - [SpringBoot中Thymeleaf的配置原理](#SpringBoot中Thymeleaf的配置原理)
    - [扩展SpringMVC配置](#扩展SpringMVC配置)
    - [国际化](#国际化)
        - [国际化配置方法](#国际化配置方法)
        - [执行国际化的原理](#执行国际化的原理)
        - [自定义国际化处理](#自定义国际化处理)
    - [自定义拦截器](#自定义拦截器)
    - [web开发示例](#web开发示例)
- [](#)
[top](#catalog)
- [其他](#其他)

# SpringBoot概述
## SpringBoot概述的基本知识
[top](#catalog)
- SpringBoot是javaWeb的开发框架，和SpringMVC类似
- SpringBoot与Spring
    - SpringBoot基于Spring开发
    - SpringBoot本身<span style="color:red">并不提供</span>Spring框架的核心特性以及扩展功能
    - SpringBoot只用于快速开发基于Spring框架的应用，能够进一步简化开发步骤
    - SpringBoot<span style="color:red">不是用来替代Spring的</span>，而是和Spring结合来提升开发体验的工具
- SpringBoot的核心思想：<span style="color:red">更加强调约定大于配置</span>
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
            - 该程序<span style="color:red">不能删除</span>
        - 自定义目录需要在 `XXXApplication.java` 的<span style="color:red">同级目录</span>下创建
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


# maven依赖分析
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
## 主程序分析
[top](#catalog)
- maven依赖关系
    - maven依赖tree
        - spring-boot-dependencies
            - spring-boot-starter-parent
                - 自定义SpringBoot工程的pom
    - 所有功能的jar包依赖都写在顶级父工程 `spring-boot-dependencies`中
        - 在SpringBoot中引入依赖的时候，可以不指定版本，因为版本已经在父工程中写好了

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

## 自动装配过程分析
[top](#catalog)
- 注解 `@SpringBootApplication` 内的注解及调用关系
    ```
    @SpringBootApplication                      SpringBoot应用配置
       │
       ├─ @SpringBootConfiguration              SpringBoot配置
       │    │
       │    └─ @Configuration                   Spring的JavaConfig配置类
       │         │
       │         └─ @Component                  Spring组件
       │
       ├─ @ComponentScan(...)                   扫描与当前配置类同目录下包
       │
       └─ @EnableAutoConfiguration              自动配置
            │
            ├─ @AutoConfigurationPackage        自动导入包
            │
            └─ @Import({AutoConfigurationImportSelector.class}) 自动配置导入选择器        
                 │
                 └─ AutoConfigurationImportSelector
                      │
                      ├─ selectImports() 加载元数据
                      │
                      ├─ getAutoConfigurationEntry() 获取自动配置信息
                      │
                      └─ getCandidateConfigurations() 获取候选配置
                           │
                           ├─ @EnableAutoConfiguration 导入所有使用该注解标识的类
                           │    │
                           │    └─ @SpringBootApplication也标识的该注解，也会被导入
                           │
                           └─ SpringFactoriesLoader 加载类
                                │
                                ├─ loadFactoryNames() 返回加载了哪些资源
                                │
                                └─ loadSpringFactories() 加载资源
                                     │
                                     └─ spring-boot-autoconfigure.jar
                                          │
                                          └─ META-INF
                                               │
                                               └─ spring.factories 保存自动配置的资源
                                                    │
                                                    └─ 大量的 XXXAutoConfiguration类
    ```

- 为什么自定义的包要放在 SpringBootApplication 的同级目录下？
    
    - SpringBootApplication 内部使用 `EnableAutoConfiguration` 标识，所以启动时会导入启动同级目录下所有的资源
- SpringBoot自动配置的过程
    1. 工程启动后，加载文件 `spring-boot-autoconfigure.jar!/META-INF/spring.factories`
        - 文件中的自动配置类：`XXXAutoConfiguration`
    2. 解析 `pom.xml` 文件中的启动器 `starter`，来判断哪些 `XXXAutoConfiguration` 有效
    3. 加载有效的 `XXXAutoConfiguration` 类
    4. `XXXAutoConfiguration` 类进行相关资源的自动配置
        - 之前一些需要手动配置的内容，现在由SpringBoot来自动配置

- 自动配置文件 `META-INF/spring.factories` 中的资源是如何生效的？
    - 通过注解 `@ConditionalOnXXXX` 来标识自动配置中的某些类
    - 在 `@ConditionalOnXXXX` 中添加一些条件，当条件满足时才进行加载
    - 实际使用时，需要导入对应 `spring-boot-starter` 才能使配置生效

- 自动配置类 `XXXAutoConfiguration` 的作用
    - 本质就是 JavaConfig，都用注解 `@Configuration` 进行标识
    - 类中的每个方法都使用 `@Bean` 注解标识，作为一个配置
    - SpringBoot启动后，解析 `pom.xml` 文件中的启动器 `starter`，来判断哪些配置类有效
    - 判断有效后，该类与 spring配置文件相同，会自动导入相关的配置



## 关键类AutoConfigurationImportSelector的分析
[top](#catalog)
- AutoConfigurationImportSelector 类中负责导入相关配置
```java
public class AutoConfigurationImportSelector implements DeferredImportSelector, BeanClassLoaderAware, ResourceLoaderAware, BeanFactoryAware, EnvironmentAware, Ordered {
    private ClassLoader beanClassLoader;

    // 用于导入 pom.xml中导入的springboot相关组件
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        if (!this.isEnabled(annotationMetadata)) {
            return NO_IMPORTS;
        } else {
            // 获取元数据
            AutoConfigurationMetadata autoConfigurationMetadata = AutoConfigurationMetadataLoader.loadMetadata(this.beanClassLoader);
            AutoConfigurationImportSelector.AutoConfigurationEntry autoConfigurationEntry = this.getAutoConfigurationEntry(autoConfigurationMetadata, annotationMetadata);
            return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
        }
    }

    // 获取自动配置信息
    protected AutoConfigurationImportSelector.AutoConfigurationEntry getAutoConfigurationEntry(AutoConfigurationMetadata autoConfigurationMetadata, AnnotationMetadata annotationMetadata) {
        if (!this.isEnabled(annotationMetadata)) {
            return EMPTY_ENTRY;
        } else {
            AnnotationAttributes attributes = this.getAttributes(annotationMetadata);
            // 配置的来源
            List<String> configurations = this.getCandidateConfigurations(annotationMetadata, attributes);
            configurations = this.removeDuplicates(configurations);
            Set<String> exclusions = this.getExclusions(annotationMetadata, attributes);
            this.checkExcludedClasses(configurations, exclusions);
            configurations.removeAll(exclusions);
            configurations = this.filter(configurations, autoConfigurationMetadata);
            this.fireAutoConfigurationImportEvents(configurations, exclusions);
            return new AutoConfigurationImportSelector.AutoConfigurationEntry(configurations, exclusions);
        }
    }

    // 导入候选配置
    protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        // 导入资源
        List<String> configurations = SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), this.getBeanClassLoader());
        // 断言配置不为空
        Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you are using a custom packaging, make sure that file is correct.");
        return configurations;
    }


    protected Class<?> getSpringFactoriesLoaderFactoryClass() {
        // 返回一个注解，即所有用 @EnableAutoConfiguration标识的类都会导入
        // SpringBootApplication 内部使用 EnableAutoConfiguration 标识，所以启动时会导入启动类下所有的资源
        return EnableAutoConfiguration.class;
    }
    
    protected ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }
}
```

```java
// 读取元数据
final class AutoConfigurationMetadataLoader {
    static AutoConfigurationMetadata loadMetadata(ClassLoader classLoader) {
        return loadMetadata(classLoader, "META-INF/spring-autoconfigure-metadata.properties");
    }
}
```

```java
public final class SpringFactoriesLoader {
    // AutoConfigurationImportSelector.getCandidateConfigurations内部调用
    public static List<String> loadFactoryNames(Class<?> factoryType, @Nullable ClassLoader classLoader) {
        String factoryTypeName = factoryType.getName();
        return (List)loadSpringFactories(classLoader).getOrDefault(factoryTypeName, Collections.emptyList());
    }

    private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
        MultiValueMap<String, String> result = (MultiValueMap)cache.get(classLoader);
        if (result != null) {
            return result;
        } else {
            try {
                // 从配置文件下读取配置内容
                Enumeration<URL> urls = classLoader != null ? classLoader.getResources("META-INF/spring.factories") : ClassLoader.getSystemResources("META-INF/spring.factories");
                LinkedMultiValueMap result = new LinkedMultiValueMap();

                while(urls.hasMoreElements()) {
                    URL url = (URL)urls.nextElement();
                    UrlResource resource = new UrlResource(url);
                    // 将资源加载到配置类中
                    Properties properties = PropertiesLoaderUtils.loadProperties(resource);
                    Iterator var6 = properties.entrySet().iterator();

                    while(var6.hasNext()) {
                        Entry<?, ?> entry = (Entry)var6.next();
                        String factoryTypeName = ((String)entry.getKey()).trim();
                        String[] var9 = StringUtils.commaDelimitedListToStringArray((String)entry.getValue());
                        int var10 = var9.length;

                        for(int var11 = 0; var11 < var10; ++var11) {
                            String factoryImplementationName = var9[var11];
                            result.add(factoryTypeName, factoryImplementationName.trim());
                        }
                    }
                }

                cache.put(classLoader, result);
                return result;
            } catch (IOException var13) {
                throw new IllegalArgumentException("Unable to load factories from location [META-INF/spring.factories]", var13);
            }
        }
    }
}
```

## 自动配置类与配置文件
[top](#catalog)
- 与配置相关的两种类
    - `XXXAutoConfiguration`，自动配置类
    - `XXXProperties`，封装配置文件中的相关属性
- 配置文件与类的关系
    - `XXXAutoConfiguration`类中， 注解使用 `prefix` 属性标识配置文件中的前缀
    - 注解 `@EnableConfigurationProperties` 中声明了`XXXProperties`类
    - `XXXProperties`类 与配置文件中对应的前缀绑定
        - 如果在配置中编写了相关内容，就会使用自定义配置
        - 如果配置中没有，会使用默认配置
- 以 `META-INF/spring.factories` 下的 `HttpEncodingAutoConfiguration`类为例说明配置与类的关系
    - 相关源码
        ```java
        @Configuration(proxyBeanMethods = false)
        // 标识使用的配置内容类
        @EnableConfigurationProperties({HttpProperties.class})  
        // 判断当前自动配置是否生效
        // 1. 是不是web环境
        @ConditionalOnWebApplication(type = Type.SERVLET)   
        // 2. 有没有使用对应的类
        @ConditionalOnClass({CharacterEncodingFilter.class})
        // 3. 检查自定义配置文件中有没有 spring.http.encoding 开头的内容。有就使用，没有就使用默认配置
        @ConditionalOnProperty(prefix = "spring.http.encoding", value = {"enabled"}, matchIfMissing = true)
        public class HttpEncodingAutoConfiguration {
            // 构造函数中主动加载 配置类：HttpProperties
            public HttpEncodingAutoConfiguration(HttpProperties properties) {
                this.properties = properties.getEncoding();
            }
        }
        ```
        ```java
        // 通过注解标识 配置中的前缀
        @ConfigurationProperties(prefix = "spring.http")
        public class HttpProperties {
            // 与 HttpEncodingAutoConfiguration 相关的内容
            private final HttpProperties.Encoding encoding = new HttpProperties.Encoding();

            public static class Encoding {
                public static final Charset DEFAULT_CHARSET;
                // 下面的私有属性就是配置文件中可以自定义的内容
                private Charset charset;
                private Boolean force;
                private Boolean forceRequest;
                private Boolean forceResponse;
                private Map<Locale, Charset> mapping;

                public Encoding() {
                    this.charset = DEFAULT_CHARSET;
                }
                // ...
            }

            // ...
        }
        ```

- 如何检测哪些配置类生效了？
    - 在配置文件中添加：`debug=true`，启动之后会在控制台打印自动配置报告
    - 输出内容
        - Positive matches，已匹配的类
        - Negative matches，没有匹配成功的类
        - Unconditional classes，没有条件的类
        - Exclusions，被排除的类

## 判断注解Conditional的说明
[top](#catalog)
- 相关注解

    |@Conditional扩展注解|判断内容|
    |-|-|
    |@ConditionOnJava|系统的java版本是否符合要求|
    |@ConditionOnBean|容器中是否存在指定的bean|
    |@ConditionOnMissingBean|容器不存在指定的bean|
    |@ConditionOnExpression|是否满足SpEL表达式|
    |@ConditionOnClass|系统中包含指定的类|
    |@ConditionOnMissingClass|系统中不包含指定的类|
    |@ConditionOnSingleCandidate|容器中只有一个指定的Bean，或者这个Bean是首选Bean|
    |@ConditionOnProperty|系统中指定的属性是否有指定的值|
    |@ConditionOnResource|类路径下是否存在指定资源文件|
    |@ConditionOnWebApplication|当前是web环境|
    |@ConditionOnNotWebApplication|当前不是web环境|
    |@ConditionOnJndi|JDNI存在指定项|

- 当注解符合条件时，导入类


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

## 多环境配置及配置文件位置
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
        - yaml的优势：**只需要一个配置文件，通过文件分割来创建多环境配置**
        - 在子配置中，使用 `spring: profiles: 配置名` 来标识当前配置
        - 在主配置中，通过 `spring: profiles: active: 子配置名` 的方式来激活某个配置
        - 示例
            ```yaml
            server:
              port: 8081
            spring:
              profiles:
                #active: dev
                active: test            

            ---
            server:
              port: 8082
            spring:
              profiles: dev            

            ---
            server:
              port: 8083
            spring:
              profiles: test
            ```

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

# 持久层整合
## 创建测试数据库
[top](#catalog)
- 使用docker配置一个mysql容器(接入自定义网桥testbr中)
    ```
    docker run -d -p 3307:3306 --name mysqllearn --network testbr \
    -v $HOME/mydocker/myvolumes/mysql/learn/mysql/conf:/etc/mysql/conf.d \
    -v $HOME/mydocker/myvolumes/mysql/learn/mysql/logs:/logs \
    -v $HOME/mydocker/myvolumes/mysql/learn/mysql/data:/var/lib/mysql \
    -e MYSQL_ROOT_PASSWORD=1234 \
    mysql:latest
    ```

## 整合JDBC
[top](#catalog)
- 整合方式
    1. maven依赖
        ```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        ```
    2. 在 `application.yaml` 配置文件中添加数据库配置
        ```yaml
        spring:    
        datasource:
            username: 用户
            password: 密码
            url: 数据库连接地址
            driver-class-name: 驱动类
        ```
    3. 在类中装配类 `JdbcTemplate`，然后通过该类对象的方法来操作数据库
        - JdbcTemplate 简化了数据库操作，只需要提供 sql 和 占位符的赋值参数就可以直接操作数据库
        - 不需要直接操作事务，JdbcTemplate 内部封装了事务的相关操作

- 示例
    - spring配置
        - 参考配置  
            
            - [/java/mylearn/myspringboot/src/main/resources/config/application.yaml](/java/mylearn/myspringboot/src/main/resources/config/application.yaml)
        - 配置内容
            ```yaml
            spring:
              datasource:
                username: root
                password: 1234
                url: jdbc:mysql://127.0.0.1:3307?serverTimezone=UTC&characterEncoding=utf8    useUnicode=true&useSSL=false
                driver-class-name: com.mysql.cj.jdbc.Driver
            ```
    - 使用 `JdbcTemplate` 操作数据库
        - 参考代码
            
            - [/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/controller/jdbc/JDBCController.java](/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/controller/jdbc/JDBCController.java)
        - 代码内容
            ```java
            @RestController
            public class JDBCController {
            @Autowired
            JdbcTemplate jdbcTemplate;
        
                // 查询
                @RequestMapping("/jdbc/userlist")
                public List<Map<String, Object>> userList(){
                    String sql = "select * from test01.user";
                    List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
                return maps;
            }
        
                // 添加
                @RequestMapping("/jdbc/adduser")
                public String addUser(){
                    String sql = "insert into test01.user(id, name, pwd) values(100, 'xxxx', 'xxxpwd')";
                    // 自动提交事务
                    jdbcTemplate.update(sql);
                return "add end";
            }
        
                // 修改
                @RequestMapping("/jdbc/upuser/{id}")
            public String updateUser(@PathVariable("id") int id){
                String sql = "update test01.user set name=?, pwd=? where id="+id;
        
                    // 封装对象
                    Object[] objects = new Object[2];
                    objects[0] = "yyy";
                    objects[1] = "yyypwd";
                    jdbcTemplate.update(sql, objects);
                    return "update end";
                }
                // 删除
                @RequestMapping("jdbc/deluser/{id}")
                public String deleteUser(@PathVariable("id") int id){
                    String sql = "delete from test01.user where id = ?";
                    jdbcTemplate.update(sql, id);
                    return "delete end";
                }
            }
            ```

## 整合数据源-Druid
[top](#catalog)
- 整合方式
    - 导入maven依赖
    - 在 `applicatio.yaml` 配置文件中添加数据库配置
    - 如果配置中包含springBoot默认配置之外的内容，需要手动添加配置类，并与配置文件绑定
    - 在配置类中添加后台监控功能
- 示例
    - spring配置
        - 参考配置
            
            - [/java/mylearn/myspringboot/src/main/resources/config/application.yaml](/java/mylearn/myspringboot/src/main/resources/config/application.yaml)
        - 配置内容
            ```yaml
            spring:
              # 配置数据库连接
              datasource:
                username: root
                password: 1234
                url: jdbc:mysql://127.0.0.1:3307?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&            useSSL=false
                driver-class-name: com.mysql.cj.jdbc.Driver
                type: com.alibaba.druid.pool.DruidDataSource
            
                # SpringBoot 默认不注入的配置，需要手动绑定
                # druid专有配置
                initialSize: 5
                minIdle: 5
                maxActive: 20
                maxWait: 60000
                timeBetweenEvictionRunsMillis: 60000
                minEvictableIdleTimeMillis: 300000
                validationQuery: SELECT 1 FROM DUAL
                testWhileIdle: true
                testOnBorrow: false
                testOnReturn: false
                poolPreparedStatements: true
            
                # 配置监控统计拦截的filters，stat:监控统计，log4j日志记录，wall:防御sql注入
                # 需要导入log4j的配置
                filters: stat,wall,log4j
                maxPoolPreparedStatementPerConncetionSize: 20
                useGlobalDataSourceStat: true
                connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
            ```
    - 配置类
        - 参考代码
            
            - [/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/config/druid/DruidConfig.java](/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/config/druid/DruidConfig.java)
        - 代码内容
            ```java
        @Configuration
        public class DruidConfig {
        
                // 接管配置文件，使当前类与配置文件绑定
                @ConfigurationProperties(prefix = "spring.datasource")
                @Bean
                public DataSource druidDataSource(){
                return new DruidDataSource();
            }
        
                // 后台监控功能
                // 以web.xml的方式配置Servlet
                // 由于SpringBoot 内置了servlet，所以没有web，
                // 需要使用ServletRegistrationBean来替代
                @Bean
                public ServletRegistrationBean statViewBean(){
                    ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
                    // 后台登录账号密码，与web.xml中的配置类似
                    Map<String, String> initParamters = new HashMap<>();
                    // 1. 账号密码对应的key是固定
                    initParamters.put("loginUsername", "admin");
                    initParamters.put("loginPassword", "12345");
                    // 2. 允许谁可以访问
                    // 如果value为空字符，则任何人都可以访问
                    // localhost：只有本机可以访问
                    initParamters.put("allow", "localhost");
                    bean.setInitParameters(initParamters);
                return bean;
            }
        
                @Bean
                public FilterRegistrationBean filterBean(){
                    FilterRegistrationBean bean = new FilterRegistrationBean();
                    // 添加过滤器
                    bean.setFilter(new WebStatFilter());
                    // 设置过滤请求
                    // 可以过滤的内容参考 WebStatFilter 中的关键字
                    Map<String, String> initParameters = new HashMap<>();
                    initParameters.put("exclusions", "*.js，*.css,/druid/*");
                    bean.setInitParameters(initParameters);
                    return bean;
                }
            }
            ```

## Mybatis整合
[top](#catalog)
- 整合方法
    1. 需要数据库(mysql)和JDBC的maven依赖
    2. 在maven依赖中引入mybatis的starter
        ```xml
        <!-- https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.2</version>
        </dependency>
        ```
    3. 添加mapper
        - 方式1：创建mapper接口
            - 使用@Mapper注解标识当前接口是mybatis的mapper
            - 使用@Repository注解，让spring接管
                ```java
                @Mapper
                public interface XXXXMapper {
                }
                ```
        - 方式2：使用mapper包扫描注解
            ```java
            @MapperScan("包的全路径")
            ```
    4. 添加spring-mybatis配置
    5. 在业务类中自动装配Mapper

- 示例
    1. 创建Mapper，采用mapper和xml分离的方式
        - 参考代码
            - [/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/dao/mybatis/UserMapper.java](/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/dao/mybatis/UserMapper.java)
            - [/java/mylearn/myspringboot/src/main/resources/mybatis/mapper/UserMapper.xml](/java/mylearn/myspringboot/src/main/resources/mybatis/mapper/UserMapper.xml)
        - mapper内容
            ```java
            @Mapper
            @Repository
            public interface UserMapper {
                List<User> getUsers();
                User getUserById(int id);
                void insertUser(User u);
                void updateUser(User u);
                void deleteUserById(int id);
            }
            ```
        - 配置内容
            ```xml
            <mapper namespace="com.ljs.learn.myspringboot.dao.mybatis.UserMapper">
                <select id="getUsers" resultType="user">
                    select * from test01.user;
                </select>
            
                <select id="getUserById" parameterType="int" resultType="user">
                    select * from test01.user where id = #{id};
                </select>
            
                <insert id="insertUser" parameterType="user">
                    insert into test01.user (id, name, pwd) values (#{id}, #{name}, #{pwd});
                </insert>
            
                <update id="updateUser" parameterType="user">
                    update test01.user set name=#{name} where id = #{id}
                </update>
            
                <delete id="deleteUserById" parameterType="int">
                    delete from test01.user where id = #{id}
                </delete>
            </mapper>
            ```
    2. 在spring配置文件中添加 mybatis 的配置
        - 参考配置
            
            - [/java/mylearn/myspringboot/src/main/resources/config/application.yaml](/java/mylearn/myspringboot/src/main/resources/config/application.yaml)
        - 配置内容
            ```yaml
            mybatis:
              #配置别名
              type-aliases-package:  com.ljs.learn.myspringboot.bean
              #指定mapper.xml的保存路径
              mapper-locations: 'classpath:mybatis/mapper/*.xml'
            ```
    - 在 Controller 中调用 Mapper
        - 参考代码
            
            - [/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/controller/mybatis/MybatisController.java](/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/controller/mybatis/MybatisController.java)
        - 代码内容
            ```java
            @RestController
            public class MybatisController {
                // 自动装配mapper
                @Autowired
                UserMapper dao;
            
                // 查询所有用户
                @RequestMapping("/mybatis/user/userlist")
                public List<User> userList(){
                    List<User> users = dao.getUsers();
                    return users;
                }
            
                // 查询
                @RequestMapping("/mybatis/user/get/{id}")
                public User userList(@PathVariable("id") int id){
                    User users = dao.getUserById(id);
                    return users;
                }
            
                // 添加
                @RequestMapping("/mybatis/user/adduser")
                public String addUser(){
                    String sql = "insert into test01.user(id, name, pwd) values(, )";
                    dao.insertUser(new User(200, "xxxx", "xxxpwd"));
                    return "add end";
                }
            
                // 修改
                @RequestMapping("/mybatis/user/upuser/{id}")
                public String updateUser(@PathVariable("id") int id){
                    dao.updateUser(new User(id, "yyy", "yyypwd"));
                    return "update end";
                }
                // 删除
                @RequestMapping("/mybatis/user/deluser/{id}")
                public String deleteUser(@PathVariable("id") int id){
                    dao.deleteUserById(id);
                    return "delete end";
                }
            }
            ```

# 任务
## 异步任务
[top](#catalog)
-　设置异步任务的方法
    - 使用 `@Async` 注解标识一个异步方法
    - 使用 `@EnableAsync` 开启异步Controller
- 示例
    - 标识Service中的一个异步方法
        - 参考代码
            
            - [/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/service/mission/AsyncService.java](/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/service/mission/AsyncService.java)
        - 代码内容
            ```java
            @Service
            public class AsyncService {
                // 使用注解标识一个异步方法
                @Async
                public void hello(){
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("running");
                }
            }
            ```
    - 在Controler中异步调用Service中的方法    
        - 参考代码
            
            - [/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/controller/mission/AsyncController.java](/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/controller/mission/AsyncController.java)    
        - 代码内容
            ```java
            @EnableAsync
            @RestController
            public class AsyncController {
                @Autowired
                private AsyncService service;
            
                @GetMapping("/async/hello")
                public String hello(){
                    service.hello();
                    return "end";
                }
            }
            ```

## 定时任务
[top](#catalog)
- 两个注解
    - `@EnableScheduling`
        - 开启定时功能的注解
        - 标识SpringBoot启动类类
    - `@Scheduled(cron="cron表达式")`
        - 标识方法，定义一个定时任务
        - 使用cron表达式定义定时任务
- 示例
    - 参考代码
        
        - [/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/service/mission/SchedulerService.java](/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/service/mission/SchedulerService.java)
    - 代码内容
        ```java
        @Service
        public class SchedulerService {
            // 设置定时任务
            @Scheduled(cron="2 * * * * 0-7")
            public void hello(){
                System.out.println("hello running");
            }
        }
        ```

# web开发
## 导入静态资源
### 静态资源的导入位置
[top](#catalog)
- 与资源导入相关的配置
    - `spring.mvc.static-path-pattern`
        - 负责浏览器访问时的路径
        - 默认全部资源都映射到`/**`中，即`localhost:8080/`下
        - 如果该属性为空，则**不会导入**默认的静态资源，**无法访问**自定义的静态资源
        
    - `spring.resources.static-locations`
        - 负责自定义的静态资源位置
        - 使用后会覆盖默认的静态资源路径
        - 可以使用数组，也可以用 `,` 分割多个路径

- 资源配置的位置
    - 自定义资源位置
        - 在配置文件中自定义静态资源位置，属性`spring.resources.static-locations`
    - 默认配置
        - 导入路径
            - 默认的导入路径
                - classpath:/META-INF/resources/
                - 工程目录/src/main/resources/resources/
                - 工程目录/src/main/resources/static/
                - 工程目录/src/main/resources/public/
            - webjars
                - 所有pom.xml中配置的webjar，项目启动后都导入到 `/META-INF/resources/webjars/`
        - 路径的优先级
            - resources > static > public
            - 创建springboot工程时，默认使用的是：static
            - 文件同名时，会访问优先级高的

### 静态资源导入原理
[top](#catalog)
- 源码分析：WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter
    - 如果有自定义配置则使用自定义配置
    - 如果没有自定义配置
        1. 如果pom.xml中有`webjars`的相关配置，则将其导入到 `classpath:/META-INF/resources/webjars/` 目录下
            - 在浏览器中通过 `站点/webjars/**` 来访问
        2. 导入几个默认路径下的资源，参考：`ResourceProperties.CLASSPATH_RESOURCE_LOCATIONS`
            - classpath:/META-INF/resources/
            - classpath:/resources/
            - classpath:/static/
            - classpath:/public/
    - 源码
        ```java
        @Import({WebMvcAutoConfiguration.EnableWebMvcConfiguration.class})
            @EnableConfigurationProperties({WebMvcProperties.class, ResourceProperties.class})
            @Order(0)
            public static class WebMvcAutoConfigurationAdapter implements WebMvcConfigurer {
            
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // 如果配置文件中有自定以的相关内容，就直接返回
                if (!this.resourceProperties.isAddMappings()) {
                    logger.debug("Default resource handling disabled");
                } else {
                    // 如果没有自定义配置，则导入默认资源
                    Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
                    CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
                    // 导入webjars
                    if (!registry.hasMappingForPattern("/webjars/**")) {
                        this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{"/webjars/**"})
                            .addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"})
                            .setCachePeriod(this.getSeconds(cachePeriod))
                            .setCacheControl(cacheControl));
                    }
            
                    String staticPathPattern = this.mvcProperties.getStaticPathPattern();
                    // 导入默认配置
                    // 通过 this.resourceProperties.getStaticLocations 获取静态资源的位置
                    // 即 ResourceProperties.staticLocations属性
                    if (!registry.hasMappingForPattern(staticPathPattern)) {
                        this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{staticPathPattern})
                            .addResourceLocations(WebMvcAutoConfiguration.getResourceLocations(this.resourceProperties.getStaticLocations()))
                            .setCachePeriod(this.getSeconds(cachePeriod))
                            .setCacheControl(cacheControl));
                    }
            
                }
            }
        }
        ```

- 与静态资源导入相关的配置资源类：`WebMvcProperties`
    - 静态资源的映射路径 `staticPathPattern`，初始化时默认为：`/**`
    ```java
    @ConfigurationProperties(prefix = "spring.mvc")
    public class WebMvcProperties {
        private Format messageCodesResolverFormat;
        private Locale locale;
        private WebMvcProperties.LocaleResolver localeResolver;
        private String dateFormat;
        private boolean dispatchTraceRequest;
        private boolean dispatchOptionsRequest;
        private boolean ignoreDefaultModelOnRedirect;
        private boolean publishRequestHandledEvents;
        private boolean throwExceptionIfNoHandlerFound;
        private boolean logResolvedException;
        private String staticPathPattern; // 静态资源的映射路径
        private final WebMvcProperties.Async async;
        private final WebMvcProperties.Servlet servlet;
        private final WebMvcProperties.View view;
        private final WebMvcProperties.Contentnegotiation contentnegotiation;
        private final WebMvcProperties.Pathmatch pathmatch;
        
        public WebMvcProperties() {
            this.localeResolver = WebMvcProperties.LocaleResolver.ACCEPT_HEADER;
            this.dispatchTraceRequest = false;
            this.dispatchOptionsRequest = true;
            this.ignoreDefaultModelOnRedirect = true;
            this.publishRequestHandledEvents = true;
            this.throwExceptionIfNoHandlerFound = false;
            this.logResolvedException = false;
            this.staticPathPattern = "/**"; // 初始化全部映射到 http://localhost:8080/
            this.async = new WebMvcProperties.Async();
            this.servlet = new WebMvcProperties.Servlet();
            this.view = new WebMvcProperties.View();
            this.contentnegotiation = new WebMvcProperties.Contentnegotiation();
            this.pathmatch = new WebMvcProperties.Pathmatch();
        }
        // ...
    }
    ```
  
- 与静态资源导入相关的配置资源类: `ResourceProperties`
    - 通过 `staticLocations` 来设置静态资源的路径
    ```java
    @ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
    public class ResourceProperties {
        // 默认的资源路径
        private static final String[] CLASSPATH_RESOURCE_LOCATIONS = new String[]{"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"};
        private String[] staticLocations;
        private boolean addMappings;
        private final ResourceProperties.Chain chain;
        private final ResourceProperties.Cache cache;
        
        public ResourceProperties() {
            this.staticLocations = CLASSPATH_RESOURCE_LOCATIONS;
            this.addMappings = true;
            this.chain = new ResourceProperties.Chain();
              this.cache = new ResourceProperties.Cache();
        }
    }
    ```

### 静态资源导入示例
[top](#catalog)
- 导入 `webjars`
    - maven依赖
        - 参考配置
            
            - [/java/mylearn/myspringboot/pom.xml](/java/mylearn/myspringboot/pom.xml) 
        - 配置内容
            ```xml
            <dependency>
                <groupId>org.webjars</groupId>
                <artifactId>jquery</artifactId>
                <version>3.5.0</version>
            </dependency>
            ```
    - 启动后，通过浏览器访问静态资源
        
        - http://localhost:8083/webjars/jquery/3.5.0/jquery.js
    
- 导入自定义静态资源
    - 添加配置
        ```yaml
        spring:
          resources:
            static-locations: classpath:/mystatic/
        ```
    - 将资源全部添加到 `src/resources/mystatic` 下
    - 通过 localhost:8080/ 来访问


## 首页定制
### 定制方法
[top](#catalog)
- 创建 `index.xml` 文件，并保存在静态资源目录中
- 启动工程后直接通过：localhost:8080/，就可以直接访问首页

### 首页定制的原理
[top](#catalog)
- WebMvcAutoConfiguration.EnableWebMvcConfiguration 中的获取方式
    - 从静态资源中搜索: `index.html`
    ```java
    @Bean
    public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext, FormattingConversionService mvcConversionService, ResourceUrlProvider mvcResourceUrlProvider) {
        WelcomePageHandlerMapping welcomePageHandlerMapping = new WelcomePageHandlerMapping(
            new TemplateAvailabilityProviders(applicationContext), 
            applicationContext, this.getWelcomePage(), 
            this.mvcProperties.getStaticPathPattern()
        );
        welcomePageHandlerMapping.setInterceptors(this.getInterceptors(mvcConversionService, mvcResourceUrlProvider));
        return welcomePageHandlerMapping;
    }
    
    private Optional<Resource> getWelcomePage() {
        //从静态资源中获取 index.html
        String[] locations = WebMvcAutoConfiguration.getResourceLocations(this.resourceProperties.getStaticLocations());
        return Arrays.stream(locations).map(this::getIndexHtml).filter(this::isReadable).findFirst();
    }
    
    private Resource getIndexHtml(String location) {
        return this.resourceLoader.getResource(location + "index.html");
    }
    
    private boolean isReadable(Resource resource) {
        try {
            return resource.exists() && resource.getURL() != null;
        } catch (Exception var3) {
            return false;
        }
    }
    ```

## 模版引擎Thymeleaf
### 为什么使用Thymeleaf而不是jsp
[top](#catalog)
- 为什么不使用jsp
    - SpringBoot的项目是以jar包的形式存在的，不是war，无法使用jsp
    - SpringBoot使用的是嵌入式的tomcat，默认不支持jsp
- SpringBoot推荐使用 `Thymeleaf` 模版引擎

### Thymeleaf的maven依赖
[top](#catalog)
```xml
<!-- https://mvnrepository.com/artifact/org.thymeleaf/thymeleaf-spring5 -->
<dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf-spring5</artifactId>
</dependency>
<!-- https://mvnrepository.com/artifact/org.thymeleaf.extras/thymeleaf-extras-java8time -->
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-java8time</artifactId>
</dependency>
```

### Thymeleaf的使用方法
[top](#catalog)
- 参考：https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#a-multi-language-welcome

- 基本的语法
    - 表达式
        - `${}`，变量
        - `*{}`，选择变量表达式
        - `#{}`，消息
        - `~{}`，片段表达式
        
    - 在html页面中，通过 `th:xxxx` 在标签中绑定值

- 开发的步骤
    - 导入maven以阿里
    - 将所有的页面代码放在：`/src/main/resources/templates/` 目录下
    - 在html中添加thymeleaf的命名空间
        ```html
        <html xmlns:th="http://www.thymeleaf.org">
        ```
    - Controller 中定义返回String类型的方法，返回值是html文件的名字（不加.html后缀）

- 示例
    - html页面
        - 参考代码
            
            - [/java/mylearn/myspringboot/src/main/resources/templates/test.html](/java/mylearn/myspringboot/src/main/resources/templates/test.html)
        - 代码内容
            ```html
            <!DOCTYPE html>
            <!--导入命名空间-->
            <html lang="en" xmlns:th="http://www.thymeleaf.org">
            <head>
                <meta charset="UTF-8">
                <title>Title</title>
            </head>
            <body>
            <p>this is thymeleaf test</p>
            <div th:text="${msg}"></div>
            </body>
            </html>
            ```
    - Controller
        - 参考代码
            
            - [/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/controller/thymeleaf/TestController.java](/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/controller/thymeleaf/TestController.java) 
        - 代码内容
            ```java
            @Controller
            public class TestController {
                @RequestMapping("/thymeleaf/test01")
                public String test01(Model model){
                    model.addAttribute("msg", "th test");
                    return "test";
                }
            }
            ```
    - 访问路径
        
        - http://localhost:8083/thymeleaf/test01

### SpringBoot中Thymeleaf的配置原理
[top](#catalog)
- 源码：ThymeleafProperties
    ```java
    @ConfigurationProperties(prefix = "spring.thymeleaf")
    public class ThymeleafProperties {
        private static final Charset DEFAULT_ENCODING;
        // 文件的保存路径
        public static final String DEFAULT_PREFIX = "classpath:/templates/";
        // 有效文件的后缀
        public static final String DEFAULT_SUFFIX = ".html";
        private boolean checkTemplate = true;
        private boolean checkTemplateLocation = true;
        // 文件的保存路径
        private String prefix = "classpath:/templates/";
        // 有效文件的后缀
        private String suffix = ".html";
        private String mode = "HTML";
        private Charset encoding;
        private boolean cache;
        private Integer templateResolverOrder;
        private String[] viewNames;
        private String[] excludedViewNames;
        private boolean enableSpringElCompiler;
        private boolean renderHiddenMarkersBeforeCheckboxes;
        private boolean enabled;
        private final ThymeleafProperties.Servlet servlet;
        private final ThymeleafProperties.Reactive reactive;
    
        public ThymeleafProperties() {
            this.encoding = DEFAULT_ENCODING;
            this.cache = true;
            this.renderHiddenMarkersBeforeCheckboxes = false;
            this.enabled = true;
            this.servlet = new ThymeleafProperties.Servlet();
            this.reactive = new ThymeleafProperties.Reactive();
        }
    }
    ```

## 扩展SpringMVC配置
[top](#catalog)
- 参考：https://docs.spring.io/spring-boot/docs/2.3.0.BUILD-SNAPSHOT/reference/html/spring-boot-features.html#boot-features-spring-mvc

- 扩展mvc的配置
    - 配置类使用 `@Configuration` 标识
    - 实现 `WebMvcConfigurer` 接口
    - 不能使用 `@EnableWebMvc` 注解，否则会用当前配置类接管SpringBoot的默认配置
    - `ContentNegotiatingViewResolver` 实现了`ViewResolver`视图解析器接口类，可以将其看作视图解析器 

- 示例
    - 参考代码
        
        - [/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/config/MyMVCConfig.java](/java/mylearn/myspringboot/src/main/java/com/ljs/learn/myspringboot/config/MyMVCConfig.java)
    - 代码内容
        ```java
        // 定制SpringMVC的方式：
        // 实现：WebMvcConfigurer
        // 写相关的组件，然后交给SpringBoot
        @Configuration
        public class MyMVCConfig implements WebMvcConfigurer {
            // 配置自定义视图解析器
            // 启动后会自动加载到DispatcherServlet的viewResolvers中
            @Bean
            public ViewResolver myViewReslover(){
                return new MyViewReslover();
            }
        
            // 自定义视图解析器
            public static class MyViewReslover implements ViewResolver{
                @Override
                public View resolveViewName(String s, Locale locale) throws Exception {
                    return null;
                }
            }
        
            // 自定义视图跳转
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/index").setViewName("test");
            }
        }
        ```

## 国际化
### 国际化配置方法
[top](#catalog)
1. 添加国际化配置文件
    - 添加目录 `/resources/i8n`
    - 在目录下添加配置配置文件
        - 自定义文件名.properties 默认
        - 自定义文件名_en_US.properties 中文
        - 自定义文件名_zh_CN.properties 英文
    - 文件名为：默认文件名_国家_地区
2. 将国际化配置目录添加到spring的配置文件中
    - application.yaml中添加配置：
        ```
        spring:
          messages
            basename: i18n.自定义文件名
        ```
3. 3各配置文件合成一个绑定资源，配置内容：`login.XXX`
4. 选择配置，并打开 Resource Bundle，分别编辑各配置下的显示内容
5. 修改模版中的国际化内容:`th:text/value=@{login.XXX}`
6. 添加自定义国际化处理器 `LocaleResolver`，并注册到MVC配置类中

### 执行国际化的原理
[top](#catalog)
1. 启动时，获取国际化配置
    ```java
    public class WebMvcAutoConfiguration {
        public LocaleResolver localeResolver() {
            // 如果有自定义配置则返回自定义配置
            if (this.mvcProperties.getLocaleResolver() == org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.LocaleResolver.FIXED) {
                return new FixedLocaleResolver(this.mvcProperties.getLocale());
            } else {
            // 如果没有自定义配置则使用springBoot默认的配置
                AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
                localeResolver.setDefaultLocale(this.mvcProperties.getLocale());
                return localeResolver;
            }
        }
    }
    ```
2. 通过 `LocaleResolver` 接口来解析请求并获取国际化参数
    - 接口源码
        ```java
        public interface LocaleResolver {
            // 解析请求
            Locale resolveLocale(HttpServletRequest var1);
        
            void setLocale(HttpServletRequest var1, @Nullable HttpServletResponse var2, @Nullable Locale var3);
        }
        ```

    - springBoot提供的默认实现
        ```java
        public class AcceptHeaderLocaleResolver implements LocaleResolver {
            public void setDefaultLocale(@Nullable Locale defaultLocale) {
                this.defaultLocale = defaultLocale;
            }
        
            public Locale resolveLocale(HttpServletRequest request) {
                Locale defaultLocale = this.getDefaultLocale();
                if (defaultLocale != null && request.getHeader("Accept-Language") == null) {
                    return defaultLocale;
                } else {
                    Locale requestLocale = request.getLocale();
                    List<Locale> supportedLocales = this.getSupportedLocales();
                    if (!supportedLocales.isEmpty() && !supportedLocales.contains(requestLocale)) {
                        Locale supportedLocale = this.findSupportedLocale(request, supportedLocales);
                        if (supportedLocale != null) {
                            return supportedLocale;
                        } else {
                            return defaultLocale != null ? defaultLocale : requestLocale;
                        }
                    } else {
                        return requestLocale;
                    }
                }
            }
        }
        ```
    
### 自定义国际化处理
[top](#catalog)
- 自定义 `LocaleResolver` 接口实现类，并注册到自定义的mvc配置类中
- `Locale resolveLocale(HttpServletRequest var1);`方法中，常用的解析方式
    - 从url，或请求头中获取语言设置参数
    - 解析参数，并用这些参数生成 `java.util.Locale` 对象
    - 将生成的对象返回
    
- 示例
    - 参考代码
        
        - [/java/mylearn/myspringboot-sample/src/main/java/com/ljs/learn/myspringbootsample/config/MyLocaleResolver.java](/java/mylearn/myspringboot-sample/src/main/java/com/ljs/learn/myspringbootsample/config/MyLocaleResolver.java)
    - 代码内容
        ```java
        public class MyLocaleResolver implements LocaleResolver {
            @Override
            public Locale resolveLocale(HttpServletRequest req) {
                // 获取请求参数中的语言参数
                String lang = req.getParameter("l");
                Locale locale; //如果没有就使用默认的
        
                // 如果请求链接中携带了国际化的参数
                if (!StringUtils.isEmpty(lang)){
                    // 将国际化参数分解成：国家、地区
                    String[] split = lang.split("_");
                    locale = new Locale(split[0], split[1]);
                }else{
                    locale = Locale.getDefault();
                }
        
                return locale;
            }
        
            @Override
            public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
        
            }
        }
        ```

## 自定义拦截器
[top](#catalog)
- 开发方法
    - 自定义HandlerInterceptor接口实现
    - 注册代码MVC配置类中，并配置拦截哪些资源
        ```java
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new 自定义接口实现())
                    .addPathPatterns("/**") // 需要过滤的请求
                    .excludePathPatterns(
                        "/index.html", "/", "/user/login",
                        "/css/**", "/js/**", "/img/**"
                    );  //排除多余的请求
        }
        ```
- 示例
    - 参考代码
        - [/java/mylearn/myspringboot-sample/src/main/java/com/ljs/learn/myspringbootsample/config/LoginHandleInterceptor.java](/java/mylearn/myspringboot-sample/src/main/java/com/ljs/learn/myspringbootsample/config/LoginHandleInterceptor.java)
        - [/java/mylearn/myspringboot-sample/src/main/java/com/ljs/learn/myspringbootsample/config/MyMvcController.java](/java/mylearn/myspringboot-sample/src/main/java/com/ljs/learn/myspringbootsample/config/MyMvcController.java)
    - 自定义HandlerInterceptor接口实现
        ```java
        public class LoginHandleInterceptor implements HandlerInterceptor {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                // 登录成功之后，会有用户session
                Object loginUser = request.getSession().getAttribute("loginUser");
        
                if (loginUser == null){
                    //未登录
                    request.setAttribute("msg","未登录");
                    request.getRequestDispatcher("/index.html").forward(request, response);
                    // response.sendRedirect("index.html");
                    return false;
                } else {
                    return true;
                }
            }
        }
        ```
    - 配置资源拦截
        ```java
        @Configuration
        public class MyMvcController implements WebMvcConfigurer {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                // 拦截请求
                // 首页和登录请求放行
                // 静态资源放行
                registry.addInterceptor(new LoginHandleInterceptor())
                        .addPathPatterns("/**")
                        .excludePathPatterns(
                            "/index.html", "/", "/user/login",
                            "/css/**", "/js/**", "/img/**"
                        );
            }
        }
        ```

## web开发流程
[top](#catalog)
- 示例参考
    - [/java/mylearn/myspringboot-sample](/java/mylearn/myspringboot-sample)

- 开发步骤
    1. 创建pojo
    2. 创建DAO模拟数据
    3. 导入静态资源 到static，导入后都默认映射到`/`
    4. 导入模版到templates，导入thymeleaf的maven依赖
        - 模版中的内部资源链全部改成`@{开发的资源目录}`，启动后会自动添加站点和应用名
        - 国际化内容使用：`#{}`
        - 显示变量的值使用：`${}`
        - 请求url：`@{}`
    5. 自定义mvc配置来控制首页跳转
    6. 配置当前应用名：`server.servlet.context-path`
    7. 配置国际化组件
    8. 配置登录拦截器
        - 登录控制器将用户信息保存到session中
        - 拦截器通过session来判断用户是否登录
    9. 增删改查
        - 参考代码
            - [java/mylearn/myspringboot-sample/src/main/java/com/ljs/learn/myspringbootsample/controller/EmployeeController.java](java/mylearn/myspringboot-sample/src/main/java/com/ljs/learn/myspringbootsample/controller/EmployeeController.java)
        - 抽取公共页面部分
            - 抽取公共页面部分，设置页面选中标的显示高亮
                - `th:fragment="sidebar"`
                - 不传参`<div th:replace="~{commons/commons::topbar}"></div>`
                - 传参`<div th:replace="~{commons/commons::sidebar(active='main.html')}"></div>`
                - 解析参数 `th:class="${active=='main.html'?'nav-link active':'nav-link'}"`
        - 查询
            - 添加员工查询逻辑
            - 列表循环展示
        - 添加
            - 都使用employ请求，使用RestFul风格，通过请求方式来区分执行添加操作，和跳转添加页面
            - `department.id`，需要提交一个属性，springBoot自动封装盛一个对象
        - 修改
        - 删除
    10. 处理404页面
        - 命名为`404.html`


# 其他
- 修改tomcat的启动端口
    - `resources/application.properties` 
- 修改banner
    - 添加banner文本文件：`resources/banner.txt`
    - 启动工程后，会自动使用自定义的 banner.txt 文件
- springboot 默认会优先加载系统环境变量
    - 如果使用了和环境变量同名的配置时，则只能获取到环境变量的值，无法从配置中获取数据
    - 解决方法
        - 没有解决方法，只能让配置内容尽量和环境变量不同
