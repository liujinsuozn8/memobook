# spring5自带的日志
- 整合方法
    - 引入log4j2
        ```xml
        <dependency>
          <groupId>org.apache.logging.log4j</groupId>
          <artifactId>log4j-api</artifactId>
          <version>2.11.2</version>
        </dependency>
    
        <dependency>
          <groupId>org.apache.logging.log4j</groupId>
          <artifactId>log4j-core</artifactId>
          <version>2.11.2</version>
        </dependency>
    
        <dependency>
          <groupId>org.apache.logging.log4j</groupId>
          <artifactId>log4j-slf4j-impl</artifactId>
          <version>2.11.2</version>
        </dependency>
    
        <dependency>
          <groupId>org.slf4j</groupId>
          <artifactId>slf4j-api</artifactId>
          <version>1.7.30</version>
        </dependency>
        ```
    - 在resources下添加配置 `log4j2.xml`
        ```xml
        <?xml version="1.0" encoding="UTF-8" ?>
        <configuration status="DEBUG">
            <appenders>
                <console name="Console" target="SYSTEM_OUT">
                    <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
                </console>
            </appenders>
        
            <loggers>
                <root level="info">
                    <appender-ref ref="Console"/>
                </root>
            </loggers>
        </configuration>
        ```
- 直接使用log对象
    - /java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/log/UserLog.java
    - 示例
        ```java
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        
        public class UserLog {
            private static final Logger logger = LoggerFactory.getLogger(UserLog.class);
        
            public static void main(String[] args) {
                logger.info("user info log test");
                logger.error("user error log test");
            }
        }
        ```

# @Nullable 注解
- @Nullable 用在方法上。表示方法返回可以为空
- @Nullable 用在属性上。表示属性可以为空
- @Nullable 用在方法的参数上。表示参数可以为空

# 函数式注册对象
- 本质: 手动 new 对象，然后注册到容器 
- 示例
    - /java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/genericcontext/MyUser.java
    - 代码内容
        ```java
        public class MyUser {
            public static void main(String[] args) {
                GenericApplicationContext context = new GenericApplicationContext();
                // 手动创建并注册
                context.refresh();
                context.registerBean(MyUser.class, ()-> new MyUser());
                // 获取对象
                MyUser bean = context.getBean(MyUser.class);
                System.out.println(bean);
            }
        }
        ```

# JUnit4整合
- 引入与测试相关的依赖
    ```xml
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>5.2.9.RELEASE</version>
      <scope>test</scope>
    </dependency>
  
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
    ```
- 创建测试类
    - /java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/test/JTest4.java
    - 代码内容
        ```java
        // 指定单元测试的版本
        @RunWith(SpringJUnit4ClassRunner.class)
        // 指定配置
        @ContextConfiguration(classes = {AopConfig.class})
        public class JTest4 {
            @Autowired
            private MathCalculator mathCalculator;
        
            @Test
            public void test(){
                mathCalculator.div(4,2);
            }
        }
        ```

# JUnit5整合
- 引入Junit5
    ```xml
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-api</artifactId>
      <version>5.5.2</version>
      <scope>test</scope>
    </dependency>

    ```
- 示例
    - /java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/test/JTest5.java
    - 代码内容
        ```java
        // @ExtendWith(SpringExtension.class)
        // @ContextConfiguration(classes = {AopConfig.class})
        // 替换上面两个注解
        @SpringJUnitConfig(classes = {AopConfig.class})
        public class JTest5 {
            @Autowired
            private MathCalculator mathCalculator;
        
            @Test
            public void test(){
                mathCalculator.div(4,2);
            }
        }
        ```