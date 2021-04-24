# Spring-IOC 的本质
- 就是一堆 map，包含了单例对象、环境信息等

# Spring容器创建分析
## 三种主要的后置处理器，及其执行顺序
- beanDefinitionRegistryPostProcessor ，参考 AbstractApplicationContext.invokeBeanFactoryPostProcessor
- beanFactoryPostProcessor ，参考 AbstractApplicationContext.invokeBeanFactoryPostProcessor
- beanPostProcessor，参考 AbstractApplicationContext.

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

## invokeBeanFactoryPostProcessors --- 执行BeanFactoryPostProcessor
- 接上文
- invokeBeanFactoryPostProcessors(beanFactory), 执行 BeanFactoryPostProcessor
- BeanFactoryPostProcessor 是 beanFactory 的后置处理器
    - 两个接口: BeanFactoryPostProcessor, BeanDefinitionRegistryPostProcessor
    - 先执行 BeanDefinitionRegistryPostProcessor
        1. 获取所有的 BeanDefinitionRegistryPostProcessor
        2. 优先执行实现了 PriorityOrdered 优先级接口的实现类
        3. 再优先执行实现了 Ordered 顺序接口的实现类
        4. 最后执行其他没有实现任何接口的实现类
    - 再执行 BeanFactoryPostProcessor 的方法
        1. 获取所有的 BeanFactoryPostProcessor
        2. 优先执行实现了 PriorityOrdered 优先级接口的实现类
        3. 再优先执行实现了 Ordered 顺序接口的实现类
        4. 最后执行其他没有实现任何接口的实现类

## registerBeanPostProcessors --- 注册后置处理器BeanPostProcessor
- 接上文
- registerBeanPostProcessors(beanFactory), 注册 bean 的后置处理器。只注册，不执行。会在 doCreateBean 内部被调用
- 注册流程
    1. 获取所有的 BeanPostProcessor
        - BeanPostProcessor
        - DestructionAwareBeanPostProcessor, 执行 bean 销毁方法的后置处理器
        - InstantiationAwareBeanPostProcessor
        - SmartInstantiationAwareBeanPostProcessor
        - MergedBeanDefinitionPostProcessor
    2. 注册bean，按照不同的优先级来注册
        1. PriorityOrdered
            - 如果有 MergedBeanDefinitionPostProcessor 的实现类，则添加到 `internalPostProcessors` 中，在最后注册
        2. Ordered
        3. 注册其他没有实现任何接口的实现类
        4. 注册临时保存在 `internalPostProcessors` 中的实现类
        5. 最后注册一个 ApplicationListenerDetector，在 bean 创建后，在 postProcessAfterInitialization 中检查 bean 是不是 ApplicationListener。如果是，则保存在容器中

## initMessageSource --- 初始化消息服务组件MessageSource
- 接上文
- initMessageSource(), 初始化 MessageSource 组件
    - 可以实现：国际化、消息绑定、消息解析
- 执行流程
    - 获取 BeanFactory
    - 检查容器中，有没有叫做 messageSource ，并且类型是 MessageSource 的组件
        - 如果没有，创建一个默认的组件: DelegatingMessageSource，并注册到容器
- MessageSource 类的作用
    - 从国际化配置文件中获取某个 key 的值。能按照区域获取
    - 可以通过id: `messageSource`，来自动注入，通过 getMessage 来获取数据

## initApplicationEventMulticaster --- 初始化事件派发器、监听器
- 接上文
- initApplicationEventMulticaster, 初始化事件派发器
- 执行流程
    1. 获取 beanFactory
    2. 从 beanFactory 中获取 applicationEventMulticaster 的组件
    3. 如果 beanFactory 中没有，则创建一个 SimpleApplicationEventMulticaster，并注册到容器
    4. 可以按照名字 applicationEventMulticaster 来注入组件

## onRefresh
- AbstractApplicationContext.onRefresh, 默认是空方法，需要子类来实现

## registerListeners --- 注册所有的 ApplicationListener
- 从容器中获取所有的 ApplicationListener 组件，注册到 ApplicationEventMulticaster
- 如果有早期事件，调用 ApplicationEventMulticaster 将事件派发给各个监听器


## finishBeanFactoryInitialization --- 初始化剩余的单实例 bean
- 获取所有的 Bean 信息，遍历并依次初始化
- 获取当前 bean 的 BeanDefinition: RootBeanDefinition
- 如果 bean 不是抽象的 + 不是懒加载的 + 单例的
    - 遍历所有的 bean 的类名
    - 判断是不是 FactoryBean 。如果是，则调用接口的 getObject 方法获取bean
    - 如果不是，则调用 `getBean`
        - doGetBean(name, null, null, false)
        - Object sharedInstance = getSinglon(beanName), 检查容器中是否已经存在该组件
        - 如果不存在，开始创建 bean
            - markBeanAsCreated, 标记当前类已经被创建了，防止多个线程同时创建一个bean
            - 获取当前 bean 依赖的其他 bean，则再次调用 getBean 创建其他的依赖 bean，然后再创建当前bean
        - 如果当前 bean 是单例的，启动单实例 bean 的创建过程
            - createBean
            - Object bean = resolveBeforeInstantiation(beanName, mbdToUse), 在 bean 创建之前，执行某些后置处理器，尝试创建并返回一个代理对象
                - 提前执行后置处理器: InstantiationAwareBeanPostProcessor
                - 先执行方法: postProcessBeforeInstantiation
                - 再执行方法: postProcessAfterInitialization
            - 如果上一步没有返回代理对象, 执行 doCreateBean
                1. 创建bean实例: `createBeanInstance(beanName, mbd, args)`
                    - 利用工厂方法 + 对象的构造器创建出 bean 实例
                2. 执行一个后置处理器: applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName)
                    - 调用 MergedBeanDefinitionPostProcessor.postProcessMergedBeanDefinition
                3. 给 bean 赋值: populateBean(beanName, mbd, instanceWrapper)
                    1. 又会调用后置处理器 InstantiationAwareBeanPostProcessor
                        ```java
                        for (BeanPostProcessor bp : getBeanPostProcessors()) {
                            if (bp instanceof InstantiationAwareBeanPostProcessor) {
                                InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
                                if (!ibp.postProcessAfterInstantiation(bw.getWrappedInstance(), beanName)) {
                                    return;
                                }
                            }
                        }
                        ```
                    2. 再次获取 InstantiationAwareBeanPostProcessor，调用 postProcessProperties
                        ```java
                        for (BeanPostProcessor bp : getBeanPostProcessors()) {
                            if (bp instanceof InstantiationAwareBeanPostProcessor) {
                                InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
                                PropertyValues pvsToUse = ibp.postProcessProperties(pvs, bw.getWrappedInstance(), beanName);
                                if (pvsToUse == null) {
                                    if (filteredPds == null) {
                                        filteredPds = filterPropertyDescriptorsForDependencyCheck(bw, mbd.allowCaching);
                                    }
                                    pvsToUse = ibp.postProcessPropertyValues(pvs, filteredPds, bw.getWrappedInstance(), beanName);
                                    if (pvsToUse == null) {
                                        return;
                                    }
                                }
                                pvs = pvsToUse;
                            }
                        }
                        ```
                    3. **利用 setter 等方法给 bean 赋值， `applyPropertyValues(beanName, mbd, bw, pvs)`**
                    
                4. bean 初始化: `initializeBean(beanName, exposedObject, mbd);`        
                    - 执行 xxxAware 接口的方法: BeanNameAware, BeanClassLoaderAware, BeanFactoryAware
                    - 执行后置处理器初始化之前的方法 `postProcessBeforeInitialization`
                    - 执行初始化方法 `invokeInitMethods(beanName, wrappedBean, mbd);`
                        - 检测当前 bean 是不是 InitializingBean 接口的实现类，并调用
                        - 检测是否有自定义的初始化方法`mbd.getInitMethodName();`
                        - **其实是执行 spring-bean 声明周期中的初始化方法，与bean的实例化等无关**
                    - 执行后置处理器初始化后的方法 `applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);`
                5. bean 已经创建完了，通过 `getSingleton` 获取对象
                6. 注册 bean 的销毁方法 `registerDisposableBeanIfNecessary(beanName, bean, mbd);`
                    - 就是 bean 生命周期中的销毁方法
                    - 在这里只是注册。当容器关闭时，才会调用
            - 将 bean 添加到缓存中 `addSingleton(beanName, singletonObject);`
    - bean 都创建完成后，遍历所有的bean
        - 如果是 SmartInitializingSingleton 的实现类，调用 `afterSingletonsInstantiated` 方法

## finishRefresh
- 完成 beanFactory 的初始化创建工作。IOC容器创建完成
- `initLifecycleProcessor()`, 初始化和生命周期有关的后置处理器 
    - 将 LifecycleProcessor 接口的实现类加入容器
        - 可以在 BeanFactory 的 onRefresh、onClose 触发
    - 如果容器中没有，则使用默认的实现类: DefaultLifecycleProcessor
- `getLifecycleProcessor().onRefresh();`，获取前面定义的生命周期处理器，并回调 `OnRefresh`
- `publishEvent(new ContextRefreshedEvent(this));`, 发布容器刷新完成事件
    - 通过派发器，调用各个 ApplicationListener

## 总结
- Spring 容器在启动的时候，先会保存所有注册的 Bean 的定义信息
    - xml 或者 注解配置
- Spring 创建 bean 的时机
    - 用到这个 bean 的时候，调用 getBean 创建 bean。创建完成之后保存在容器中
    - 统一创建剩下的单实例bean
- 后置处理器
    - 每一个 bean 创建完成，都会使用各种后置处理器进行处理，来增强bean的功能
- 事件驱动模型
    - ApplicationListener 事件监听
    - 事件派发 ApplicationEventMulticaster
