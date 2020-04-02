<span id="catalog"></span>

### 目录
- [log4j介绍](#log4j介绍)
- [log4j的基本使用方法](#log4j的基本使用方法)
- [配置文件说明-log4.properties](#配置文件说明-log4.properties)
    - [log4j配置的4个关键点](#log4j配置的4个关键点)
    - [log4j的日志级别](#log4j的日志级别)
    - [示例配置说明](#示例配置说明)
- [配置文件说明-log4.xml](#配置文件说明-log4.xml)
    - [xml配置的基本介绍](#xml配置的基本介绍)
    - [从properties到xml](#从properties到xml)
    - [日志过滤功能](#日志过滤功能)
    - [xml配置示例](#xml配置示例)
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

- 一句化描述log4j的功能：**以什么样的格式，按照日志的优先级，将日志输出到哪里**

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
- 4个关键点
    1. 目的地（appender）：表示日志输出到哪里
    2. 布局（layout）
    3. 控制单元（logger）
    4. 级别（level）

- 常用Appebder
    
    |appender|描述|
    |-|-|
    |org.apache.log4j.ConsoleAppender|控制台输出|
    |org.apache.log4j.FileAppender|输出到文件|
    |org.apache.log4j.DailyRollingFileAppender|每天生产一个日志文件|
    |org.apache.log4j.RollingFileAppnder|文件大小到达指定尺寸时，产生一个新的文件|
    |org.apache.log4j.WriterAppender|将日志信息以流格式发送到任意指定的地方|
    |org.apache.log4j.jdbc.JDBCAppender|把日志用JDBC记录到数据库中|


- 常见Layout
    
    |appender|描述|
    |-|-|
    |org.apache.log4j.HTMLLayout|以html格式输出|
    |org.apache.log4j.PatternLayout|**自定义log格式，最常用**|
    |org.apache.log4j.SimpleLayout|包含日志信息的级别和信息字符串|
    |org.apache.log4j.TTCCLayout|包含日志产生的时间、线程、类别等信息|
    
- 常用PatternLayout

    |pattern|描述|
    |-|-|
    |%m|日志信息|
    |%n|换行符，windows为`\r\n`，Unix为`\n`|
    |%p|日志优先级|
    |%r|从应用启动到输出该log信息时的毫秒数|
    |%c|所属的类的全类名|
    |%t|产生该日志事件的线程名|
    |%d|<ul><li>日志时间点的日期或时间，默认格式为ISO8601，也可以单独指定格式</li><li>常用标准：%d{yyyy-MM-dd HH:mm:ss, SSS}</li></ul>|
    |%l|输出日志事件的发生位置。与 `%F%L%C%M` 相同|
    |%F|java文件名|
    |%L|代码行数|
    |%C|全类名。`%C{1}` 只表示类名|
    |%M|方法名|


- 日志控制器的设置
    - 两种控制器
        - 根logger：log4j.rootLogger
        - 自定义logger：log4j.logger.全类名/包名
    - 使用多个控制器时的规则：
        - 日志级别使用类名最精确的控制器的级别
            - 如下面三个，第二个最精确，指定到了具体的类，所以三个控制器的级别都是 info
                ```properties
                log4j.rootLogger=error,ljs.File,ljs.Console
                log4j.logger.com.ljs.learn.mylog4j.UserService=info,ljs.File,ljs.Console
                log4j.logger.com.ljs.learn.mylog4j=warn,ljs.File,ljs.Console
                ```
        - 每个控制器输出各自的内容，不会互相影响

- 日志的传播机制
    - 自定义logger 是 根logger 下的**子logger**
    - 默认情况下 子logger会继承父logger，子logger的输出也会在父logger中输出

- Appender、Layout、Logger三者之间的关系
    - 每个Appender后面必须跟随Layout，来自定义日志格式
    - 每个Logger都可以指定一个级别，同时引用多个Appender
    - 每个Appender可以被多个Logger引用
    - Logger和Appender之间的关系是`n:m`

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
    # 如果路径不存在，会自动创建
    log4j.appender.ljs.File.file=./logs/ljs_log_file.log
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
    # 3. 配置日志的控制器（默认控制器）
    # debug，表示日志级别
    # 控制日志输出到Console和File中 
    log4j.rootLogger=error,ljs.File,ljs.Console
    log4j.logger.com.ljs.learn.mylog4j.UserService=info,ljs.File,ljs.Console
    log4j.logger.com.ljs.learn.mylog4j=warn,ljs.File,ljs.Console
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

# 配置文件说明-log4.xml
## xml配置的基本介绍
[top](#catalog)
- **`log4j.xml` 的优先级大于 `log4j.properties`**
- 通过xml配置文件可以对日志的输出进行更精细的控制

## 从properties到xml
[top](#catalog)
- appender
    ```properties
    log4j.appender.ljs.File=org.apache.log4j.DailyRollingFileAppender
    log4j.appender.ljs.File.file=mylog4j/logs/ljs_log_file.log
    log4j.appender.ljs.File.DatePattern=.yyy-MM-dd
    log4j.appender.ljs.File.layout=org.apache.log4j.PatternLayout
    log4j.appender.ljs.File.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss, SSS} %5p (%C:%M) - %m%n
    ```
  
    ```xml
    <appender name="log.file" class="org.apache.log4j.DailyRollingFileAppender">
        <param name="File" value="mylog4j/logs/ljs_xmllog_file.log"/>
        <!--已追加的方式写文件，默认为true。若使用false，则每次启动都后删除并新建日志文件-->
        <param name="Append" value="true"/>
        <param name="DatePattern" value=".yyy-MM-dd"/>
        <!--设置日志格式-->
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d{yyyy-MM-dd HH:mm:ss, SSS} %5p (%C:%M) - %m%n"/>
        </layout>
    </appender>
    ```
- 根日志控制器
    ```properties
    log4j.rootLogger=error,ljs.File,ljs.Console
    ```
  
    ```xml
    <root>
        <level value="error"/>
        <appender-ref ref="log.file"/>
        <appender-ref ref="log.console"/>
    </root>
    ```
- 自定义控制器
    ```properties
    log4j.logger.com.ljs.learn.mylog4j=warn,ljs.File,ljs.Console
    ```
  
    ```xml
    <logger name="com.ljs.learn.mylog4j" additivity="true">
        <level value="debug"/>
        <appender-ref ref="log.file"/>
        <appender-ref ref="log.console"/>
    </logger>
    ```

- logger的`additivity`属性
    - 因为存在：`日志的传播机制`，所以自定义logger的输出也会在根logger中输出
    - `additivity`属性默认为 true，自定义logger 的信息也会输出到 根logger
    - `additivity`设为 false 时，会关闭日志传播，**日志只会在当前logger中输出**，使根logger的配置失效
    - `additivity`属性可以与 `日志过滤` 一起配置，来精细化控制日志的输出内容

## 日志过滤功能
[top](#catalog)
- 虽然可以通过logger设置日志级别来控制日志的输出，但是有时候需要对日志的目的地进行更精细的控制
- 日志过滤的配置方法
    ```xml
    <appender name="..." class="....">
        <!--设置日志过滤 只输出 debug～warn的日志-->
        <filter class="org.apache.log4j.varia.LevelRangeFilter">
            <!--日志级别的最小值-->
            <param name="levelMin" value="debug"/>
            <!--日志级别的最大值-->
            <param name="levelMax" value="warn"/>
            <param name="AcceptOnMatch" value="true"/>
        </filter>
    </appender>
    ```
  
## xml配置示例
[top](#catalog)
- 参考配置
    - [/java/mylearn/mylog4j/src/main/resources/log4j.xml](/java/mylearn/mylog4j/src/main/resources/log4j.xml)
- 配置内容
    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
    
    <log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">
        <!--创建目的地-->
        <appender name="log.file" class="org.apache.log4j.DailyRollingFileAppender">
            <param name="File" value="mylog4j/logs/ljs_xmllog_file.log"/>
            <!--已追加的方式写文件，默认为true。若使用false，则每次启动都后删除并新建日志文件-->
            <param name="Append" value="true"/>
            <param name="DatePattern" value=".yyy-MM-dd"/>
            <!--设置日志格式-->
            <layout class="org.apache.log4j.PatternLayout">
                <param name="ConversionPattern" value="%d{yyyy-MM-dd HH:mm:ss, SSS} %5p (%C:%M) - %m%n"/>
            </layout>
        </appender>
        <appender name="log.console" class="org.apache.log4j.ConsoleAppender">
            <!--设置日志格式-->
            <layout class="org.apache.log4j.PatternLayout">
                <param name="ConversionPattern" value="%d{yyyy-MM-dd HH:mm:ss, SSS} %5p (%C:%M) - %m%n"/>
            </layout>
            <!--设置日志过滤 只输出 debug～warn的日志-->
            <filter class="org.apache.log4j.varia.LevelRangeFilter">
                <!--日志级别的最小值-->
                <param name="levelMin" value="debug"/>
                <!--日志级别的最大值-->
                <param name="levelMax" value="warn"/>
                <param name="AcceptOnMatch" value="true"/>
            </filter>
        </appender>
    
        <!--配置日志控制器，并引用目的地-->
        <logger name="com.ljs.learn.mylog4j" additivity="true">
            <level value="debug"/>
            <appender-ref ref="log.file"/>
            <appender-ref ref="log.console"/>
        </logger>
        <root>
            <level value="error"/>
            <appender-ref ref="log.file"/>
            <appender-ref ref="log.console"/>
        </root>
    
    </log4j:configuration>
    ```