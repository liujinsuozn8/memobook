<span id="catalog"></span>

### 目录
- [log4j介绍](#log4j介绍)
- [log4j的基本使用方法](#log4j的基本使用方法)
- [配置文件说明-log4.properties](#配置文件说明-log4.properties)
    - [log4j配置的4个关键点](#log4j配置的4个关键点)
    - [log4j的日志级别](#log4j的日志级别)
    - [示例配置说明](#示例配置说明)
- [](#)
- [](#)
- [](#)

# log4j介绍
[top](#catalog)
- log4j是什么
    - 一个开源的、轻量级的，用于日志管理的框架
    - Log4j是Apache的一个开放源代码项目，Log4j可以：
        - 控制日志信息输送的目的地，如：
            - 控制台
            - 文件
            - GUI组件
            - 套接口服务器
            - NT的事件记录器
        - 设置日志输出的格式
        - 定义日志的级别，更加精细的控制日志的生成过程
    - Log4j通过配置文件来进行设置，无需修改代码
    
- log4j能做什么
    - 日志监控打印
        - 如：在项目试运行期需要记录用户的所有操作，正式上线后某些日志可以省略
    - 可以向日志中添加内容，如时间和线程信息
    - 程序调试期间，记录运行的步骤和代码行号
    - 配置多个日志的输出源，如：
        - 数据库
        - 控制台
        - 日志文件
        
- log4j下载
    - 官网：https://logging.apache.org    
    
# log4j的基本使用方法
1. 引入maven依赖
    ```xml
    <!-- https://mvnrepository.com/artifact/log4j/log4j -->
    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>1.2.17</version>
    </dependency>
    ```
2. 添加配置文件
    - 配置文件路径：`工程名/src/main/resources/log4j.properties` 
3. 在类中通过静态方法获取日志对象，并保存到静态字段中
    ```java
    class XXX{
       private static final Logger logger = Logger.getLogger(类的Class对象);
    }
    ```
4. 在方法中，使用日志对象输出不同级别的日志
    ```java
    public class UserService {
        private static final Logger logger = Logger.getLogger(UserService.class);
    
        public static void main(String[] args) {
            logger.debug("UserService test debug");
            logger.info("UserService test info");
            logger.error("UserService test error");
            logger.warn("UserService test warn");
        }
    }
    ```

# 配置文件说明-log4.properties
## log4j配置的4个关键点
[top](#catalog)
1. 目的地（appender）：表示日志输出到哪里
2. 布局（layout）
3. 控制单元（logger）
4. 级别（level）

## log4j的日志级别
[top](#catalog)
- **log4j建议使用的4个级别**
    1. ERROR
    2. WARN
    3. INFO
    4. DEBUG
- 所有日志级别
    - OFF、FATAL、ERROR、WARN、INFO、DEBUG、ALL
- 级别的大小关系
    - DEBUG < INFO < WARN < ERROR
- 打印日志时，会打印所有**大于等于当前日志级别**的日志
    
## 示例配置说明
[top](#catalog)
- 参考配置
    - [/java/mylearn/mylog4j/src/main/resources/log4j.properties](/java/mylearn/mylog4j/src/main/resources/log4j.properties)

- 注意事项
    - **目的地**一般会配置两个：控制台+本地的日志文件
    - 一般的目的地配置：`log4j.appender.公司名.目的地`
    - `#`表示注释
    - properties文件对中文支持不好
    
- 配置内容说明
    ```properties
    # 1. 配置目的地：输出到文件--日滚动日志文件
    log4j.appender.ljs.File=org.apache.log4j.DailyRollingFileAppender
    # 设置日志文件名的路径+文件名
    # 相对路径是当前工程，如果有父工程则相对路径是父工程
    # 如果路径不存在，会自动创建
    log4j.appender.ljs.File.file=mylog4j/logs/ljs_log_file.log
    # 设置日志文件名的时间戳
    log4j.appender.ljs.File.DatePattern=.yyy-MM-dd
    # 开启自定义日志格式
    log4j.appender.ljs.File.layout=org.apache.log4j.PatternLayout
    # 配置日志格式 
    # %d，定义日期格式
    # %5p，日志级别字符串对齐，5位补空格
    # (%C:%M) - %m%n，全类名:方法名 - message 换行符
    log4j.appender.ljs.File.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss, SSS} %5p (%C:%M) - %m%n
    
    #----------------------------------------------------------------------------------------
    # 2. 配置目的地：输出到控制台
    log4j.appender.ljs.Console=org.apache.log4j.ConsoleAppender
    log4j.appender.ljs.Console.layout=org.apache.log4j.PatternLayout
    log4j.appender.ljs.Console.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss, SSS} %5p (%C:%M) - %m%n

    #----------------------------------------------------------------------------------------
    # 3. 配置日志的根控制器（默认控制器）
    # debug，表示日志级别
    # 控制日志输出到Console和File中 
    log4j.rootLogger=debug,ljs.File,ljs.Console
    ```
- 测试
    - 参考代码
        - [/java/mylearn/mylog4j/src/main/java/com/ljs/learn/mylog4j/UserService.java](/java/mylearn/mylog4j/src/main/java/com/ljs/learn/mylog4j/UserService.java)
    - 代码内容
        ```java
        public class UserService {
            private static final Logger logger = Logger.getLogger(UserService.class);
        
            public static void main(String[] args) {
                logger.debug("UserService test debug");
                logger.info("UserService test info");
                logger.error("UserService test error");
                logger.warn("UserService test warn");
            }
        }
        ```