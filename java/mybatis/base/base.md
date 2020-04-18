- 参考
    - 官网中文： https://mybatis.org/mybatis-3/zh/index.html
    - https://www.bilibili.com/video/av69742084

<span id="catalog"></span>

### 目录
- [MyBatis概述](#MyBatis概述)
- [Maven配置](#Maven配置)
- [测试数据库](#测试数据库)
- [Mybatis的基本使用方式-xml方式](#Mybatis的基本使用方式-xml方式)
    - [基本使用步骤](#基本使用步骤)
    - [各配置、接口、实体类之间的关系图](#各配置、接口、实体类之间的关系图)
    - [Mybatis使用时的常见问题](#Mybatis使用时的常见问题)
    - [helloWorld示例](#helloWorld示例)
- [Mapper配置文件](#Mapper配置文件)
    - [Mapper配置文件的基本使用](#Mapper配置文件的基本使用)
    - [CRUD操作](#CRUD操作)
    - [Map对象作为接口参数](#Map对象作为接口参数)
    - [模糊查询的使用方法](#模糊查询的使用方法)
    - [解决属性名和字段不一致的问题](#解决属性名和字段不一致的问题)
    - [resultMap-结果映射](#resultMap-结果映射)
- [mybatis-config配置分析](#mybatis-config配置分析)
    - [配置内容的顺序](#配置内容的顺序)
    - [properties-属性](#properties-属性)
    - [settings-设置](#settings-设置)
    - [environments-环境配置](#environments-环境配置)
    - [typeAliases-类型别名](#typeAliases-类型别名)
        - [类型别名的配置方法](#类型别名的配置方法)
        - [mybatis中java常见类型的内建类型别名](#mybatis中java常见类型的内建类型别名)
    - [生命周期和作用域](#生命周期和作用域)
    - [日志工厂](#日志工厂)
- [分页](#分页)
    - [通过sql做分页](#通过sql做分页)
    - [RowBounds分页](#RowBounds分页)
- [使用注解开发mapper](#使用注解开发mapper)
- [Mybatis执行流程分析](#Mybatis执行流程分析)
- [多对一与一对多示例使用的数据库环境](#多对一与一对多示例使用的数据库环境)
- [多对一关系处理](#多对一关系处理)
    - [创建多对一关系的表](#创建多对一关系的表)
    - [按照查询进行嵌套处理多对一关系](#按照查询进行嵌套处理多对一关系)
    - [按照结果进行嵌套处理多对一关系](#按照结果进行嵌套处理多对一关系)
- [一对多关系处理](#一对多关系处理)
    - [创建一对多关系](#创建一对多关系)
    - [按照查询嵌套处理一对多关系](#按照查询嵌套处理一对多关系)
    - [按照结果嵌套处理一对多关系](#按照结果嵌套处理一对多关系)
- [动态sql](#动态sql)
    - [动态sql概述](#动态sql概述)
    - [创建动态sql使用的数据库环境](#创建动态sql使用的数据库环境)
    - [动态sql语句](#动态sql语句)
        - [所有动态sql语句的配置及测试内容](#所有动态sql语句的配置及测试内容)
        - [if语句](#if语句)
        - [where语句](#where语句)
        - [choose_when_otherwise语句](#choose_when_otherwise语句)
        - [set语句](#set语句)
        - [trim语句](#trim语句)
        - [foreach语句](#foreach语句)
- [缓存](#缓存)
    - [缓存简介](#缓存简介)
    - [mybayis缓存](#mybayis缓存)
    - [测试缓存的步骤](#测试缓存的步骤)
    - [mybayis一级缓存](#mybayis一级缓存)
    - [mybayis二级缓存](#mybayis二级缓存)
    - [mybatis缓存原理](#mybatis缓存原理)
    - [自定义二级缓存](#自定义二级缓存)
- [](#)
- [](#)
- [](#)


# MyBatis概述
[top](#catalog)
- 什么是Mybatis
    - 一款**持久层**框架
    - 支持自定义SQL、存储过程以及高级映射
    - 使用Mybatis，就不用写JDBC代码来设置参数和获取结果集
    - 可以通过XML或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象为数据库中的记录
   
- 为什么需要Mybatis？
    - 简化JDBC代码
    - sql与代码分离
    - 提供映射标签，支持对象与数据库的orm字段关系映射
    - 提供对象关系映射标签，支持对象关系组件维护
    - 提供xml标签，支持编写动态sql
    
# Maven配置
[top](#catalog)
```xml
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.4</version>
</dependency>
```

# 测试数据库
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
            `pwd` varchar(30) default null
        )default charset=utf8
        
        -- 添加数据
        insert into user 
        values
        (1, "aaa", "aaapwd"),
        (2, "bbb", "bbbpwd"),
        (3, "ccc", "cccpwd"),
        (4, "ddd", "dddpwd"),
        (5, "eee", "eeepwd"),
        (6, "fff", "fffpwd")
        ```
    - 实体类
        - 参考代码
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/common/bean/User.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/common/bean/User.java)
        - 代码内容
            ```java
            public class User {
                private int id;
                private String name;
                private String pwd;
                
                //...
                //getter setter
            }
            ```
          
# Mybatis的基本使用方式-xml方式
## 基本使用步骤
[top](#catalog)
- 参考：https://mybatis.org/mybatis-3/zh/getting-started.
- 三要素
    1. 接口
    2. mapper
    3. mybatis-config.xml
- 使用步骤
    1. 创建maven工程
    2. 导入maven依赖
        - mybatis依赖
        - mysql数据库依赖
    3. 创建xml配置文件
        - 配置文件名：`mybatis-config.xml`
        - 基本配置内容
            ```xml
            <?xml version="1.0" encoding="UTF-8" ?>
            <!DOCTYPE configuration
              PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
              "http://mybatis.org/dtd/mybatis-3-config.dtd">
            <configuration>
              <environments default="development">
                <environment id="development">
                  <transactionManager type="JDBC"/>
                  <dataSource type="POOLED">
                    <property name="driver" value="${driver}"/>
                    <property name="url" value="${url}"/>
                    <property name="username" value="${username}"/>
                    <property name="password" value="${password}"/>
                  </dataSource>
                </environment>
              </environments>
              <mappers>
                <mapper resource="org/mybatis/example/BlogMapper.xml"/>
              </mappers>
            </configuration>
            ```
    4. 创建接口以及`mapper.xml`文件：创建Dao/Mapper接口，并为接口创建`mapper.xml`文件，作为接口的实现类使用
        - 接口
            ```java
            public interface BlogMapper{
               Blog selectBLog();
            }
            ```
        - mapper.xml
            ```xml
            <?xml version="1.0" encoding="UTF-8" ?>
            <!DOCTYPE mapper
              PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
              "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
            <!--namespace绑定一个Dao/Mapper接口-->
            <mapper namespace="org.mybatis.example.BlogMapper">
               <!--id绑定接口中的方法名, resultType设置返回类型的全类名-->
               <select id="selectBlog" resultType="Blog">
                   select * from Blog where id = #{id}
               </select>
            </mapper>
            ```
    5. 添加`mapper.xml`的路径配置：在`mybatis-config.xml`中的`<mappers>`下添加注册mapper
        ```xml
        <mappers>
           <mapper resource="org/mybatis/example/BlogMapper.xml"/>
        </mappers>
        ```
       
    6. 创建`SlqSession`工厂：从xml中构建`SqlSessionFactory`
        ```java
        String resource = "org/mybatis/example/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        ```
    7. 获取`SqlSession`对象：从`SqlSessionFactory`中获取`SqlSession`，并通过`SqlSession`来执行sql
        ```java
        SqlSession session = sqlSessionFactory.openSession()
        ```
    8. 获取Mapper对象：从`SqlSession`中通过Dao/Mapper接口获取Mapper，并执行`mapper.xml`中配置的方法来执行sql操作
        ```java
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Blog blog = mapper.selectBlog(101);
        ```        
    9. 执行增删改操作之后，执行事务commit
        ```java
        session.commit();
        ```
    10. 关闭：操作结束后，关闭`SqlSession`对象，**或者使用带资源的try**
        ```java
        sqlSession.close();
        ```

- 数据库操作结束后必须关闭`SqlSession`对象

## 各配置、接口、实体类之间的关系图
[top](#catalog)
- ![xml_Relation](imgs/mapperXml/xml_Relation.png)


## Mybatis使用时的常见问题
[top](#catalog)
- `mybatis-config.xml`中设置的默认环境在所有有环境中无法找到
    - 如配置，默认使用development，但是所有的环境给配置中没有development
        ```xml
        <environments default="development">
            <environment id="ea"></environment>
            <environment id="eb"></environment>
            <environment id="ec"></environment>
        </environments>
        ```
    - 导致的异常：执行`sqlSessionFactory.openSession();`时，无法打开session
    - 异常内容
        ```
        org.apache.ibatis.exceptions.PersistenceException: 
        ### Error opening session.  
        Cause: java.lang.NullPointerException
        ```
    
- dao/mapper接口的`mapper.xml`没有在`mybatis-config.xml`中配置
    ```
    org.apache.ibatis.binding.BindingException: 
  Type interface com.ljs.learn.mybatis.firstSample.dao.UserDao 
  is not known to the MapperRegistry.
    ```
- 启动时，无法找到dao/mapper接口的`mapper.xml`
    - 异常信息
        ```
        org.apache.ibatis.builder.BuilderException: 
        Error parsing SQL Mapper Configuration. 
        Cause: java.io.IOException: 
        Could not find resource 
        com/ljs/learn/batis/dao/UserMapper.xml
        ```
    - 异常原因
        - 在maven中，约定大于配置，所以可能会产生配置文件无法别导出、无法生效的问题
    - 在`pom.xml`的`<build>`中配置`<resources>`，来防止资源导出失败
        ```xml
        <resources>
          <resource>
            <directory>src/main/resources</directory>
            <includes>
              <include>**/*.properties</include>
              <include>**/*.xml</include>
            </includes>
            <filtering>true</filtering>
          </resource>
          <resource>
            <directory>src/main/java</directory>
            <includes>
              <include>**/*.properties</include>
              <include>**/**/*.xml</include>
            </includes>
            <filtering>true</filtering>
          </resource>
        </resources>
        ```
    
- `mapper.xml`下的 select语句 中没有指定 `resultType` 或 `resultMap`，导致查询结果无法映射到实体类中
    ```
    org.apache.ibatis.executor.ExecutorException: A query was run and no Result Maps were found for the Mapped Statement '全类名.方法名'.  It's likely that neither a Result Type nor a Result Map was specified.
    ```

- 绑定接口错误
    - **必须在`mapper.xml`文件下的`namespace`属性中配置接口，否则启动后，无法绑定**
- 方法名异常
- 返回类型异常

- 开启**可读写**缓存时，实体类不是可序列化的
    ```
    org.apache.ibatis.cache.CacheException: Error serializing object.
    Cause: java.io.NotSerializableException: 全类名
    ```
   
## helloWorld示例 
[top](#catalog)
- 封装工具类，统一获取`SqlSession`对象
    - 参考代码
        - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/utils/MybatisUtils.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/utils/MybatisUtils.java)

    - 代码内容
        ```java
        public class MybatisUtils {
            private static SqlSessionFactory sqlSessionFactory;
        
            // 获取sqlSessionFactory对象
            static{
                InputStream inputStream = null;
                try {
                    String resource = "mybatis-config.xml";
                    inputStream = Resources.getResourceAsStream(resource);
                    sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        
            // 返回执行sql的对象
            public static SqlSession getSqlSession(){
                return sqlSessionFactory.openSession();
            }
        }
        ```
- 创建Dao接口及其对应的`mapper.xml`
    - 参考代码
        - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/firstSample/dao/UserDao.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/firstSample/dao/UserDao.java)
        - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/firstSample/dao/UserMapper.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/firstSample/dao/UserMapper.xml)

    - 接口
        ```java
        public interface UserDao {
            // 获取所有用户信息
            List<User> getUsers();
        }
        ```
    - `mapper.xml`
        ```xml
        <!--namespace绑定一个Dao/Mapper接口-->
        <mapper namespace="com.ljs.learn.mybatis.firstSample.dao.UserDao">
            <!--id绑定接口中的方法名, resultType设置返回类型-->
            <select id="getUsers" resultType="com.ljs.learn.mybatis.common.bean.User">
                select * from test01.user;
            </select>
        </mapper>
        ```
    
- `mybatis-config.xml`配置文件
    - 文件路径
        - [/java/mylearn/mybatis/src/main/resources/mybatis-config.xml](/java/mylearn/mybatis/src/main/resources/mybatis-config.xml)
    - 配置内容
        ```xml
        <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE configuration
                PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-config.dtd">
        <!--核心配置内容-->
        <configuration>
            <!--配置多套环境-->
            <environments default="development">
                <environment id="development">
                    <!--事务管理，使用JDBC-->
                    <transactionManager type="JDBC"/>
                    <dataSource type="POOLED">
                        <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                        <!--配置数据库连接-->
                        <property name="url" value="jdbc:mysql://127.0.0.1:3307?serverTimezone=UTC&amp;characterEncoding=utf8&amp;useUnicode=true&amp;useSSL=false"/>
                        <property name="username" value="root"/>
                        <property name="password" value="1234"/>
                    </dataSource>
                </environment>
            </environments>
            <mappers>
                <!--注册mapper，注册mapper对象的xml路径-->
                <mapper resource="com/ljs/learn/mybatis/firstSample/dao/UserMapper.xml"/>
            </mappers>
        </configuration>
        ```
- 测试类
    - 参考代码
        - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/firstSample/dao/UserDaoTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/firstSample/dao/UserDaoTest.java)

    - 测试内容
        ```java
        @Test
        public void test01(){
            // 获取资源
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            // 获取mapper
            UserDao mapper = sqlSession.getMapper(UserDao.class);
            // 执行数据库操作
            List<User> users = mapper.getUsers();
            for (User user : users) {
                System.out.println(user);
            }
            
            //关闭资源
            sqlSession.close();
        }
        ```

# Mapper配置文件
## Mapper配置文件的基本使用
[top](#catalog)
- namespace
    - `namespace`中的全类名要和Dao/Mapper接口的全类名一致
- 标签中的通用属性
    - id，对应Dao/Mapper接口中的方法名
    - resultType，接口方法的返回值类型，即bean
        - **增删改方法**可以有int型返回值，来表示执行是否成功，但是**在配置中没有resultType属性**
    - paramterType，方法的参数类型
        - 如果是自定义类型，需要使用全类名
        - 基本数据类型
        - Map类型使用`map`
- 标签内容：sql
- sql中的数据库名
    - 如果`mybatis-config.xml`的
- sql中的占位符
    - 在sql中，使用`#{参数名}`来表示占位符，类似于JDBC中的`?`
    - 如果参数是类，可以直接通过`#{属性名}`来从类对象中获取
    - 如果参数是Map，可以通过`#{key}`来从map中获取
    
## CRUD操作
[top](#catalog)
- CRUD操作的关键字与其标签要匹配，否则会产生异常
    - `<select>`
    - `<insert>`
    - `<update>`
    - `<delete>`
    
- 增删改处理，需要提交事务：`sqlSession.commit();`

- 示例
    - Dao接口及其`mapper.xml`
        - 参考代码
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/crud/UserDao.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/crud/UserDao.java)
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/crud/UserMapper.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/crud/UserMapper.xml)
        - Dao接口
            ```java
            public interface UserDao {
                // 获取所有用户信息
                List<User> getUsers();
            
                // 根据ID查询用户
                User getUserById(int id);
            
                // 插入用户
                int insertUser(User user);
            
                // 更新用户
                int updateUser(User user);
            
                // 删除用户
                int deleteUserById(int id);
            }
            ```
        - `mapper.xml`
            ```xml
            <?xml version="1.0" encoding="UTF-8" ?>
            <!DOCTYPE mapper
                    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
                    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
            
            <!--namespace绑定一个Dao/Mapper接口-->
            <mapper namespace="com.ljs.learn.mybatis.crud.dao.UserDao">
                <select id="getUsers" resultType="com.ljs.learn.mybatis.common.bean.User">
                    select * from test01.user;
                </select>
            
                <!-- #{参数名} 在sql中类似与JDBC中的 ?，表示占位符 -->
                <select id="getUserById" parameterType="int" resultType="com.ljs.learn.mybatis.common.bean.User">
                    select * from test01.user where id = #{id};
                </select>
            
                <insert id="insertUser" parameterType="com.ljs.learn.mybatis.common.bean.User">
                    insert into test01.user (id, name, pwd) values (#{id}, #{name}, #{pwd});
                </insert>
            
                <update id="updateUser" parameterType="com.ljs.learn.mybatis.common.bean.User">
                    update test01.user set name=#{name} where id = #{id}
                </update>
            
                <delete id="deleteUserById" parameterType="int">
                    delete from test01.user where id = #{id}
                </delete>
            </mapper>
            ```
    - `mybatis-config.xml`中注册mapper.xml的
        ```xml
        <mappers>
            <mapper resource="com/ljs/learn/mybatis/crud/dao/UserMapper.xml"/>
        </mappers>
        ```
    - 测试类
        - 参考代码
            - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/crud/CRUDTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/crud/CRUDTest.java)
        - 测试内容
            ```java
            @Test
            public void testGetUserById(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                UserDao mapper = sqlSession.getMapper(UserDao.class);
                User user = mapper.getUserById(2);
                System.out.println(user);
            }
        
            // 添加数据，不commit
            @Test
            public void testInsertUser01(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                UserDao mapper = sqlSession.getMapper(UserDao.class);
                int result = mapper.insertUser(new User(7, "kkk", "kkkkpw"));
        
                System.out.println("insertUser = " + result);
                sqlSession.close();
            }
        
            // 添加数据，然后commit
            @Test
            public void testInsertUser02(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                UserDao mapper = sqlSession.getMapper(UserDao.class);
                int result = mapper.insertUser(new User(7, "kkk", "kkkk"));
        
                if (result <= 0){
                    System.out.println("insert error");
                } else {
                    // 提交事务
                    sqlSession.commit();
                }
                sqlSession.close();
            }
        
            @Test
            public void testUpdateUser(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                UserDao mapper = sqlSession.getMapper(UserDao.class);
                int result = mapper.updateUser(new User(7, "newK", "kkkk"));
                if (result <= 0){
                    System.out.println("update error");
                }else {
                    sqlSession.commit();
                }
                sqlSession.close();
            }
        
            @Test
            public void testDeleteUser(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                UserDao mapper = sqlSession.getMapper(UserDao.class);
                int result = mapper.deleteUserById(4);
                if (result <= 0){
                    System.out.println("update error");
                }else {
                    sqlSession.commit();
                }
                sqlSession.close();
            }
            ```
          
## Map对象作为接口参数
[top](#catalog)
- 当实体类或表的字段过多，可以使用map对象
- 使用map对象作为接口参数时，可以自由控制key的名字
- 配置文件中，通过`#{key}`来从map对象中根据key获取参数值
- 示例
    - Dao接口及其`mapper.xml`
        - 参考代码
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/map/UserDao.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/map/UserDao.java)
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/map/UserMapper.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/map/UserMapper.xml)
        - Dao接口
            ```java
            public interface UserDao {
                // 通过map对象来执行插入
                int insertUser(Map<String, Object> map);
            }
            ```
        - `mapper.xml`
            ```xml
            <?xml version="1.0" encoding="UTF-8" ?>
            <!DOCTYPE mapper
                    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
                    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
            
            <!--namespace绑定一个Dao/Mapper接口-->
            <mapper namespace="com.ljs.learn.mybatis.mapperXml.map.UserDao">
                <insert id="insertUser" parameterType="map" >
                    insert into user(id, name, pwd) values(#{userid}, #{username}, #{userpwd})
                </insert>
            </mapper>
            ```
    - 测试类
        - 参考代码
            - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/map/MapParamTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/map/MapParamTest.java)
        - 测试内容
            ```java
            @Test
            public void testInsertUser(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                UserDao mapper = sqlSession.getMapper(UserDao.class);
        
                // 创建Map对象
                HashMap<String, Object> map = new HashMap<>();
                map.put("userid", 8);
                map.put("username", "tttt");
                map.put("userpwd", "ttttpwd");
        
                int result = mapper.insertUser(map);
        
                if (result <= 0){
                    System.out.println("insert error");
                }else {
                    sqlSession.commit();
                }
                sqlSession.close();
            }
            ```
          
## 模糊查询的使用方法
[top](#catalog)
- 两种设置模糊查询的方法
    1. 在`mapper.xml`中，将通配符写在sql中
    2. 在调用方法传递参数前，将通配符添加到参数中
- 示例
    - Dao接口及其`mapper.xml`
        - 参考代码
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/sqllike/UserDao.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/sqllike/UserDao.java)
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/sqllike/UserMapper.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/sqllike/UserMapper.xml)  
        - Dao接口
            ```java
            public interface UserDao {
                // 模糊查询，将通配符写在配置文件中
                List<User> getUser01(String name);
            
                // 模糊查询，需要将通配符写在参数中
                List<User> getUser02(String name);
            }
            ```
        - `mapper.xml`
            ```xml
            <?xml version="1.0" encoding="UTF-8" ?>
            <!DOCTYPE mapper
                    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
                    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
            
            <mapper namespace="com.ljs.learn.mybatis.mapperXml.sqllike.UserDao">
                <!--模糊查询, 在sql中使用通配符-->
                <select id="getUser01" parameterType="String" resultType="com.ljs.learn.mybatis.common.bean.User">
                    select * from user where name like "%"#{name}"%"
                </select>
            
                <!--模糊查询, 需要将通配符添加到参数中-->
                <select id="getUser02" parameterType="String" resultType="com.ljs.learn.mybatis.common.bean.User">
                    select * from user where name like #{name}
                </select>
            </mapper>
            ```
    - 测试类
        - 参考代码
            - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/sqllike/LikeTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/sqllike/LikeTest.java)
        - 测试内容
            ```java
            // 模糊查询，将通配符写在配置文件中
            @Test
            public void test01(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                UserDao mapper = sqlSession.getMapper(UserDao.class);
                List<User> userList = mapper.getUser01("a");
        
                for (User user : userList) {
                    System.out.println(user);
                }
        
                sqlSession.close();
            }
        
            // 模糊查询，需要将通配符写在参数中
            @Test
            public void test02(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                UserDao mapper = sqlSession.getMapper(UserDao.class);
                List<User> userList = mapper.getUser02("%a%");
                for (User user : userList) {
                    System.out.println(user);
                }
        
                sqlSession.close();
            }
            ```

## 解决属性名和字段不一致的问题
[top](#catalog)
- 开发时会可能出现的问题：实体类的属性名和sql中的字段名不一致，导致实体类对象中的某些属性无法被赋值
    - 示例
        - 实体类:[/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean/User02.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean/User02.java)
            ```java
            public class User {
                private int id;
                private String name;
                private String password;
                ...
            }
            ```
        - sql:[/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/resultmap/problem/UserMapper10.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/resultmap/problem/UserMapper10.xml)
            - 返回：id、 name、 pwd，与实体类的属性不一致
            ```sql
            select * from user where id = #{id} 
            ``` 
        - 测试类：[/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/resultmap/problem/ProblemTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/resultmap/problem/ProblemTest.java)
            ```java
            @Test
            public void problemTest(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                User02 user = mapper.getUserById(1);
                System.out.println(user);

                sqlSession.close();
            }
            ```
        - 测试结果
            - sql中没有password字段，所以该字段是`null`
            ```
            User02{id=1, name='aaa', password='null'}
            ```
          
- 解决方法1：在sql中，给字段起别名
    - 示例
        - sql ：[/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/resultmap/newSql/UserMapper11.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/resultmap/newSql/UserMapper11.xml)
            - 通过在sql中设置别名，来使属性名与字段一直
            ```sql
            select id, name, pwd as password from user where id = #{id}
            ```
        - 测试类：[/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/resultmap/newSql/NewSqlTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/resultmap/newSql/NewSqlTest.java)
            ```java
            @Test
            public void testNewSql(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                UserMapper11 mapper = sqlSession.getMapper(UserMapper11.class);
                User02 user = mapper.getUserById(1);
                System.out.println(user);

                sqlSession.close();
            }
            ```
        - 测试结果
            - 实体类对象的`password`字段可以被正常赋值
            ```
            User02{id=1, name='aaa', password='aaapwd'}
            ```

- 解决方式2：使用resultMap
    - [resultMap-结果映射](#resultMap-结果映射)

- 解决方法3：驼峰命名自动转换
    - 该方法只适用于实体类字段和数据库字段命名规则不一致的情况
        - 如下，表中的字段为 `create_time`，Java实体类中的属性名为 `createTime`，只是命名规则不同
            ```sql
            create table xxx (
                create_time datetime,
            );
            ```
            ```java
            class xxx{
                private Date createTime;
            }
            ```
    - 在 mybatis-config.xml 中添加配置
        ```xml
        <settings>
            <setting name="mapUnderscoreToCamelCase" value="true"/>
        </settings>
        ```
    
## resultMap-结果映射
[top](#catalog)
- ResultMap的设计思想
    - 简单的sql语句不需要配置显示的结果映射
    - 复杂的sql语句需要描述语句之间的关系
- resultMap 在 mapper.xml 中的配置方式
    - 可以为每一对属性与字段名进行配置
    - <label style="color: red">如果实体类与sql中的字段只是有部分不一样，可以只配置这些不同的部分，相同的部分可以省略</label>
    ```xml
    <resultMap id="映射id" type="实体类的全类名 / 别名">
        <result column="sql中的列名" property="实体类中的属性名"/>
    </resultMap>
    ```
- resultMap 在 mapper.xml 中的使用方法
    - 不需要结果映射的时候使用 `resultType`。需要使用结果映射的时候，使用 `resultMap` 替换 `resultMap`
        
        ```xml
        <select id="方法名" parameterType="方法参数" resultMap="映射id">
            ...
        </select>
        ```
      
- 示例
    1. 简单的sql
        - 实体类
            - 参考代码
                - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean/User02.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean/User02.java)
            - 参考内容
                ```java
                public class User02 {
                    private int id;
                    private String name;
                    private String password;
                    // getter、setter
                }
                ```
        - mapper.xml
            - 参考配置
                - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/resultmap/simpleMap/UserMapper12.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/resultmap/simpleMap/UserMapper12.xml)
            - 配置内容
                ```xml
                <?xml version="1.0" encoding="UTF-8" ?>
                <!DOCTYPE mapper
                        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
                        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
                
                <mapper namespace="com.ljs.learn.mybatis.mapperXml.resultmap.simpleMap.UserMapper12">
                    <!--结果集映射-->
                    <resultMap id="MyUser02Map" type="com.ljs.learn.mybatis.common.bean.User02">
                        <!--省略实体类和sql中相同的部分-->
                        <!--<result column="id" property="id"/>-->
                        <!--<result column="name" property="name"/>-->
                        <result column="pwd" property="password"/>
                    </resultMap>
                    
                    <select id="getUserById" parameterType="int" resultMap="MyUser02Map">
                        select * from user where id = #{id}
                    </select>
                </mapper>
                ```
        - 测试类
            - 参考代码
                - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/resultmap/simpleMap/SimpleMapTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/resultmap/simpleMap/SimpleMapTest.java)
            - 测试内容
                ```java
                @Test
                public void testResultMap(){
                    SqlSession sqlSession = MybatisUtils.getSqlSession();
                    UserMapper12 mapper = sqlSession.getMapper(UserMapper12.class);
                    User02 user = mapper.getUserById(1);
                    System.out.println(user);

                    sqlSession.close();
                }
                ```
            - 测试结果
                ```
                User02{id=1, name='aaa', password='aaapwd'}
                ```

## sql片段
[top](#catalog)
- **通过sql片段可以实现代码复用**
- 语法
    - 创建sql片段，片段中可以包含参数
        ```xml
        <sql id="sql片段id">
            ${参数名}
            ...
        </sql>
        ```
    - 引用sql片段
        ```xml
        <select id="select" resultType="map">
            ...
            <!-- 引用sql片段 -->
            <include refid="sql片段id">
                <property name="sql片段中的参数名" value="参数值"/>
            </include>

            ...
        </select>
        ```
- 创建sql片段的注意事项
    1. 尽量只为单表创建sql片段
    2. 不要包含 [where语句](#where语句)、[set语句](#set语句)
        - 添加后，sql片段就完全被限定为where条件，或set语句的内容，降低了复用功能

- 示例
    - mapper
        - 参考代码
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/sqlCode/UserMapper15.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/sqlCode/UserMapper15.xml)
        - 代码内容
            ```xml
            <mapper namespace="com.ljs.learn.mybatis.mapperXml.sqlCode.UserMapper15">
                <sql id="user_item">
                    ${table}.id, ${table}.name, ${table}.pwd
                </sql>

                <select id="getUserById" parameterType="int" resultType="MyUser">
                    select
                        <include refid="user_item">
                            <property name="table" value="u"/>
                        </include>
                    from user u where u.id = #{id}
                </select>
            </mapper>
            ```

    - 测试类
        - 参考代码
            - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/sqlCode/UserMapper15Test.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/sqlCode/UserMapper15Test.java)
        - 测试内容
            ```java
            @Test
            public void test01(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                UserMapper15 mapper = sqlSession.getMapper(UserMapper15.class);
                User user = mapper.getUserById(1);
                System.out.println(user);
                sqlSession.close();
            }
            ```

# mybatis-config配置分析
## 配置内容的顺序
[top](#catalog)
- `mybatis-config.xml`文件的配置内容必须严格遵守下面的顺序，否则会产生异常
    1. properties（属性）
    2. settings（设置）
    3. typeAliases（类型别名）
    4. typeHandlers（类型处理器）
    5. objectFactory（对象工厂）
    6. objectWapperFactory
    7. reflectorFactory
    8. plugins（插件）
    9. environments（环境配置）
    10. databaseIdProvider（数据库厂商标识）
    11. mappers（映射器）


## properties-属性
[top](#catalog)
- 配置示例
    ```xml
    <!--统一管理通用属性值-->
    <!--引入外部java属性文件-->
    <properties resource="org/mybatis/example/config.properties">
      <!--替换文件中的某些属性-->
      <property name="username" value="dev_user"/>
      <property name="password" value="F2Fa3!33TYyg"/>
    </properties>
    ```
- `<properties>`统一管理`mybatis-config.xml`中使用的通用属性值
- 在`resource`中指定文件路径来引入外部的Java属性文件
    - 可以通过`<property>`来添加一些**额外的属性**
    - 如果外部文件和`<property>`中的属性发生冲突，**优先使用外部文件中的属性**
    - 常用的文件，如：`db.properties`
- 在后面的配置中通过`${property_name}`的方式来使用配置，如：
    ```xml
    <dataSource type="POOLED">
      <property name="driver" value="${driver}"/>
      <property name="url" value="${url}"/>
      <property name="username" value="${username}"/>
      <property name="password" value="${password}"/>
    </dataSource>
    ```

## settings-设置
[top](#catalog)
- `settings` 是MyBatis中极为重要的调整设置，它们会改变 MyBatis 的运行时行为

- settings的可配置内容

    |设置名|描述|有效值|默认值|参考|
    |-|-|-|-|-|
    |logImpl|设置日志实现|<ul><li>SLF4J</li><li>LOG4J</li><li>LOG4J2</li><li>JDK_LOGGING</li><li>COMMONS_LOGGING</li><li>STDOUT_LOGGING</li><li>NO_LOGGING</li></ul>|-|[日志工厂](#日志工厂)|
    |mapUnderscoreToCamelCase|是否开启驼峰命名自动映射<br>开启后，可以从列名 A_COLUMN 映射属性名 aColumn|true,false|false||
    |cacheEnabled|开启所有以配置的mybatis二级缓存|true,false|true|[mybayis二级缓存](#mybayis二级缓存)|
    |||||

## environments-环境配置
[top](#catalog)
- 配置示例
    ```xml
    <environments default="development">
      <environment id="development">
        <transactionManager type="JDBC">
          <property name="..." value="..."/>
        </transactionManager>
        <dataSource type="POOLED">
          <property name="driver" value="${driver}"/>
          <property name="url" value="${url}"/>
          <property name="username" value="${username}"/>
          <property name="password" value="${password}"/>
        </dataSource>
      </environment>
    </environments>
    ```
- `<environments>`：多套测试环境
    - mybatis可以配置多种环境，以适应开发、测试的不同需求。但是**每个`SqlSessionFactory`实现只能使用一种环境**
    - 存在多套环境时，通过`<environments default="环境ID">`
- `<transactionManager>`：事务管理器
    - mybatis有两种事务管理器：JDBC、MANAGED
        - JDBC：这个配置直接使用了 JDBC 的提交和回滚设施，它依赖从数据源获得的连接来管理事务作用域。
        - MANAGED：这个配置几乎没做什么。它从不提交或回滚一个连接，而是让容器来管理事务的整个生命周期（比如 J2EE 应用服务器的上下文）
            - 默认情况下它会关闭连接
            - 如果不希望连接被关闭，因此需要将 closeConnection 属性设置为 false 来阻止默认的关闭行为
                ```xml
                <property name="closeConnection" value="false"/>
                ```
    - 默认值:JDBC
    - 如果使用的是：Spring+MyBatis，没有必要配置事务管理器，Spring模块会使用自带的管理器来覆盖该配置
- `<dataSource>`：数据源
    - 3中内建数据源
        1. UNPOOLED：不是用连接池，每次请求都会重新打开关闭连接
        2. POOLED：使用连接池
        3. JNDI：为了能在如 EJB 或应用服务器这类容器中使用，容器可以集中或在外部配置数据源，然后放置一个 JNDI 上下文的数据源引用
    - 默认值：POOLED
    
  
## typeAliases-类型别名
### 类型别名的配置方法
[top](#catalog)
- 类型别名是为`java类型`设置一个别名
- 通过类型别名，使配置更加清晰，减少全类名造成的冗余
- 在`mybatis-config.xml`中配置别名后，可以在`mapper.xml`中直接使用别名
- 两种设置别名的方式
    1. 单独为每一个类设置一个别名
    2. 扫描包
        - 适用于实体非常多的情况
    
- 设置方式1：单独为每一个类设置一个别名
    1. `mybatis-config.xml`，添加配置
        ```xml
        <typeAliases>
            <typeAlias type="全类名" alias="别名"/>
        </typeAliases>
        ```
    2. 在`mapper.xml`中使用别名
        ```xml
        <mapper namespace="...">
            <select id="..." parameterType="..." resultType="别名">
                ...
            </select>
        </mapper>
        ```
- 设置方式2：扫描包
    1. `mybatis-config.xml`，添加配置
        ```xml
        <typeAliases>
          <package name="包名"/>
        </typeAliases>
        ```
    2. 设置别名
        - 使用注解设置别名
            ```java
            @Alias("author")
            public class Author {
                ...
            }
            ```
        - **如果没有使用注解，则默认使用类名，第一个字母小写**
    3. 在`mapper.xml`中使用别名
        ```xml
        <mapper namespace="...">
          <select id="..." parameterType="..." resultType="注解中的别名/类名(第一个字母小写)">
              ...
          </select>
        </mapper>
        ```

- 示例
    - `mybatis-config.xml`的配置
        1. 单独为每一个类设置一个别名
            - 参考配置
                - [/java/mylearn/mybatis/src/main/resources/mybatis-config_BK02.xml](/java/mylearn/mybatis/src/main/resources/mybatis-config_BK02.xml)
            - 配置内容
                ```xml
                <!--为实体类添加别名-->
                <typeAliases>
                    <typeAlias type="com.ljs.learn.mybatis.common.bean.User" alias="MyUser"/>
                </typeAliases>
                ```
        2. 扫描包
            - 参考配置
                - [/java/mylearn/mybatis/src/main/resources/mybatis-config.xml](/java/mylearn/mybatis/src/main/resources/mybatis-config.xml)
            - 配置内容
                ```xml
                <!--扫描包来添加别名-->
                <typeAliases>
                    <package name="com.ljs.learn.mybatis.common.bean"/>
                </typeAliases>
                ```
    - 在`mapper.xml`中使用别名
        - 参考配置
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/configXml/typeAlias/UserMapper.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/configXml/typeAlias/UserMapper.xml)
        - 配置内容
            ```xml
            <?xml version="1.0" encoding="UTF-8" ?>
            <!DOCTYPE mapper
                    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
                    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
            
            <mapper namespace="com.ljs.learn.mybatis.configXml.typeAlias.UserDao">
                <select id="getUserById" parameterType="int" resultType="MyUser">
                    select * from user where id = #{id}
                </select>
            </mapper>
            ```
    - 实体类的配置
        - 参考代码
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean/User.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean/User.java)
        - 代码内容
            ```java
            // 通过注解配置扫描包时的别名
            @Alias("MyUser")
            public class User {
                private int id;
                private String name;
                private String pwd;
                ...
            }
            ```
    - 测试类
        - 参考代码
            - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/configXml/typeAlias/AliasTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/configXml/typeAlias/AliasTest.java)
        - 测试内容
            ```java
            @Test
            public void testMyUser(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                UserDao mapper = sqlSession.getMapper(UserDao.class);
                User user = mapper.getUserById(2);
                System.out.println(user);

                sqlSession.close();
            }
            ```

### mybatis中java常见类型的内建类型别名
[top](#catalog)
- 基本规则
    - 基本类型的别名 = `_`基本类型名
    - 包装类型别名 = 基本类型名
    - 其他

- 内建别名

    |别名|映射的类型|
    |-|-|
    |_byte|byte|
    |_long|long|
    |_short|short|
    |_int|int|
    |_integer|int|
    |_double|double|
    |_float|float|
    |_boolean|boolean|
    |string|String|
    |byte|Byte|
    |long|Long|
    |short|Short|
    |int|Integer|
    |integer|Integer|
    |double|Double|
    |float|Float|
    |boolean|Boolean|
    |date|Date|
    |decimal|BigDecimal|
    |bigdecimal|BigDecimal|
    |object|Object|
    |map|Map|
    |hashmap|HashMap|
    |list|List|
    |arraylist|ArrayList|
    |collection|Collection|
    |iterator|Iterator|

## mappers-映射器
[top](#catalog)
- 用于注册 mapper.xml 配置文件
- 配置方式1：使用相对于类路径的资源引用
    - mapper.xml与对应的mapper接口，可以分别放在不同的目录下，通过`resource`来绑定，但不建议这样做
    - 示例
        ```xml
        <mappers>
          <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
          <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
        </mappers>
        ```
- 配置方式2：使用mapper接口的全类名
    - 需要注意
        1. mapper.xml与对应的mapper接口，两者的名字必须相同
        2. mapper.xml与对应的mapper接口，两者必须在同一个包下
    - 示例
        ```xml
        <mappers>
          <mapper class="org.mybatis.builder.AuthorMapper"/>
          <mapper class="org.mybatis.builder.BlogMapper"/>
        </mappers>
        ```

- 配置方式3：扫描包
    - 需要注意
        1. mapper.xml与对应的mapper接口，两者的名字必须相同
        2. mapper.xml与对应的mapper接口，两者必须在同一个包下
    ```xml
    <mappers>
      <package name="包名"/>
    </mappers>
    ```
  
- 示例
    - mybatis-config.xml
        - 参考配置
            - [/java/mylearn/mybatis/src/main/resources/mybatis-config.xml](/java/mylearn/mybatis/src/main/resources/mybatis-config.xml)
        - 配置内容
            ```xml
            <mappers>
                <!--通过类名来配置mapper-->
                <mapper class="com.ljs.learn.mybatis.configXml.mappers.UserMapper"/>
            </mappers>
            ```
    - mapper.xml与mapper接口
        - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/configXml/mappers/UserMapper.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/configXml/mappers/UserMapper.java)
        - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/configXml/mappers/UserMapper.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/configXml/mappers/UserMapper.xml)
    - 测试类
        - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/configXml/mappers/MappersTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/configXml/mappers/MappersTest.java)

## 生命周期和作用域
[top](#catalog)
- 生命周期和作用域非常重要，如果使用错误会导致非常严重的<label style="color:red">并发问题</label>
- SqlSessionFactoryBuilder
    - 只是用于创建 SqlSessionFactory，创建之后就不再需要了
    - 作用域：局部变量
- SqlSessionFactory
    - 相当于：数据库连接池
    - SqlSessionFactory被创建后，在运行期间，应该一直存在。不应该丢弃该对象，或重新创建另一个实例
    - 应该使用单例模式
    - 作用域：整个应用的作用域
- SqlSession
    - 相当于：从连接池获取了一个连接对象
    - SqlSession 不是线程安全的，所以不能被共享
    - 使用之后应该立刻关闭，防止资源占用
    - 作用域：方法内部
    
## 日志工厂
[top](#catalog)
- 如果数据库操作出现了异常，需要检查执行的sql来判断问题具体的问题，这种情况就需要使用日志在执行过程中间相关的执行信息输出到日志中
- 添加日志配置
    ```xml
    <settings>
        <setting name="logImpl" value="日志实现"/>
    </settings>
    ```
- 可用的日志实现
    - SLF4J
    - <label style="color:red">LOG4J</label>
    - LOG4J2
    - JDK_LOGGING
        - JDK自带的日志实现
    - COMMONS_LOGGING 
    - <label style="color:red">STDOUT_LOGGING</label>
        - 控制台输出
    - NO_LOGGING
    
- 使用 `STDOUT_LOGGING` 日志实现
    - 直接添加配置
        ```xml
        <settings>
            <setting name="logImpl" value="STDOUT_LOGGING"/>
        </settings>
        ```
- 使用 `LOG4J` 日志实现
    1. 添加 mybatis 配置
        ```xml
        <settings>
            <setting name="logImpl" value="LOG4J"/>
        </settings>
        ```
    2. 导入maven依赖
        ```xml
        <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>.....</version>
        </dependency>
        ```
    3. 在 resources 目录下添加 log4j的配置文件
    4. 直接执行程序，不需要获取`logger`对象来输出日志，mybatis底层会自动处理

# 分页
## 通过sql做分页
[top](#catalog)
- mysql的分页sql：`select * from tabel limit offset, limit`
- 将 offset、limit 作为查询参数，来完成分页的控制
- 示例
    - 接口
        - 参考代码
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/paging/UserMapper13.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/paging/UserMapper13.java)
        - 代码内容
            ```java
            public interface UserDao {
                // 通过sql完成分页
                List<User02> getUserByLimit(Map<String, Object> map);
                ...
            }
            ```
    - mapper配置
        - 参考代码
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/paging/UserMapper13.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/paging/UserMapper13.xml)
        - 配置内容
            ```xml
            <select id="getUserByLimit" parameterType="map" resultMap="user02Map">
                select * from user limit #{offset}, #{size};
            </select>
            ```
    - 测试类
        - 参考代码
            - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/paging/PagingTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/paging/PagingTest.java)
        - 代码内容
            ```java
            @Test
            public void testSqlLimit(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                UserMapper13 mapper = sqlSession.getMapper(UserMapper13.class);
                Map<String, Object> param= new HashMap<>();
                param.put("offset", 2);
                param.put("size", 3);
                List<User02> users = mapper.getUserByLimit(param);
                for (User02 user : users) {
                    System.out.println(user);
                }
            }
            ```
          
## RowBounds分页
[top](#catalog)
- 这是一种mybatis早期的功能，**现在不推荐使用**
- 使用方法
    1. 按照普通的检索sql来开发接口和mapper
    2. 使用时通过分页起始位置和分页大小来创建 `RowBounds` 对象
    3. 调用方法：`sqlSession.selectList("全类名.方法名", 参数, RowBounds对象)` 来进行分页查询

- 示例
    - 接口
        - 参考代码
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/paging/UserMapper13.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/paging/UserMapper13.java)
        - 代码内容
            ```java
            public interface UserMapper13 {
                // 使用RowsBounds来做分页
                List<User02> getUserByRowBounds();
            }
            ```
    - mapper配置
        - 参考代码
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/paging/UserMapper13.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/mapperXml/paging/UserMapper13.xml)
        - 配置内容
            ```xml
            <select id="getUserByRowBounds" resultMap="user02Map">
                select * from user;
            </select>
            ```
    - 测试类
        - 参考代码
            - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/paging/PagingTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/mapperXml/paging/PagingTest.java)
        - 测试内容
            ```java
            @Test
            public void testRowBounds(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                RowBounds rowBounds = new RowBounds(2, 3);
                List<User02> users = sqlSession.selectList("com.ljs.learn.mybatis.mapperXml.paging.UserMapper13.getUserByRowBounds", null, rowBounds);
                for (User02 user : users) {
                    System.out.println(user);
                }
            }
            ```

# 使用注解开发mapper
[top](#catalog)
- 使用注解只能开发简单的mapper，对于**复杂的mapper还是需要使用配置文件**
- 开发步骤
    1. 开发接口
    2. 在接口方法上添加注解，并在注解中添加sql
- 注解
    - CRUD
        - `@Select`、`@Insert`、`@Update`、`@Delete`
    - `@Param("属性名")`
        - 方法存在多个参数时，所有的参数前面必须加上
        - 引用对象不需要添加该注解
        - 在SQL中引用的是 `@Param("属性名")` 中的属性名
        - 示例
            ```java
            @Select("select * from user where id = #{id}")
            User findById(@Param("id") long id);

            @Select("select * from user where name = #{name}")
            User findByName(@Param("name") String name);

            @Select("select * from user where email = #{email}")
            User findByEmail(@Param("email") String email);
            ```
        
- 示例
    - 接口
        - 参考代码
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/annotation/UserMapper14.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/annotation/UserMapper14.java)
        - 代码内容
            ```java
            public interface UserMapper14 {
                // 获取所有用户
                @Select("select * from user")
                List<User> getUsers();
            
                // 通过Id检索用户
                @Select("select * from user where id = #{id}")
                User getUserByID(@Param("id") int id);
            
                // 插入用户
                @Insert("insert into test01.user (id, name, pwd) values (#{id}, #{name}, #{pwd});")
                int insertUser(User user);
            
                // 更新用户
                @Update("update test01.user set name=#{name} where id = #{id}")
                int updateUser(User user);
            
                // 删除用户
                @Delete("delete from test01.user where id = #{id}")
                int deleteUserById(int id);
            }
            ```
    - 测试类
        - 参考代码
            - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/annotation/AnnotationTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/annotation/AnnotationTest.java)



# Mybatis执行流程分析
[top](#catalog)
- Resources 获取加载全局配置文件
- 实例化 SqlSessionFactoryBuilder 构造器
- 解析配置文件流 XMLConfigBuilder
- 转化为 Configuration 对象，对象内部保存所有的配置信息
- 实例化 SqlSessionFactory 对象
- 创建transactiional事务管理器
- 创建 exector 执行器
- 创建 SqlSession
- 实现 CRUD
- 执行成功，提交事务；执行失败，回滚事务
- 关闭

# 多对一与一对多示例使用的数据库环境
[top](#catalog)
- 多个学生对应一个老师
- 创建表并插入数据
    ```sql
    create table teacher (
        id int(10) not null primary key,
        `name` varchar(30) default null
    );
    insert into teacher(id, `name`) values(1, "teacher001");
    
    ----------------------------------------
    create table student(
        id int(10) not null primary key,
        `name` varchar(30) default null,
        tid int(10) default null,
        key fktid (tid),
        constraint fktid foreign key (tid) references teacher (id)
    );
    
    insert into student (id,`name`,tid) values
    ("1", "student01", "1"), 
    ("2", "student02", "1"), 
    ("3", "student03", "1"), 
    ("4", "student04", "1"), 
    ("5", "student05", "1"), 
    ("6", "student06", "1"); 
    ```


# 多对一关系处理
## 创建多对一关系
[top](#catalog)
- 参考代码
    - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean02/Student.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean02/Student.java)
    - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean02/Teacher.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean02/Teacher.java)
- Teacher
    ```java
    public class Teacher {
        private int id;
        private String name;
        
        //getter、setter
    }
    ```
- Student
    ```java
    public class Student {
        private int id;
        private String name;
    
        // 产生关联关系时，不直接参照数据库表来设置字段
        // 而已直接添加类之间的依赖关系
        // private int tid;
        private Teacher teacher;
        
        // getter、setter
    }
    ```

## 按照查询进行嵌套处理多对一关系
[top](#catalog)
- 处理方法
    - 将复杂关系的查询拆分成两个sql，即主查询与子查询
    - 主查询中查询student，主查询的结果是一个ResultMap，通过ResultMap来设置子查询的转化规则
    - 通过`<association>`来处理对象
        - 通过select属性标注对应的检索子查询sql
        ```xml
        <association property="主查询对应的类中的属性" column="主查询sql的标识字段，作为子查询的查询条件"
                     javaType="全类名/别名" select="子查询"/>
        ```

- 配置示例
    - 参考配置
        - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/relation/StudentMapper.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/relation/StudentMapper.xml)

    - 配置内容
        ```xml
        <!--按照查询进行嵌套处理-->
        <!--主查询-->
        <select id="getStudents" resultMap="StudentTeacher">
            select * from student;
        </select>
    
        <resultMap id="StudentTeacher" type="student">
            <!--单独处理复杂属性-->
            <association property="teacher" column="tid" javaType="teacher" select="getTeacherById"/>
        </resultMap>
    
        <!--子查询-->
        <select id="getTeacherById" parameterType="int" resultType="teacher">
            select * from teacher where id = #{tid};
        </select>
        ```
- 测试内容
    - 参考代码
        - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/relation/RelationTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/relation/RelationTest.java)
    - 测试代码
        ```java
        @Test
        public void test01(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            List<Student> students = mapper.getStudents();
            for (Student student : students) {
                System.out.println(student);
            }
        }
        ```


## 按照结果进行嵌套处理多对一关系
[top](#catalog)
- 处理方法
    1. 编写一个完整的sql，通过sql联表查询两个表的内容
    2. 配置 resultMap 处理结果映射
        - `<association>` 处理对象
            ```xml
            <association property="主查询对应的类中的属性名" javaType="全类名/别名" >
                <result property="类属性" column="sql中的字段"/>
                ...
            </association>
            ```
   
- 配置示例
    - 参考配置
        - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/relation/StudentMapper.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/relation/StudentMapper.xml)

    - 配置内容
        ```xml
        <select id="getStudents02" resultMap="StudentTeacher02">
            select s.id as sid, s.name as sname, s.tid as tid, t.name as tname
            from student s, teacher t
            where s.tid = t.id
        </select>
        
        <resultMap id="StudentTeacher02" type="student">
            <!--student表的id和name使用了别名，所以配置别名与属性的对应关系-->
            <result property="id" column="sid"/>
            <result property="name" column="sname"/>
            <association property="teacher" javaType="teacher" >
                <result property="id" column="tid"/>
                <result property="name" column="tname"></result>
            </association>
        </resultMap>
        ```
- 测试内容
    - 参考代码
        - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/relation/RelationTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/relation/RelationTest.java)
    - 测试代码
        ```java
        @Test
        public void test02(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            List<Student> students = mapper.getStudents02();
            for (Student student : students) {
                System.out.println(student);
            }
        }
        ```
      
# 一对多关系处理
## 创建一对多关系
[top](#catalog)
- 参考代码
    - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean03/Teacher02.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean03/Teacher02.java)
    - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean03/Student02.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean03/Student02.java)

- Teacher
    ```java
    public class Teacher02 {
        private int id;
        private String name;
  
        // 配置一对多关系：一个老师对应多个学生
        private List<Student02> students;
    
        // getter，setter
    }
    ```
- Student
    ```java
    public class Student02 {
        private int id;
        private String name;

        // getter，setter
    }
    ```
    
## 按照查询嵌套处理一对多关系
[top](#catalog)
- 处理方法
    - 配置主查询和子查询的sql
    - 配置ResultMap来设置`集合类<T>`的转换规则
    - 通过`<collection>`来处理集合
        ```xml
        <resultMap id="..." type="主查询对应的实体类/别名">
            <!--主查询sql的标识字段，作为子查询的查询条件。需要手动添加该映射，否则实体类对象中该属性将是零值-->
            <result property="实体类中的属性" column="主查询sql的标识字段"></result>
            <collection property="主查询对应的类中的属性" column="主查询sql的标识字段，作为子查询的查询条件" 
                        javaType="集合类类名" ofType="集合类中的泛型" select="子查询"></collection>
        </resultMap>
        ```

- 配置示例
    - 参考配置
        - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/relation/oneToMore/Teacher02Mapper.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/relation/oneToMore/Teacher02Mapper.xml)

    - 配置内容
        ```xml
        <!--主查询-->
        <select id="getTeacherById" resultMap="teacherMap02">
            select id, name from teacher
        </select>
      
        <resultMap id="teacherMap02" type="teacher02">
            <!--id被子查询使用，需要手动添加id的映射，否则会为0-->
            <result property="id" column="id"></result>
            <collection property="students" column="id" javaType="ArrayList" ofType="student02" select="getStudent"></collection>
        </resultMap>
      
        <!--子查询-->
        <select id="getStudent" resultType="student02">
            select id, name from student where tid = #{id}
        </select>
        ```
      
- 测试内容
    - 参考代码
        - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/relation/RelationTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/relation/RelationTest.java)
    - 测试代码  
        ```java
        @Test
        public void test03(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            Teacher02Mapper mapper = sqlSession.getMapper(Teacher02Mapper.class);
            Teacher02 t = mapper.getTeacherById(1);
            System.out.println(t);
        }
        ```


## 按照结果嵌套处理一对多关系
[top](#catalog)
- 处理方法
    - 配置联合查询sql
    - 配置ResultMap来设置`集合类<T>`的转换规则
    - 通过`<collection>`来处理集合
        ```xml
        <collection property="主查询对应的类中的属性" javaType="集合类类名" ofType="集合中的泛型类">
            <result property="泛型类中的属性名" column="sql中的字段名"></result>
        </collection>
        ```

- 配置示例
    - 参考配置
        - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/relation/oneToMore/Teacher02Mapper.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/relation/oneToMore/Teacher02Mapper.xml)

    - 配置内容
        ```xml
        <!--联合查询sql-->
        <select id="getTeacherById02" resultMap="teacherMap">
            select t.id as tid, t.name as tname, s.id as sid, s.name as sname
            from teacher t
            inner join student s on t.id = s.tid
        </select>
    
        <resultMap id="teacherMap" type="teacher02">
            <result property="id" column="tid"></result>
            <result property="name" column="tname"></result>
            <collection property="students" ofType="student02">
                <result property="id" column="sid"></result>
                <result property="name" column="sname"></result>
            </collection>
        </resultMap>
        ```
      
- 测试内容
    - 参考代码
        - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/relation/RelationTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/relation/RelationTest.java)
    - 测试代码  
        ```java
        @Test
        public void test04(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            Teacher02Mapper mapper = sqlSession.getMapper(Teacher02Mapper.class);
            Teacher02 t = mapper.getTeacherById02(1);
            System.out.println(t);
        }
        ```

# 动态sql
## 动态sql概述
[top](#catalog)
- 动态sql是指：根据不同的条件生成不同的sql语句
- 动态sql本质还是sql语句，只是可以在sql中执行一些逻辑代码

## 创建动态sql使用的数据库环境
[top](#catalog)
- 创建一个博客信息的table
- sql
    ```sql
    create table blog (
      id varchar(50) not null,
      title varchar(100) not null,
      author varchar(30) not null,
      create_time datetime not null,
      views int(30) not null
    );
    ```

- 创建实体类
    - 参考代码
        - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean04/Blog.java](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/common/bean04/Blog.java)
    - 代码内容
        ```java
        public class Blog {
            private String id;
            private String title;
            private String author;
            private Date create_time;
            private int views;
            // getter setter
        }
        ```  

- 通过测试类添加数据
    - 参考代码
        - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/dynamicSql/BlogMapperTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/dynamicSql/BlogMapperTest.java)
    - 代码内容
        ```java
        @Test
        public void testAdd(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    
            Blog blog = new Blog();
            blog.setId(IDUtils.getUUID());
            blog.setAuthor("mine");
            blog.setTitle("a01");
            blog.setViews(500);
            blog.setCreateTime(new Date());
            mapper.addBlog(blog);
    
            blog.setId(IDUtils.getUUID());
            blog.setTitle("a02");
            mapper.addBlog(blog);
    
            blog.setId(IDUtils.getUUID());
            blog.setTitle("a04");
            mapper.addBlog(blog);
    
            blog.setId(IDUtils.getUUID());
            blog.setTitle("a04");
            mapper.addBlog(blog);
    
            sqlSession.commit();
            sqlSession.close();
        }
        ```

## 动态sql语句
### 所有动态sql语句的配置及测试内容
[top](#catalog)

- [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/dynamicSql/BlogMapper.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/dynamicSql/BlogMapper.xml)
- [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/dynamicSql/BlogMapperTest.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/dynamicSql/BlogMapperTest.java)

### if语句
[top](#catalog)
- 语法:
    ```xml
    <if test="判断表达式">...</if>
    ```
- if语句的问题
    - 在语句外需要使用 `where 1=1` 连接多个以 `and` 开头的条件，防止sql中缺少 and 或 where 导致的异常

- 示例
    - 配置
        ```xml
        <select id="getBlogsIf" parameterType="map" resultType="blog">
            select * from blog where 1=1
            <if test="title != null">
                and title = #{title}
            </if>
            <if test="author != null">
                and author = #{author}
            </if>
        </select>
        ```
    - 测试
        ```java
        @Test
        public void testIf(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            Map<String,Object> map =  new HashMap<>();
    
            // 1. 没有条件
            List<Blog> result = mapper.getBlogsIf(map);
            System.out.println(result);
    
            // 2. 1个条件
            map.put("title", "a01");
            result = mapper.getBlogsIf(map);
            System.out.println(result);
    
            // 3. 2个条件
            map.put("title", "a02");
            map.put("author", "mine");
            result = mapper.getBlogsIf(map);
            System.out.println(result);
      
            sqlSession.close();
        }
        ```
      
### where语句
[top](#catalog)
- 语法：
    ```xml
    <where>其他语句</where>
    ```
  
- where语句的本质，参考：[trim语句](#trim语句)
    ```xml
    <trim prefix="WHERE" prefixOverrides="AND |OR ">
      ...
    </trim>
    ```

- where语句的作用
    - 外部的语句不用额外包含 `where 1=1` 这样的语句来保证结果的正确性
    - 内部的语句按照正常的sql书写习惯设置即可。
    - 拼接sql时，mybatis会检查第一个条件，如果条件是以`and`或`or`开头的，会**自动取去除**
    - 如果内部的语句是空(如if语句全部是false)，则不会添加 where 关键字
- 示例
    - 配置
        ```xml
        <select id="getBlogsWhere" parameterType="map" resultType="blog">
            select * from blog
            <where>
                <if test="title != null">
                    title = #{title}
                </if>
                <if test="author != null">
                    and author = #{author}
                </if>
            </where>
        </select>
        ```
    - 测试
        ```java
        @Test
        public void testWhere(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            Map<String,Object> map =  new HashMap<>();
    
            // 1. 没有条件
            List<Blog> result = mapper.getBlogsWhere(map);
            System.out.println(result);
    
            // 2. 只输入第二个条件
            map.put("author", "mine");
            result = mapper.getBlogsWhere(map);
            System.out.println(result);
    
            // 3. 输入2个条件
            map.put("title", "a02");
            map.put("author", "mine");
            result = mapper.getBlogsWhere(map);
            System.out.println(result);
      
            sqlSession.close();
        }
        ```

### choose_when_otherwise语句
[top](#catalog)
- 语法，类似于`switch...case`，只会执行一个
    ```xml
    <choose>
        <when test="判断表达式">
            ...
        </when>
        <when test="判断表达式">
            ...
        </when>
        <otherwise>
            ...
        </otherwise>
    </choose>
    ```

- 示例
    - 配置
        ```xml
        <select id="getBlogsChoose" parameterType="map" resultType="blog">
            select * from blog
            <where>
                <choose>
                    <when test="title != null">
                        title = #{title}
                    </when>
                    <when test="author != null">
                        and author = #{author}
                    </when>
                    <otherwise>
                        and views = #{views}
                    </otherwise>
                </choose>
            </where>
        </select>
        ```
    - 测试
        ```java
        @Test
        public void testChoose(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    
            // 1. 只输入第二个条件
            Map<String,Object> map01 =  new HashMap<>();
            map01.put("author", "mine");
            List<Blog> result01 = mapper.getBlogsChoose(map01);
            System.out.println("result01 = " + result01);
    
            // 2. 输入2个条件
            Map<String,Object> map02 =  new HashMap<>();
            map02.put("title", "a02");
            map02.put("author", "mine");
            List<Blog> result02 = mapper.getBlogsChoose(map02);
            System.out.println("result02 = " + result02);
    
            // 3. 只输入第3个条件
            Map<String,Object> map03 =  new HashMap<>();
            map03.put("views", "500");
            List<Blog> result03 = mapper.getBlogsChoose(map03);
            System.out.println("result02 = " + result02);
            System.out.println(result03);
    
            sqlSession.close();
        }
        ```

### set语句
[top](#catalog)
- 语法
    ```xml
    update xxx
    <set>
       <if>a=#{a},</if>
       <if>b=#{b},</if>
       <if>c=#{c},</if>
       ...
    </set>
    ```
  
- set语句的本质，参考：[trim语句](#trim语句)
    ```xml
    <trim prefix="SET" suffixOverrides=",">
      ...
    </trim>
    ```
  
- set 语句用于更新语句，会动态设置set关键字，并删除更新语句中无关的 `,`
    - 当语句中有判断语句时，set会自动除去最后一个语句末尾的 `,` 来保证sql的正确性
    - 使用set语句时，内部的判断语句最好在末尾都加 `,`
- 示例
    - 配置
        ```xml
        <update id="updateBlog" parameterType="map">
            update blog
            <set>
                <if test="createTime != null">
                    create_time = #{createTime},
                </if>
                <if test="author != null">
                    author = #{author},
                </if>
                <if test="views != null">
                    views = #{views},
                </if>
            </set>
    
            where title = #{title}
        </update>
        ```
    - 测试
        ```java
        @Test
        public void tetstUpdate(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    
            Map<String, Object> map = new HashMap<>();
            map.put("title", "a02");
            map.put("createTime", new Date());
            map.put("views", 600);
    
            int i = mapper.updateBlog(map);
    
            if (i > 0){
                sqlSession.commit();
            }
    
            sqlSession.close();
        }
        ```
      
### trim语句
[top](#catalog)
- 语法
    ```xml
    <trim prefix="需要在开头添加的内容" 
          prefixOverrides="需要删除的前缀" 
          suffixOverrides="需要删除的后缀">
      ...
    </trim>
    ```
  
- 如：where语句的实现
    - prefixOverrides 会忽略通过管道符分隔的文本序列，前后必须添加空格
    ```xml
    <trim prefix="WHERE" prefixOverrides="AND |OR ">
      ...
    </trim>
    ```
  
### foreach语句
[top](#catalog)
- foreach语句可以进行集合遍历
    - 通常用于创建 `IN` 条件语句。如传入一个数组：`[1,2,3]`，遍历后转换为：`(1, 2, 3)`

- 语法
    - `需要遍历的集合` 一般是参数中的一个集合类型的属性，或者是 map 中的一个键值对
    ```xml
      <foreach item="集合项" index="索引" collection="需要遍历的集合"
          open="前缀" separator="分隔符" close="后缀">
            #{集合项}
      </foreach>
    ```
  
- 示例
    - 配置
        ```xml
        <select id="getBlogsForeach" parameterType="map" resultType="blog">
            select * from blog
            <where>
                <if test="titleList != null">
                    title in
                </if>
                <foreach collection="titleList" item="node"
                        open="(" separator="," close=")">
                    '${node}'
                </foreach>
            </where>
        </select>
        ```
    - 测试
        ```java
        @Test
        public void testForeach(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    
            List<String> titleList = new ArrayList<>(3);
            titleList.add("a01");
            titleList.add("a03");
            titleList.add("a04");
    
            Map<String, Object> map = new HashMap<>();
            map.put("titleList", titleList);
            List<Blog> result = mapper.getBlogsForeach(map);
            System.out.println(result);
        
            sqlSession.close();
        }
        ```

# 缓存
## 缓存简介
[top](#catalog)
- 什么是缓存
    - 保存在内存中的临时数据
- 为什么需要缓存
    - 将用户经常查询的数据放在缓存中，在用户查询数据时，就不需要通过数据库来访问磁盘上的数据（不与磁盘进行交互），直接从缓存中查询，来提高查询效率
    - 减少高并发系统的性能问题
    - 减少与数据库的交互次数、减少系统开销、提高系统效率
- 经常使用且很少修改的数据 适合使用缓存

## mybayis缓存
[top](#catalog)
- mybatis包含一个查询缓存特性，可以方便的定制和配置缓存
- mybatis中的缓存

    |缓存类型|启动状态|描述|
    |-|-|-|-|
    |一级缓存|默认开启|`SqlSession`级别的缓存，也称为本地缓存|
    |默认二级缓存|手动配置和开启|基于`namespace`级别的缓存，也成为全局缓存。开启后在整个mapper中都可以使用|
    |自定义二级缓存|手动配置和开启|通过实现mybatis的`Cache接口`来自定义二级缓存|
    
- 所有 `select` 语句的结果会被缓存
- 缓存失效的情况
    1. 增删改操作(不限于同一条数据)，可能会改变原来的数据，所以会刷新缓存
    2. 查询不同的 mapper.xml
    3. 手动清除：`sqlSession.clearCache()`

## 测试缓存的步骤
[top](#catalog)
- 在 `mybatis-config.xml` 中开启日志
- 编写测试类，如多次执行同一个sql查询相同的记录
- 观察日志中的输出内容
 
## mybayis一级缓存
[top](#catalog)
- mybayis的一级缓存默认开启
- 一级缓存是`SqlSession`级别的缓存，也称为本地缓存
- 一级缓存的作用域：一个`SqlSession`对象的开启到关闭
- 一级缓存的本质：一个Map对象

- 示例
    - mapper.xml
        - 参考代码
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/cache/l1/UserMapper16.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/cache/l1/UserMapper16.xml)
        - 配置内容
            ```xml
            <mapper namespace="com.ljs.learn.mybatis.cache.l1.UserMapper16">
                <select id="getUserById" parameterType="int" resultType="MyUser">
                    select * from user where id = #{id}
                </select>
            
                <update id="updateUser" parameterType="map">
                    update user
                    set name=#{name}, pwd=#{pwd}
                    where id = #{id}
                </update>
            </mapper>
            ```
    - 测试类
        - 参考代码
            - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/cache/l1/UserMapper16Test.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/cache/l1/UserMapper16Test.java)
        - 测试内容
            1. 连续多次查询相同的数据来测试一级缓存
                - 测试方法
                    ```java
                    @Test
                    public void test01(){
                        SqlSession sqlSession = MybatisUtils.getSqlSession();
                        UserMapper16 mapper = sqlSession.getMapper(UserMapper16.class);
                        User result01 = mapper.getUserById(1);
                        User result02 = mapper.getUserById(1);
                        System.out.println("result01 == result02 : " + (result01 == result02));
                    }
                    ```
                - log：两次访问，只执行了一次sql，第二次使用的是一级缓存
                    ```
                     - Created connection 892555958.
                     - Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@353352b6]
                     - ==>  Preparing: select * from user where id = ?  <<-----执行检索
                     - ==> Parameters: 1(Integer)
                     - <==      Total: 1
                    result01 == result02 : true        <<-----访问一级缓存
                    ```
                  
            2. 更新**相同**的数据数据后缓存失效，再次访问**相同**的数据会重新检索
                - 测试方法
                    ```java
                    @Test
                    public void test02(){
                        SqlSession sqlSession = MybatisUtils.getSqlSession();
                        UserMapper16 mapper = sqlSession.getMapper(UserMapper16.class);
                        User result01 = mapper.getUserById(1);
                        
                        // 再次查询 
                        User result02 = mapper.getUserById(1);
                
                        System.out.println("result01 == result02 : " + (result01 == result02));
                
                        // 更新同一条数据
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", 1);
                        map.put("name", "newName");
                        map.put("pwd", "123456");
                
                        mapper.updateUser(map);
                
                        // 重新检索相同的数据
                        User result03 = mapper.getUserById(1);
                
                        System.out.println("result01 == result03 : " + (result01 == result03));
                    }
                    ```
                - log
                    - 更新数据前使用的一级缓存，更新数据后，缓存失效，再次查询会重新进行检索
                    ```
                     - ==>  Preparing: select * from user where id = ?  <<------执行检索
                     - ==> Parameters: 1(Integer)
                     - <==      Total: 1
                    result01 == result02 : true                         <<---访问一级缓存
                     - ==>  Preparing: update user set name=?, pwd=? where id = ? <<--执行更新
                     - ==> Parameters: newName(String), 123456(String), 1(Integer) <--更新不同数据
                     - <==    Updates: 1
                     - ==>  Preparing: select * from user where id = ? <<---重新执行检索
                     - ==> Parameters: 1(Integer)
                     - <==      Total: 1
                    result01 == result03 : false                        
                    ```
        
            3. 更新**不同**的数据后缓存失效，再次访问**相同**的会重新检索
                - 测试方法
                    ```java
                    @Test
                    public void test03(){
                        SqlSession sqlSession = MybatisUtils.getSqlSession();
                        UserMapper16 mapper = sqlSession.getMapper(UserMapper16.class);
                        User result01 = mapper.getUserById(1);
                        
                        // 再次查询
                        User result02 = mapper.getUserById(1);
                
                        System.out.println("result01 == result02 : " + (result01 == result02));
                
                        // 更新其他数据
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", 2);
                        map.put("name", "newName");
                        map.put("pwd", "123456");
                
                        mapper.updateUser(map);
                
                        // 重新检索相同的数据
                        User result03 = mapper.getUserById(1);
                
                        System.out.println("result01 == result03 : " + (result01 == result03));
                    }
                    ```
                - log
                    - 更新数据前使用的一级缓存，更新数据后，缓存失效，再次查询会重新进行检索
                    ```
                     - ==>  Preparing: select * from user where id = ? <<------执行检索
                     - ==> Parameters: 1(Integer)
                     - <==      Total: 1
                    result01 == result02 : true                        <<---访问一级缓存
                     - ==>  Preparing: update user set name=?, pwd=? where id = ?  <<--执行更新
                     - ==> Parameters: newName(String), 123456(String), 2(Integer) <--更新相同数据
                     - <==    Updates: 1
                     - ==>  Preparing: select * from user where id = ? <<---重新执行检索
                     - ==> Parameters: 1(Integer)
                     - <==      Total: 1
                    result01 == result03 : false
                    ```

## mybayis二级缓存
[top](#catalog)
- 什么是mybayis二级缓存
    - 二级缓存也称为**全局缓存**
    - mybatis二级缓存基于**namespace级别的缓存**，即 `一个命名空间/mapper = 一个二级缓存`
    - 因为一级缓存的作用域太小，所以一些场景下需要使用二级缓存
    
- **二级缓存只会在同一个mapper中有效**
    - 多个相同的mapper对象共享一个二级缓存

- 数据如何保存到二级缓存
    - 只有当一级缓存关闭时，如果开启了二级缓存，则数据会自动保存到二级缓存中

- 二级缓存的工作机制
    - 通过`SqlSession`查询的数据被保存在一级缓存中
    - `SqlSession`关闭时，将一级缓存的数据保存到二级缓存
    - 新的`SqlSession`开启后，直接从二级缓存中获取数据

- mybatis二级缓存的使用步骤
    1. mybatis-config.xml 中显示的添加 `cacheEnabled`设置。
        - `cacheEnabled` 默认只为 true，即开启状态，但是一般开发都会进行显示的配置
        ```xml
        <settings>
            <setting name="cacheEnabled" value="true"/>
        </settings>
        ```
    2. 在mapper.xml 中添加 ：`<cache/>` 来启动并配置当前 mapper 的二级缓存
        ```xml
        <cache
          eviction="清除策略"
          flushInterval="刷新间隔"
          size="引用数目"
          readOnly="缓存是否只读"/>
        ```
        
- 二级缓存的策略说明
    - 每个策略的默认值
        
        |策略|说明|默认值|
        |-|-|-|
        |eviction|缓存清除|LRU|
        |flushInterval|刷新间隔|没有刷新时间|
        |size|引用数目|1024|
        |readOnly|缓存是否只读|false|
        
    - `eviction` 缓存清除
    
        |缓存策略|含义|描述|
        |-|-|-|
        |LRU|最近最少使用|**默认策略**，删除最长时间不被使用的对象|
        |FIFP|先进先出|按对象进入缓存的顺序来删除|
        |SOFT|软引用|基于垃圾回收器状态和软引用规则删除对象|
        |WEAK|弱引用|更积极的基于垃圾回收器状态和弱引用规则删除对象|

    - `flushInterval` 刷新间隔
        - 属性值：正整数，单位：毫秒 
        - 默认没有刷新间隔，缓存仅会在增删改时刷新
    
    - `size`
        - 缓存中可用的对象引用数量
        - 使用时需要注意缓存对象的大小和运行环境中的可用内存大小
        - 默认值：1024 
        
    - `readOnly`
        - true：只读缓存，false：可读写缓存
        - 默认值：false
        - 设置为true时，只读缓存会给所有调用者返回相同的实例，并且不能被修改
            - 这种情况下相当于单例的，能有效提升性能
        - 设置为false时，可读写缓存会通过**序列化**返回缓存对象的拷贝
            - 这种情况速度会下降，但是更安全
    
- 示例
    - mapper.xml
        - 使用**只读**缓存进行测试
        - 参考代码
            - [/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/cache/l2/UserMapper17.xml](/java/mylearn/mybatis/src/main/java/com/ljs/learn/mybatis/cache/l2/UserMapper17.xml)
        - 配置内容
            ```xml
            <mapper namespace="com.ljs.learn.mybatis.cache.l2.UserMapper17">
                <cache
                        eviction="FIFO"
                        flushInterval="6000"
                        size="512"
                        readOnly="true"/>
                        <!--readOnly="false"/>-->
            
                <select id="getUserById" parameterType="int" resultType="MyUser">
                    select * from user where id = #{id}
                </select>            
            </mapper>
            ```

    - 测试类
        - 参考代码
            - [/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/cache/l2/UserMapper17Test.java](/java/mylearn/mybatis/src/test/java/com/ljs/learn/mybatis/cache/l2/UserMapper17Test.java)
        - 测试内容
            1. 测试不关闭 SqlSession，一级缓存能够转换为二级缓存
                - 同时打开两个SqlSession对象，同时操作一个mapper，并且同时启动、同时关闭
                - 测试方法
                    ```java
                    @Test
                    public void test01(){
                        // 1. 同时打开两个SqlSession对象，同时操作一个mapper接口，
                        SqlSession sqlSession01 = MybatisUtils.getSqlSession();
                        UserMapper17 mapper1701 = sqlSession01.getMapper(UserMapper17.class);
                
                        SqlSession sqlSession02 = MybatisUtils.getSqlSession();
                        UserMapper17 mapper1702 = sqlSession02.getMapper(UserMapper17.class);
                
                        // 2. 两个SqlSession分别进行检索
                        User result01 = mapper1701.getUserById(1);
                        User result02 = mapper1702.getUserById(1);
                
                        System.out.println("result01 == result02 : " + (result01 == result02));
                
                        // 3. 同时关闭
                        sqlSession01.close();
                        sqlSession02.close();
                    }
                    ```
                - log
                    - 两个sqlSession分别执行了一次检索操作，都还在操作各自的一级缓存，还没有保存到二级缓存，所以结果不同
                    ```
                     - Cache Hit Ratio [com.ljs.learn.mybatis.cache.l2.UserMapper17]: 0.0 <----第一次访问
                     - Opening JDBC Connection
                     - Created connection 1040776996.
                     - Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3e08ff24]
                     - ==>  Preparing: select * from user where id = ? 
                     - ==> Parameters: 1(Integer)
                     - <==      Total: 1
                    
                     - Cache Hit Ratio [com.ljs.learn.mybatis.cache.l2.UserMapper17]: 0.0 <----第二次访问
                     - Opening JDBC Connection
                     - Created connection 292138977.
                     - Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1169afe1]
                     - ==>  Preparing: select * from user where id = ? 
                     - ==> Parameters: 1(Integer)
                     - <==      Total: 1
                     
                    result01 == result02 : false   <-----两次访问的结果不同
                    ```
            2. 创建第一个sqlSession并执行检索，然后关闭，一级缓存的数据保存到二级缓存。再创建第二个sqlSession并执行检索
                - 测试方法
                    ```java
                    @Test
                    public void test02(){
                        // 1. 创建第一个sqlSession并执行检索，然后关闭
                        SqlSession sqlSession01 = MybatisUtils.getSqlSession();
                        UserMapper17 mapper1701 = sqlSession01.getMapper(UserMapper17.class);
                
                        User result01 = mapper1701.getUserById(1);
                        sqlSession01.close();
                
                        // 2. 再创建第二个sqlSession并执行检索
                        SqlSession sqlSession02 = MybatisUtils.getSqlSession();
                        UserMapper17 mapper1702 = sqlSession02.getMapper(UserMapper17.class);
                
                        User result02 = mapper1702.getUserById(1);
                        sqlSession02.close();
                
                        System.out.println("result01 == result02 : " + (result01 == result02));
                    }
                    ```
                - log
                    - 只有第一次查询执行了数据库检索。
                    - 关闭第一个SqlSession之后，一级缓存的数据保存到二级缓存中
                    - 第二次检索时，直接到二级缓存中查询数据。两次的结果是同一个对象
                    ```
                     - Cache Hit Ratio [com.ljs.learn.mybatis.cache.l2.UserMapper17]: 0.0
                     - Opening JDBC Connection
                     - Created connection 308433917.
                     - Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@126253fd]
                     - ==>  Preparing: select * from user where id = ? 
                     - ==> Parameters: 1(Integer)
                     - <==      Total: 1
                     - Resetting autocommit to true on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@126253fd]
                     - Closing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@126253fd]
                     - Returned connection 308433917 to pool.
                     - Cache Hit Ratio [com.ljs.learn.mybatis.cache.l2.UserMapper17]: 0.5
                    result01 == result02 : true
                    ```
            3. 测试二级缓存刷新时间
                - 测试方法
                    ```java
                    @Test
                    public void test03(){
                        // 1. 创建第一个sqlSession并执行检索，然后关闭
                        SqlSession sqlSession01 = MybatisUtils.getSqlSession();
                        UserMapper17 mapper1701 = sqlSession01.getMapper(UserMapper17.class);
                
                        User result01 = mapper1701.getUserById(1);
                        sqlSession01.close();
                
                        // 2. 再创建第二个sqlSession并执行检索
                        SqlSession sqlSession02 = MybatisUtils.getSqlSession();
                        UserMapper17 mapper1702 = sqlSession02.getMapper(UserMapper17.class);
                
                        // 3. 暂停
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                
                        User result02 = mapper1702.getUserById(1);
                        sqlSession02.close();
                
                        System.out.println("result01 == result02 : " + (result01 == result02));
                    }
                    ```
                - log
                    ```
                    <-------第一次执行检索
                    - Cache Hit Ratio [com.ljs.learn.mybatis.cache.l2.UserMapper17]: 0.0
                    - Opening JDBC Connection
                    - Created connection 1040776996.
                    - Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3e08ff24]
                    - ==>  Preparing: select * from user where id = ? 
                    - ==> Parameters: 1(Integer)
                    - <==      Total: 1
                    - Resetting autocommit to true on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3e08ff24]
                    - Closing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3e08ff24]
                    - Returned connection 1040776996 to pool.
                    
                    <-------第二次执行检索
                    - Cache Hit Ratio [com.ljs.learn.mybatis.cache.l2.UserMapper17]: 0.0
                    - Opening JDBC Connection
                    - Checked out connection 1040776996 from pool.
                    - Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3e08ff24]
                    - ==>  Preparing: select * from user where id = ?  <<------缓存被刷新，重新进行了检索
                    - ==> Parameters: 1(Integer)
                    - <==      Total: 1
                    - Resetting autocommit to true on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3e08ff24]
                    - Closing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3e08ff24]
                    - Returned connection 1040776996 to pool.
                    result01 == result02 : false
                    ```
                  
## mybatis缓存原理
[top](#catalog)
- 缓存原理
    - [/java/mybatis/base/imgs/cache/mybatis_Cache_principle.png](/java/mybatis/base/imgs/cache/mybatis_Cache_principle.png)

- 一次数据库访问的缓存顺序
    1. 先在二级缓存中查找
    2. 如果二级缓存中没有，则到一级缓存中查找
    3. 如果缓存中没有数据，则访问数据库

## 自定义二级缓存
[top](#catalog)
- ?????

- useCache ?????
- flushCache ?????