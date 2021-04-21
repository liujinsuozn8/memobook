
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

# 声明式事务原理
## 主要功能的实现方式
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
