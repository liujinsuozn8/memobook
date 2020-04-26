<span id="catalog"></span>

### 目录
- [整合Mybatis](#整合Mybatis)
    - [整合Mybatis的基本步骤](#整合Mybatis的基本步骤)
    - [使用mybatis-spring来整合Mybatis](#使用mybatis-spring来整合Mybatis)
    - [mybatis-spring基本使用步骤的示例分析](#mybatis-spring基本使用步骤的示例分析)
    - [整合mybatis的基本配置方式分析](#整合mybatis的基本配置方式分析)
    - [使用SqlSessionDaoSupport简化Mapper](#使用SqlSessionDaoSupport简化Mapper)
    - [配置声明式事务](#配置声明式事务)
    - [mybatis-spring配置的注意事项](#mybatis-spring配置的注意事项)

- [](#)
- [](#)

# 整合Mybatis
## 整合Mybatis的基本步骤
[top](#catalog)
1. 导入maven依赖
    - junit
    - spring
        - spring 相关的包
        - aop 支持：aspectjweaver
        - spring-JDBC 用于访问数据库
    - mysql
    - mybatis
        - 如果输出日志还需要日志的依赖，如log4j
    - mybatis-spring
2. 编写配置文件
3. 测试

## 使用mybatis-spring来整合Mybatis
[top](#catalog)
- 为什么要使用 mybatis-spring ? 
    - MyBatis-Spring 可以将 MyBatis 代码整合到 Spring 中
    - 它将允许 MyBatis 参与到 Spring 的事务管理之中，创建映射器 mapper 和 SqlSession 并注入到 bean 中，以及将 Mybatis 的异常转换为 Spring 的 DataAccessException
    - 最终，可以做到应用代码不依赖于 MyBatis，Spring 或 MyBatis-Spring

- mybatis-spring的基本使用步骤
    1. 编写数据源配置
    2. 装配sqlSessionFactory
    3. 通过Spring提供的SqlSessionTempalte创建SqlSession
    4. 为Mapper接口添加实现类(装饰类)
        - 因为Spring只能接管类，但是Mybatis使用的接口。
        - 为了能够使用Spring进行注入，需要为每个Mapper接口创建对应的实现类
    5. 将Mapper实现类添加到Spring中
    6. 通过Spring的方式调用mapper对象

## mybatis-spring基本使用步骤的示例分析
[top](#catalog)
- 分析示例的参考
    1. 只使用mybatis
        - mapper配置文件
            - [/java/mylearn/myspring/src/main/java/com/ljs/learn/myspring/integration/mybatis/mapper/UserMapper.xml](/java/mylearn/myspring/src/main/java/com/ljs/learn/myspring/integration/mybatis/mapper/UserMapper.xml)
        - mybatis配置文件
            - [/java/mylearn/myspring/src/main/resources/integration/mybatis/mybatisConfig/db.properties](/java/mylearn/myspring/src/main/resources/integration/mybatis/mybatisConfig/db.properties)
            - [/java/mylearn/myspring/src/main/resources/integration/mybatis/mybatisConfig/mybatis-config.xml](/java/mylearn/myspring/src/main/resources/integration/mybatis/mybatisConfig/mybatis-config.xml)
        - 测试代码：
            - [/java/mylearn/myspring/src/test/java/com/ljs/learn/myspring/integration/mybatis/mapper/UserMapperTest.java](/java/mylearn/myspring/src/test/java/com/ljs/learn/myspring/integration/mybatis/mapper/UserMapperTest.java)
    2. 通过spring-mybatis整合mybatis
        - mapper的实现类
            - [/java/mylearn/myspring/src/main/java/com/ljs/learn/myspring/integration/mybatis/mapper/UserMapperImpl.java](/java/mylearn/myspring/src/main/java/com/ljs/learn/myspring/integration/mybatis/mapper/UserMapperImpl.java)
        - spring整合配置
            - [/java/mylearn/myspring/src/main/resources/integration/mybatis/springMybatisConfigByXML/applicationContext.xml](/java/mylearn/myspring/src/main/resources/integration/mybatis/springMybatisConfigByXML/applicationContext.xml)
        - spring-mybaits配置
            - [/java/mylearn/myspring/src/main/resources/integration/mybatis/springMybatisConfigByXML/spring-mybatis.xml](/java/mylearn/myspring/src/main/resources/integration/mybatis/springMybatisConfigByXML/spring-mybatis.xml)
        - mybatis配置文件
            - [/java/mylearn/myspring/src/main/resources/integration/mybatis/springMybatisConfigByXML/mybatis-config.xml](/java/mylearn/myspring/src/main/resources/integration/mybatis/springMybatisConfigByXML/mybatis-config.xml) 
        - 测试代码：
            - [/java/mylearn/myspring/src/test/java/com/ljs/learn/myspring/integration/mybatis/mapper/UserMapperTest.java](/java/mylearn/myspring/src/test/java/com/ljs/learn/myspring/integration/mybatis/mapper/UserMapperTest.java)

- 只使用Mybatis来实现数据库访问的方式
    - 实现代码  
        ```java
        @Test
        public void testMybatis() throws IOException {
            InputStream in = Resources.getResourceAsStream("integration/mybatis/mybatisConfig/mybatis-config.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            SqlSession sqlSession = factory.openSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> users = mapper.getAllUsers();
            System.out.println(users);
        }
        ```

- 通过Spring-Mybatis整合Mybatis，并通过Spring的方式访问数据库
    1. 编写数据源/数据库连接池配置
        - 这里使用Spring默认的数据源，可以根据需要替换
        - spring配置
            ```xml
            <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
                <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="url=jdbc:mysql://127.0.0.1:3307/test01?serverTimezone=UTC&amp;characterEncoding=utf8&amp;useUnicode=true&amp;useSSL=false&amp;allowPublicKeyRetrieval=true"/>
                <property name="username" value="root"/>
                <property name="password" value="1234"/>
            </bean>
            ```
          
        - spring配置相当于 mybatis-config.xml中的`<environments>`配置
            ```xml
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
            ```
    
    2. sqlSessionFactory
        - spring配置
            ```xml
            <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
                <property name="dataSource" ref="dataSource" />
            </bean>
            ```

        - 等价的mybatis实现
            ```java
            InputStream in = Resources.getResourceAsStream("integration/mybatis/mybatis-config.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            ```
    
    3. 通过Spring提供的SqlSessionTempalte创建SqlSession
        - spring配置
            ```xml
            <bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">
                <!--只能通过构造器注入-->
                <constructor-arg index="0" ref="sqlSessionFactory"/>
            </bean>
            ```
        - 等价的mybatis实现
            ```java
            SqlSession sqlSession = factory.openSession();
            ```  
            
    4. 为Mapper接口添加实现类(装饰类)
        ```java
        public class UserMapperImpl implements UserMapper{
           // 通过spring注入 SqlSessionTemplate，来获取Mybatis 生成的mapper对象
           private SqlSessionTemplate sqlSessionTemplate;
        
           public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
               this.sqlSessionTemplate = sqlSessionTemplate;
           }
        
           @Override
           public List<User> getAllUsers() {
               return sqlSessionTemplate.getMapper(UserMapper.class).getAllUsers();
           }
        }
        ```  
       
    5. 将Mapper实现类添加到Spring中
        ```xml
        <bean id="userMapper" class="com.ljs.learn.myspring.integration.mybatis.mapper.UserMapperImpl">
            <!--向类中注入 sqlSessionTemplate 来获取Mybatis创建的 mapper 对象-->
            <property name="sqlSessionTemplate" ref="sqlSessionTemplate"/>
        </bean>
        ```
       
    6. 通过Spring的方式调用mapper对象 
        ```java
        @Test
        public void testSpringMybatis(){
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("integration/mybatis/springMybatisConfigByXML/applicationContext.xml");
            UserMapperImpl userMapper = (UserMapperImpl) context.getBean("userMapper");
            List<User> users = userMapper.getAllUsers();
            System.out.println(users);
        }
        ```
         
## 整合mybatis的基本配置方式分析
[top](#catalog)
- 配置方式
    - applicationContext.xml
        - 负责引入其他相关的spring配置
    - mybatis-config.xml
        - mybatis配置
        - 一般会保留 `<setting>` 和 `<typeAliases>`
    - spring-mybatis.xml
        - 只负责注入mybatis部分的配置
        - 配置后一般会在 applicationContext.xml 中引入
        
- 整体配置示例
    - 参考配置
        - spring整合配置
            - [/java/mylearn/myspring/src/main/resources/integration/mybatis/springMybatisConfigByXML/applicationContext.xml](/java/mylearn/myspring/src/main/resources/integration/mybatis/springMybatisConfigByXML/applicationContext.xml)
        - spring-mybaits配置
            - [/java/mylearn/myspring/src/main/resources/integration/mybatis/springMybatisConfigByXML/spring-mybatis.xml](/java/mylearn/myspring/src/main/resources/integration/mybatis/springMybatisConfigByXML/spring-mybatis.xml)
        - mybatis配置文件
            - [/java/mylearn/myspring/src/main/resources/integration/mybatis/springMybatisConfigByXML/mybatis-config.xml](/java/mylearn/myspring/src/main/resources/integration/mybatis/springMybatisConfigByXML/mybatis-config.xml)
    - spring整合配置
        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://www.springframework.org/schema/beans
                            https://www.springframework.org/schema/beans/spring-beans.xsd">
            <!-- 整合spring-mybatis 配置-->
            <import resource="spring-mybatis.xml"/>
        
            <!--配置mapper对象-->
            <bean id="userMapper" class="com.ljs.learn.myspring.integration.mybatis.mapper.UserMapperImpl">
                <property name="sqlSessionTemplate" ref="sqlSessionTemplate"/>
            </bean>
        </beans>
        ```
    - spring-mybaits配置
        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://www.springframework.org/schema/beans
                            https://www.springframework.org/schema/beans/spring-beans.xsd">
        
            <!--  DataSource:使用Spring的数据源替换Mybatis的配置
              使用Spring提供的JDBC：-->
            <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
                <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://127.0.0.1:3307/test01?serverTimezone=UTC&amp;characterEncoding=utf8&amp;useUnicode=true&amp;useSSL=false&amp;allowPublicKeyRetrieval=true"/>
                <property name="username" value="root"/>
                <property name="password" value="1234"/>
            </bean>
        
            <!-- 配置sqlSessionFactory -->
            <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
                <property name="dataSource" ref="dataSource" />
                <!--  绑定mybatis配置文件  -->
                <property name="configLocation" value="classpath:integration/mybatis/springMybatisConfigByXML/mybatis-config.xml"/>
                <!--  注册mapper.xml   -->
                <property name="mapperLocations" value="classpath:com/ljs/learn/myspring/integration/mybatis/mapper/UserMapper.xml"/>
            </bean>
        
            <!-- 通过Spring提供的SqlSessionTempalte创建SqlSession  -->
            <bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">
                <!--只能通过构造器注入-->
                <constructor-arg index="0" ref="sqlSessionFactory"/>
            </bean>
        </beans>
        ```
    - mybatis配置文件
        ```xml
        <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE configuration
                PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-config.dtd">
        <configuration>
            <!--保留 mybatis设置 和 别名的配置-->
        
            <settings>
                <setting name="logImpl" value="LOG4J"/>
            </settings>
        
            <typeAliases>
                <typeAlias type="com.ljs.learn.myspring.integration.mybatis.bean.User" alias="user"></typeAlias>
            </typeAliases>
        
        </configuration>
        ```
      
## 使用SqlSessionDaoSupport简化Mapper
[top](#catalog)
- 在Mapper的实现类中，通过**继承**SqlSessionDaoSupport，可以直接创建sqlSession对象并使用
- 示例
    - mapper实现类
        - 参考代码
            - [/java/mylearn/myspring/src/main/java/com/ljs/learn/myspring/integration/mybatis/mapper/UserMapperImpl02.java](/java/mylearn/myspring/src/main/java/com/ljs/learn/myspring/integration/mybatis/mapper/UserMapperImpl02.java) 
        - 代码内容
            ```java
            public class UserMapperImpl02 extends SqlSessionDaoSupport implements UserMapper{
                @Override
                public List<User> getAllUsers() {
                    return getSqlSession().getMapper(UserMapper.class).getAllUsers();
                }
            }
            ```
    - spring配置
        - 参考配置
            - [/java/mylearn/myspring/src/main/resources/integration/mybatis/springMybatisConfigByXML/applicationContext.xml](/java/mylearn/myspring/src/main/resources/integration/mybatis/springMybatisConfigByXML/applicationContext.xml)
        - 配置内容
            ```xml
            <bean id="userMapper02" class="com.ljs.learn.myspring.integration.mybatis.mapper.UserMapperImpl02">
                <property name="sqlSessionFactory" ref="sqlSessionFactory"></property>
            </bean>
            ```
    - 测试内容
        - 参考代码
            - [/java/mylearn/myspring/src/test/java/com/ljs/learn/myspring/integration/mybatis/mapper/UserMapperTest.java](/java/mylearn/myspring/src/test/java/com/ljs/learn/myspring/integration/mybatis/mapper/UserMapperTest.java)
        - 代码内容
            ```java
            @Test
            public void testSqlSessionDaoSupport(){
                ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("integration/mybatis/springMybatisConfigByXML/applicationContext.xml");
                UserMapperImpl02 useMapper = (UserMapperImpl02) context.getBean("userMapper02");
                List<User> users = useMapper.getAllUsers();
                System.out.println(users);
            }
            ```

## 配置声明式事务
[top](#catalog)
- 事务将一组业务当作一个业务来做，即所有业务同时成功，同时失败
- 事务涉及到数据的完整性、一致性，非常重要
- spring中的事务管理
    - 声明式事务：通过AOP，将事务织入到业务中
    - 编程式事务：需要修改代码，在代码中进行事务管理

- 配置声明式事务的一种方式：通过AOP织入事务
    1. 开启声明式事务
    2. 通过 `<tx:advice>` 配置spring事务
    3. 添加事务切入点，并将事务切入mapper中
    
- 示例说明
    - mapper实现类
        - 参考代码
            - [/java/mylearn/myspring/src/main/java/com/ljs/learn/myspring/integration/mybatis/transaction/UserMapperImpl.java](/java/mylearn/myspring/src/main/java/com/ljs/learn/myspring/integration/mybatis/transaction/UserMapperImpl.java)
        - 代码内容
            ```xml
            <mapper namespace="com.ljs.learn.myspring.integration.mybatis.transaction.UserMapper">
                ...
                <!-- 创建一个sql异常  -->
                <delete id="deleteUser" >
                    deleteX from user where id = #{id};
                    <!--delete from user where id = #{id};-->
                </delete>
            </mapper>
            ```
            ```java
            public class UserMapperImpl implements UserMapper {
                  ...            
                // 先插入在删除，删除时会有异常，通过数据的插入状况来检查事务
                public void transcation(){
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", 100);
                    map.put("name", "xxx");
                    map.put("pwd", "xcvb");
                    addUser(map);
            
                    deleteUser(10);
                }
            }
            ```
    - spring配置
        - 参考内容
            - [/java/mylearn/myspring/src/main/resources/integration/mybatis/transaction/spring-mybatis.xml](/java/mylearn/myspring/src/main/resources/integration/mybatis/transaction/spring-mybatis.xml)
        - 配置内容
            ```xml
            <!--配置声明式事务-->
            <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
                <constructor-arg ref="dataSource" />
            </bean>
        
            <!--通过AOP织入事务-->
            <tx:advice id="txAdvice" transaction-manager="transactionManager">
                <!-- 配置事务的传播特性-->
                <!-- 为方法配置事务 -->
                <tx:attributes>
                    <tx:method name="*" propagation="REQUIRED"/>
                </tx:attributes>
            </tx:advice>
        
            <!--配置事务切入-->
            <aop:config proxy-target-class="true">
                <!--添加切入点：transcation下的所有类的所有方法-->
                <aop:pointcut id="transactionPoint" expression="execution(* com.ljs.learn.myspring.integration.mybatis.transaction.*.*(..))"/>
                <!--事务切入-->
                <aop:advisor advice-ref="txAdvice" pointcut-ref="transactionPoint"/>
            </aop:config>
            ```
     
## mybatis-spring配置的注意事项
[top](#catalog)
1. 配置 `SqlSessionTempalte` 时，只能通过构造器来注入 `sqlSessionFactory`
    - spring配置
        ```xml
        <bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">
            <!--只能通过构造器注入-->
            <constructor-arg index="0" ref="sqlSessionFactory"/>
        </bean>
        ```
    - 源码分析
        ```java
        public class SqlSessionTemplate implements SqlSession, DisposableBean {
            private final SqlSessionFactory sqlSessionFactory;
            private final ExecutorType executorType;
            private final SqlSession sqlSessionProxy;
            private final PersistenceExceptionTranslator exceptionTranslator;
        
            // SqlSessionTemplate 中没有 setter，只有含参构造器
            public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
                this(sqlSessionFactory, sqlSessionFactory.getConfiguration().getDefaultExecutorType());
            }
            
            // ...
        }
        ```