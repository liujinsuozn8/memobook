# JdbcTemplate
- Spring 对JDBC 的封装
- 配置
    - 引入依赖
        - mysql-connector-java
        - spring-jdbc
            - 里面已经集成了 spring-tx
    - 添加数据库连接池
        ```xml
        <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close">
            <property name="url" value="jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf-8"/>
            <property name="username" value="root"/>
            <property name="password" value="root"/>
            <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        </bean>
        ```
    - 配置 JdbcTemplate 对象，注入 DataSource
        - 可以通过构造器注入，可以通过 set 方法注入
            ```xml
            <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
                <property name="dataSource" ref="dataSource"/>        
            </bean>
            ```
    - 创建 service 创建 dao，在 dao 注入 jdbcTemplate 对象
- 操作数据库
    - /java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/jdbctemplate/jt01/dao/UserDaoImpl.java
    - 添加
        1. 对应数据库表创建实体类
        2. 编写 service 和 dao
            - 在 dao 中做 insert 操作
            - 调用 jdbcTemplate.update(sql, sql参数)
                ```java
                public void add(User user) {
                    String sql = "insert into t_user values (?, ?, ?)";
                    int insertCount = jdbcTemplate.update(sql, user.getUserId(), user.getUsername(), user.getUstatus());
                    System.out.println("insert data count = " + insertCount);
                }
                ```
    - 修改，和添加类似
        ```java
        public void updateUser(User user) {
            String sql = "update t_user set username=?, ustatus=? where user_id=?";
            int updateCount = jdbcTemplate.update(sql, user.getUsername(), user.getUstatus(), user.getUserId());
            System.out.println("update data count = " + updateCount);
        }
        ```
    - 删除，和添加类似
        ```java
        public void deleteUser(String userId) {
            String sql = "delete from t_user where user_id=?";
            int deleteCount = jdbcTemplate.update(sql, userId);
            System.out.println("delete data count = " + deleteCount);
        }
        ```
- 查询操作
    - 查询返回某个值, `T queryForObject(String sql, Class<T> requiredType)`
        ```java
        public int selectCount() {
            String sql = "select count(*) from t_user";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
            return count;
        }
        ```
    - 返回一个对象, `T queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)`
        ```java
        public User findUser(String userId) {
            String sql = "select * from t_user where user_id=?";
    
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userId);
            return user;
        }
        ```
    - 返回集合, `List<T> query(String sql, RowMapper<T> rowMapper, @Nullable Object... args)`
        ```java
        public List<User> findAllUser() {
            String sql = "select * from t_user";
            List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
            return userList;
        }
        ```
- 批量操作
    - 批量添加
        ```java
        public void batchAddUser(List<Object[]> batchArgs) {
            String sql = "insert into t_user values (?, ?, ?)";
            int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
            System.out.println(Arrays.toString(ints));
        }
        ```
    - 批量修改
        ```java
        public void batchUpdateUser(List<Object[]> batchArgs) {
            String sql = "update t_user set username=?, ustatus=? where user_id=?";
            int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
            System.out.println(Arrays.toString(ints));
        }
        ```
    - 批量删除
        ```java
        @Override
        public void batchDeleteUser(List<Object[]> batchArgs) {
            String sql = "delete from t_user where user_id=?";
            int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
            System.out.println(Arrays.toString(ints));
        }
        ```

- RowMapper
    - 一个接口，针对不同返回类型的数据，使用这个接口实现数据封装
            
# 声明式事务的环境搭建
- 导入相关依赖
    - 数据源、数据库驱动、SpringJdbc模块
        ```xml
        <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-jdbc</artifactId>
          <version>5.2.4.RELEASE</version>
        </dependency>

        <dependency>
          <groupId>mysql</groupId>
          <artifactId>mysql-connector-java</artifactId>
          <version>8.0.18</version>
        </dependency>
        ```
- 创建数据库 + 表
    ```sql
    CREATE SCHEMA `test` DEFAULT CHARACTER SET utf8mb4 ;
    
    create table tbl_user(
       id int(11) primary key auto_increment,
       username varchar(50),
       age int(2)
    )
    
    insert into tbl_user(username,age) values ('admin', 18);
    ```
  
# 声明式事务的配置
- 配置数据源、JdbcTemplate 操作数据、<span style='color:red'>开始事务管理注解 + 注册事务管理器</span>
    - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/tx/TxConfig.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/tx/TxConfig.java)
    - 代码内容
        ```java
        // 开启事务管理功能
        @EnableTransactionManagement
        @Configuration
        @ComponentScan("com.ljs.learn.myspringannotation.tx")
        public class TxConfig {
            @Bean
            public DataSource dataSource() throws Exception {
                ComboPooledDataSource dataSource = new ComboPooledDataSource();
                dataSource.setUser("root");
                dataSource.setPassword("root");
                dataSource.setDriverClass("com.mysql.jdbc.Driver");
                dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8");
                return dataSource;
            }
        
            @Bean
            public JdbcTemplate jdbcTemplate(DataSource dataSource){
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
                return jdbcTemplate;
            }
        
            /**
             * 必须注册事务管理器
             * @return
             * @throws Exception
             */
            @Bean
            public PlatformTransactionManager transactionManager() throws Exception {
                return new DataSourceTransactionManager(dataSource());
            }
        }
        ```

- 给方法添加：`@Transactional` 注解，表示当前方法是一个事务方法
    - /java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/tx/UserService.java
    - 代码内容
        ```sql
        @Service
        public class UserService {
            @Autowired
            UserDao userDao;
        
            @Transactional
            public void insertUser(){
                userDao.insert();
                System.out.println("service 插入完成");
            }
        }
        ```

# 事务参数
- propagation, 事务的传播行为
    - 多事务方法（增删改）直接进行调用，这个过程中事务是如何进行管理的
    - 示例
        ```java
        @Transactonal
        public void add(){
            update();
        }
        public void update(){}
        ```
    - 7种行为
        - 最常用的两种
            - Required，如果 add 有事务，调用 update 后，使用 add 的事务。**默认值**
            - Required_New，add 调用 update，无论 add 有没有事务，update 都会创建新事务
        - Supports
            - 如果 add 的事务在运行，update 就在这个事务内运行，否则可以不运行在事务中
        - Not_Supports
            - update 不应该运行在事务中，如果 add 运行在事务中，将 update 挂起
        - Mandatory
            - update 必须运行在事务内，如果 add 没有运行在事务中，就抛出异常
        - Never
            - update 不能运行在事务内，如果 add 运行在事务中，就抛出异常
        - Nested
            - 如果 add 有事务在运行，update 应该创建一个嵌套的事务再运行。否则 update 自己创建一个新的事务并运行
               
- isolation, 事务隔离级别
    - 隔离级别
        
        |                        |脏读|不可重复读|幻读|
        |------------------------|---|--------|---|
        |READ UNCOMMITTED，读未提交|有  |有      |有 |
        |READ COMMITTED，读已提交  |无  |有      |有 |
        |REPEATABLE READ，可重复读 |无  |无      |有 |
        |SERIALIZABLE，串行化      |无  |无      |无 |
        
    - 多事务之间不会产生影响，不考虑隔离级别会产生很多问题
        - 脏读
            - 对于同一条数据，未提交事务 a 读取到未提交事务 b 的数据，然后未提交事务 b **回滚**
             
        - 不可重复读
            - 未提交事务 a 读取到了已提交事务 b 修改后的数据
            - 流程
                - 事务 a 获取到数据 data1
                - 事务 b 获取到数据 data1
                - 事务 b 修改数据 data1 --> data2
                - 事务 b 提交修改
                - 事务 a 可以读取到事务 b 修改的结果 data2
                  
        - 幻读
            - 未提交事务 a 读取到了已提交事务 b 添加的数据
- timeout, 提交的超时时间
    - 在时间内，如果没有提交，会自动回滚
    - 默认为 -1，没有超时时间

- readOnly, 是否只读
    - 读: 查询操作
    - 写: 增删该操作
    - readOnly 默认值 false，表示可以查询 + 增删改
    - readonly = true，只能查
- rollbackFor, 回滚
    - 设置哪些异常触发回滚
- noRollbackFor, 不回滚
    - 设置哪些异常不触发回滚

# 声明式事务原理
## 主要功能的实现方式
- 底层使用 aop
- 注解导入两个组件: AutoProxyRegistrar + ProxyTransactionManagementConfiguration
    - AutoProxyRegistrar，向容器中注入 InfrastructureAdvisorAutoProxyCreator
- 两个主要的组件
    - InfrastructureAdvisorAutoProxyCreator
        - 利用后置处理器机制，在对象创建之后，包装对象，并返回一个代理对象
        - 通过增强器链，代理对象的原始方法
    - ProxyTransactionManagementConfiguration

## 注册过程分析
- @EnableTransactionManagement 注解向容器中引入 TransactionManagementConfigurationSelector
    ```java
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Import({TransactionManagementConfigurationSelector.class})
    public @interface EnableTransactionManagement {
        boolean proxyTargetClass() default false;
    
        AdviceMode mode() default AdviceMode.PROXY;
    
        int order() default 2147483647;
    }
    ```
- 父类 AdviceModeImportSelector 是 ImportSelector 接口的实现类
    - 默认情况下，@EnableTransactionManagement 会使用 AdviceMode.PROXY
    - 所以会导入两个组件 AutoProxyRegistrar + ProxyTransactionManagementConfiguration
        ```java
        public class TransactionManagementConfigurationSelector extends AdviceModeImportSelector<EnableTransactionManagement> {
            public TransactionManagementConfigurationSelector() {
            }
        
            protected String[] selectImports(AdviceMode adviceMode) {
                switch(adviceMode) {
                // 默认情况下，@EnableTransactionManagement 会使用 AdviceMode.PROXY
                case PROXY:
                    // 所以会导入两个组件 AutoProxyRegistrar +  ProxyTransactionManagementConfiguration
                    // 接下
                    return new String[]{AutoProxyRegistrar.class.getName(), ProxyTransactionManagementConfiguration.class.getName()};
                case ASPECTJ:
                    return new String[]{this.determineTransactionAspectClass()};
                default:
                    return null;
                }
            }
        
            private String determineTransactionAspectClass() {
                return ClassUtils.isPresent("javax.transaction.Transactional", this.getClass().getClassLoader()) ? "org.springframework.transaction.aspectj.AspectJJtaTransactionManagementConfiguration" : "org.springframework.transaction.aspectj.AspectJTransactionManagementConfiguration";
            }
        }
        ```

- 接上，AutoProxyRegistrar
    ```java
    public class AutoProxyRegistrar implements ImportBeanDefinitionRegistrar {
        private final Log logger = LogFactory.getLog(getClass());
    
        // 向容器中注入bean
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            boolean candidateFound = false;
            Set<String> annTypes = importingClassMetadata.getAnnotationTypes();
            for (String annType : annTypes) {
                AnnotationAttributes candidate = AnnotationConfigUtils.attributesFor(importingClassMetadata, annType);
                if (candidate == null) {
                    continue;
                }
                Object mode = candidate.get("mode");
                Object proxyTargetClass = candidate.get("proxyTargetClass");
                if (mode != null && proxyTargetClass != null && AdviceMode.class == mode.getClass() &&
                        Boolean.class == proxyTargetClass.getClass()) {
                    candidateFound = true;
                    // 默认情况下是 PROXY，
                    if (mode == AdviceMode.PROXY) {
                        // 接下，注册
                        AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);
                        // 这个属性是 @EnableTransactionManagement 中的字段，默认为false
                        if ((Boolean) proxyTargetClass) {
                            AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
                            return;
                        }
                    }
                }
            }
            if (!candidateFound && logger.isInfoEnabled()) {
                String name = getClass().getSimpleName();
                logger.info(String.format("%s was imported but no annotations were found " +
                        "having both 'mode' and 'proxyTargetClass' attributes of type " +
                        "AdviceMode and boolean respectively. This means that auto proxy " +
                        "creator registration and configuration may not have occurred as " +
                        "intended, and components may not be proxied as expected. Check to " +
                        "ensure that %s has been @Import'ed on the same class where these " +
                        "annotations are declared; otherwise remove the import of %s " +
                        "altogether.", name, name, name));
            }
        }
    
    }
    ```

- 接上，`AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);`
    ```java
    @Nullable
    public static BeanDefinition registerAutoProxyCreatorIfNecessary(BeanDefinitionRegistry registry) {
        return registerAutoProxyCreatorIfNecessary(registry, null);
    }
    ```
- 接上，向容器中注入 InfrastructureAdvisorAutoProxyCreator
    ```java
    @Nullable
    public static BeanDefinition registerAutoProxyCreatorIfNecessary(
            BeanDefinitionRegistry registry, @Nullable Object source) {
        // 注册 InfrastructureAdvisorAutoProxyCreator
        return registerOrEscalateApcAsRequired(InfrastructureAdvisorAutoProxyCreator.class, registry, source);
    }
    ```

## InfrastructureAdvisorAutoProxyCreator分析
- 利用后置处理器机制，在对象创建之后，包装对象，并返回一个代理对象
- 通过增强器链，代理对象的原始方法

- InfrastructureAdvisorAutoProxyCreator 分析
    ```java
    public class InfrastructureAdvisorAutoProxyCreator extends AbstractAdvisorAutoProxyCreator {
    
        @Nullable
        private ConfigurableListableBeanFactory beanFactory;
    
    
        @Override
        protected void initBeanFactory(ConfigurableListableBeanFactory beanFactory) {
            super.initBeanFactory(beanFactory);
            this.beanFactory = beanFactory;
        }
    
        @Override
        protected boolean isEligibleAdvisorBean(String beanName) {
            return (this.beanFactory != null && this.beanFactory.containsBeanDefinition(beanName) &&
                    this.beanFactory.getBeanDefinition(beanName).getRole() == BeanDefinition.ROLE_INFRASTRUCTURE);
        }
    
    }
    ```

- 父类分析，再继承 AbstractAutoProxyCreator，再往上仍然是 SmartInstantiationAwareBeanPostProcessor + BeanFactoryAware
    - 本质上仍然是后置处理器
    - 通过父类中的功能实现拦截器链调用 + 代理对象生成

    ```java
    public abstract class AbstractAdvisorAutoProxyCreator extends AbstractAutoProxyCreator {
        // ...
    }
    ```

## ProxyTransactionManagementConfiguration分析
- 本身是一个配置类
- 向容器中注册事务增强器
    - 事务增强器需要用事务注解的信息，AnnotationTransactionAttributeSource 解析事务注解
- 注册事务拦截器，TransactionInterceptor
    - 保存了事务属性信息、事务管理器
    - 这个类本身是一个方法拦截器 MethodInterceptor
        - 目标方法执行时，会执行拦截器链
        - 拦截器链只有一个，就是 TransactionInterceptor 本身
    - 功能
        1. 先获取事务相关的属性
        2. 获取平台的事务管理器。如果没有指定任何 transactionManager，会从容器中按照类型获取一个管理器
        3. 执行目标方法
            - 如果异常，获取事务管理器，回滚这次操作
            - 如果正常，则提交事务


- ProxyTransactionManagementConfiguration 分析
    ```java
    @Configuration(proxyBeanMethods = false)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public class ProxyTransactionManagementConfiguration extends AbstractTransactionManagementConfiguration {
        // 注册事务增强器
        @Bean(name = TransactionManagementConfigUtils.TRANSACTION_ADVISOR_BEAN_NAME)
        @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
        public BeanFactoryTransactionAttributeSourceAdvisor transactionAdvisor(
                TransactionAttributeSource transactionAttributeSource, TransactionInterceptor transactionInterceptor) {
    
            BeanFactoryTransactionAttributeSourceAdvisor advisor = new BeanFactoryTransactionAttributeSourceAdvisor();
            // 设置事务属性 transactionAttributeSource()
            advisor.setTransactionAttributeSource(transactionAttributeSource);
            // 设置事务拦截器
            advisor.setAdvice(transactionInterceptor);
            if (this.enableTx != null) {
                advisor.setOrder(this.enableTx.<Integer>getNumber("order"));
            }
            return advisor;
        }
    
        // 设置事务属性
        @Bean
        @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
        public TransactionAttributeSource transactionAttributeSource() {
            return new AnnotationTransactionAttributeSource();
        }
    
        // 注册事务拦截器
        @Bean
        @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
        public TransactionInterceptor transactionInterceptor(TransactionAttributeSource transactionAttributeSource) {
            TransactionInterceptor interceptor = new TransactionInterceptor();
            interceptor.setTransactionAttributeSource(transactionAttributeSource);
            if (this.txManager != null) {
                interceptor.setTransactionManager(this.txManager);
            }
            return interceptor;
        }
    
    }
    ```
  
- 事务拦截器 TransactionInterceptor 分析
    - 基本调用
        ```java
        public class TransactionInterceptor extends TransactionAspectSupport implements MethodInterceptor, Serializable {
            @Override
            @Nullable
            public Object invoke(MethodInvocation invocation) throws Throwable {
                // Work out the target class: may be {@code null}.
                // The TransactionAttributeSource should be passed the target class
                // as well as the method, which may be from an interface.
                Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
        
                // Adapt to TransactionAspectSupport's invokeWithinTransaction...
                // 利用事务执行方法
                return invokeWithinTransaction(invocation.getMethod(), targetClass, invocation::proceed);
            }
        
        }
        ```
    - 父类 TransactionAspectSupport.invokeWithinTransaction 分析
        ```java
        @Nullable
        protected Object invokeWithinTransaction(Method method, @Nullable Class<?> targetClass,
                final InvocationCallback invocation) throws Throwable {
        
            // If the transaction attribute is null, the method is non-transactional.
            TransactionAttributeSource tas = getTransactionAttributeSource();
        
            // 1. 获取事务相关的属性
            final TransactionAttribute txAttr = (tas != null ? tas.getTransactionAttribute(method, targetClass) : null);
            // 2. 获取平台的事务管理器
            final TransactionManager tm = determineTransactionManager(txAttr);
        
            if (this.reactiveAdapterRegistry != null && tm instanceof ReactiveTransactionManager) {
                ReactiveTransactionSupport txSupport = this.transactionSupportCache.computeIfAbsent(method, key -> {
                    if (KotlinDetector.isKotlinType(method.getDeclaringClass()) && KotlinDelegate.isSuspend(method)) {
                        throw new TransactionUsageException(
                                "Unsupported annotated transaction on suspending function detected: " + method +
                                ". Use TransactionalOperator.transactional extensions instead.");
                    }
                    ReactiveAdapter adapter = this.reactiveAdapterRegistry.getAdapter(method.getReturnType());
                    if (adapter == null) {
                        throw new IllegalStateException("Cannot apply reactive transaction to non-reactive return type: " +
                                method.getReturnType());
                    }
                    return new ReactiveTransactionSupport(adapter);
                });
                return txSupport.invokeWithinTransaction(
                        method, targetClass, invocation, txAttr, (ReactiveTransactionManager) tm);
            }
        
            PlatformTransactionManager ptm = asPlatformTransactionManager(tm);
            final String joinpointIdentification = methodIdentification(method, targetClass, txAttr);
        
            if (txAttr == null || !(ptm instanceof CallbackPreferringPlatformTransactionManager)) {
                // Standard transaction demarcation with getTransaction and commit/rollback calls.
                // !!! 如果是一个事务，则开启事务
                TransactionInfo txInfo = createTransactionIfNecessary(ptm, txAttr, joinpointIdentification);
        
                Object retVal;
                try {
                    // This is an around advice: Invoke the next interceptor in the chain.
                    // This will normally result in a target object being invoked.
                    // !!! 3. 执行事务方法
                    retVal = invocation.proceedWithInvocation();
                }
                catch (Throwable ex) {
                    // target invocation exception
                    // 异常时回滚
                    completeTransactionAfterThrowing(txInfo, ex);
                    throw ex;
                }
                finally {
                    cleanupTransactionInfo(txInfo);
                }
        
                if (vavrPresent && VavrDelegate.isVavrTry(retVal)) {
                    // Set rollback-only in case of Vavr failure matching our rollback rules...
                    TransactionStatus status = txInfo.getTransactionStatus();
                    if (status != null && txAttr != null) {
                        retVal = VavrDelegate.evaluateTryFailure(retVal, txAttr, status);
                    }
                }
                // 正常执行后，commit 事务
                commitTransactionAfterReturning(txInfo);
                return retVal;
            }
        
            else {
                final ThrowableHolder throwableHolder = new ThrowableHolder();
        
                // It's a CallbackPreferringPlatformTransactionManager: pass a TransactionCallback in.
                try {
                    Object result = ((CallbackPreferringPlatformTransactionManager) ptm).execute(txAttr, status -> {
                        TransactionInfo txInfo = prepareTransactionInfo(ptm, txAttr, joinpointIdentification, status);
                        try {
                            Object retVal = invocation.proceedWithInvocation();
                            if (vavrPresent && VavrDelegate.isVavrTry(retVal)) {
                                // Set rollback-only in case of Vavr failure matching our rollback rules...
                                retVal = VavrDelegate.evaluateTryFailure(retVal, txAttr, status);
                            }
                            return retVal;
                        }
                        catch (Throwable ex) {
                            if (txAttr.rollbackOn(ex)) {
                                // A RuntimeException: will lead to a rollback.
                                if (ex instanceof RuntimeException) {
                                    throw (RuntimeException) ex;
                                }
                                else {
                                    throw new ThrowableHolderException(ex);
                                }
                            }
                            else {
                                // A normal return value: will lead to a commit.
                                throwableHolder.throwable = ex;
                                return null;
                            }
                        }
                        finally {
                            cleanupTransactionInfo(txInfo);
                        }
                    });
        
                    // Check result state: It might indicate a Throwable to rethrow.
                    if (throwableHolder.throwable != null) {
                        throw throwableHolder.throwable;
                    }
                    return result;
                }
                catch (ThrowableHolderException ex) {
                    throw ex.getCause();
                }
                catch (TransactionSystemException ex2) {
                    if (throwableHolder.throwable != null) {
                        logger.error("Application exception overridden by commit exception", throwableHolder.throwable);
                        ex2.initApplicationException(throwableHolder.throwable);
                    }
                    throw ex2;
                }
                catch (Throwable ex2) {
                    if (throwableHolder.throwable != null) {
                        logger.error("Application exception overridden by commit exception", throwableHolder.throwable);
                    }
                    throw ex2;
                }
            }
        }
        ```
    - 父类 TransactionAspectSupport.determineTransactionManager，获取事务管理器
        ```java
        @Nullable
        protected TransactionManager determineTransactionManager(@Nullable TransactionAttribute txAttr) {
            // Do not attempt to lookup tx manager if no tx attributes are set
            if (txAttr == null || this.beanFactory == null) {
                return getTransactionManager();
            }
    
            String qualifier = txAttr.getQualifier();
            if (StringUtils.hasText(qualifier)) {
                return determineQualifiedTransactionManager(this.beanFactory, qualifier);
            }
            else if (StringUtils.hasText(this.transactionManagerBeanName)) {
                return determineQualifiedTransactionManager(this.beanFactory, this.transactionManagerBeanName);
            }
            else {
                TransactionManager defaultTransactionManager = getTransactionManager();
                if (defaultTransactionManager == null) {
                    defaultTransactionManager = this.transactionManagerCache.get(DEFAULT_TRANSACTION_MANAGER_KEY);
                    if (defaultTransactionManager == null) {
                        defaultTransactionManager = this.beanFactory.getBean(TransactionManager.class);
                        this.transactionManagerCache.putIfAbsent(
                                DEFAULT_TRANSACTION_MANAGER_KEY, defaultTransactionManager);
                    }
                }
                return defaultTransactionManager;
            }
        }
        ```
    - 事务异常处理：TransactionAspectSupport.completeTransactionAfterThrowing
        ```java
        protected void completeTransactionAfterThrowing(@Nullable TransactionInfo txInfo, Throwable ex) {
            if (txInfo != null && txInfo.getTransactionStatus() != null) {
                if (logger.isTraceEnabled()) {
                    logger.trace("Completing transaction for [" + txInfo.getJoinpointIdentification() +
                            "] after exception: " + ex);
                }
                if (txInfo.transactionAttribute != null && txInfo.transactionAttribute.rollbackOn(ex)) {
                    try {
                        // 出现异常时，回滚事务 !!1
                        txInfo.getTransactionManager().rollback(txInfo.getTransactionStatus());
                    }
                    catch (TransactionSystemException ex2) {
                        logger.error("Application exception overridden by rollback exception", ex);
                        ex2.initApplicationException(ex);
                        throw ex2;
                    }
                    catch (RuntimeException | Error ex2) {
                        logger.error("Application exception overridden by rollback exception", ex);
                        throw ex2;
                    }
                }
                else {
                    // We don't roll back on this exception.
                    // Will still roll back if TransactionStatus.isRollbackOnly() is true.
                    try {
                        txInfo.getTransactionManager().commit(txInfo.getTransactionStatus());
                    }
                    catch (TransactionSystemException ex2) {
                        logger.error("Application exception overridden by commit exception", ex);
                        ex2.initApplicationException(ex);
                        throw ex2;
                    }
                    catch (RuntimeException | Error ex2) {
                        logger.error("Application exception overridden by commit exception", ex);
                        throw ex2;
                    }
                }
            }
        }
    
        ```
