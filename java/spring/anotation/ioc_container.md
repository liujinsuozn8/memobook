# Spring容器创建分析
## BeanFactory 的创建与预准备
1. refresh
2. prepareRefresh
    - initPropertySources()，初始化一些属性设置。是一个空方法。子类可以设置自定义的属性
    - getEnvironment().validateRequiredProperties(); 校验属性的额合法性
    - this.earlyApplicationEvents = new LinkedHashSet<>(); 保存容器中的一些早期的事件
3. obtainFreshBeanFactory()，获取 bean 工厂
    - GenericApplicationContext.refreshBeanFactory(); 刷新 BeanFactory
        ```java
        private final AtomicBoolean refreshed = new AtomicBoolean();
      
        // 在初始化时，已经创建了 beanFactory
        public GenericApplicationContext() {
            this.beanFactory = new DefaultListableBeanFactory();
        }
      
        @Override
        protected final void refreshBeanFactory() throws IllegalStateException {
      
            if (!this.refreshed.compareAndSet(false, true)) {
                throw new IllegalStateException(
                        "GenericApplicationContext does not support multiple refresh attempts: just call 'refresh' once");
            }
            // 为 beanFactory 设置了一个序列化的Id
            this.beanFactory.setSerializationId(getId());
        }
        ```
    - this.beanFactory = new DefaultListableBeanFactory(); 在该对象初始化时，已经创建了 beanFactory
    - getBeanFactory(), 返回 GenericApplicationContext 内创建的 beanFactory 对象
        ```java
        public final ConfigurableListableBeanFactory getBeanFactory() {
            return this.beanFactory;
        }
        ```
    - 将创建的 BeanFactory 返回，`DefaultListableBeanFactory` 对象
        - 但是此时的 bean 还没有做任何设置，几乎是一个空对象
4. prepareBeanFactory(beanFactory), 对 beanFactory 进行设置
    - 设置很多东西
        - beanFactory 的类加载器
        - 表达式解析器
        - 添加部分 BeanPostProcessor: ApplicationContextAwareProcessor
            - 用于向 ApplicationContextAware 接口的实现类中，注入IOC容器
        - 设置忽略的自动装配接口
            - 即不能通过接口类型来自动注入
        - 注册可以解析的自动装配
            - 可以在任何组件中自动注入: BeanFactory, ResourceLoader, ApplicationEventPublisher, ApplicationContext
        - 添加后置处理器: ApplicationListenerDetector
        - 添加编译时的 AspectJ 支持
        - 向 beanFactory 中注册一些能用的组件
            - ConfigurableEnvironment
            - 一些系统属性：Map<String, Object> getEnvironment().getSystemProperties() 
            - 一些环境变量：Map<String, Object> getEnvironment().getSystemEnvironment() 
            - 
    ```java
    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        // Tell the internal bean factory to use the context's class loader etc.
        // 设置 beanFactory 的类加载器
        beanFactory.setBeanClassLoader(getClassLoader());
        // 表达式解析器
        beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader()));
        beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, getEnvironment()));

        // Configure the bean factory with context callbacks.
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        // 设置忽略的自动装配接口
        beanFactory.ignoreDependencyInterface(EnvironmentAware.class);
        beanFactory.ignoreDependencyInterface(EmbeddedValueResolverAware.class);
        beanFactory.ignoreDependencyInterface(ResourceLoaderAware.class);
        beanFactory.ignoreDependencyInterface(ApplicationEventPublisherAware.class);
        beanFactory.ignoreDependencyInterface(MessageSourceAware.class);
        beanFactory.ignoreDependencyInterface(ApplicationContextAware.class);

        // BeanFactory interface not registered as resolvable type in a plain factory.
        // MessageSource registered (and found for autowiring) as a bean.
        // 注册可以解析的自动装配
        beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
        beanFactory.registerResolvableDependency(ResourceLoader.class, this);
        beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
        beanFactory.registerResolvableDependency(ApplicationContext.class, this);

        // Register early post-processor for detecting inner beans as ApplicationListeners.
        beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));

        // 添加编译时的 AspectJ 支持
        // Detect a LoadTimeWeaver and prepare for weaving, if found.
        if (beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)) {
            beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));
            // Set a temporary ClassLoader for type matching.
            beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));
        }

        // Register default environment beans.
        // 向 beanFactory 中注册一些能用的组件
        if (!beanFactory.containsLocalBean(ENVIRONMENT_BEAN_NAME)) {
            beanFactory.registerSingleton(ENVIRONMENT_BEAN_NAME, getEnvironment());
        }
        if (!beanFactory.containsLocalBean(SYSTEM_PROPERTIES_BEAN_NAME)) {
            beanFactory.registerSingleton(SYSTEM_PROPERTIES_BEAN_NAME, getEnvironment().getSystemProperties());
        }
        if (!beanFactory.containsLocalBean(SYSTEM_ENVIRONMENT_BEAN_NAME)) {
            beanFactory.registerSingleton(SYSTEM_ENVIRONMENT_BEAN_NAME, getEnvironment().getSystemEnvironment());
        }
    }
    ```
5. AbstractApplicationContext.postProcessBeanFactory(beanFactory), beanFactory 准备工作完成后，进行的后置处理
    - 本身是一个空方法，需要子类重写，自己实现

## 执行BeanFactoryPostProcessor
- 接上文
- invokeBeanFactoryPostProcessors(beanFactory), 执行 BeanFactoryPostProcessor 
- BeanFactoryPostProcessor 是 beanFactory 的后置处理器