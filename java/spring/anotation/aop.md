- 参考
    - https://www.bilibili.com/video/BV1gW411W7wy

# AOP
## AOP注解

- `@EnableAspectJAutoProxy`
    - 标识位置: 在配置类中使用，装饰配置类
    - 功能: 开启AOP注解支持，默认使用cglibs代理
    - 相当于XML注解
        ```xml
        <aop:aspectj-autoproxy proxy-target-class="true"/>
        ```
- `@Pointcut("切入点表达式")`
    - 功能: 将AOP类的某个方法设置成公共的切入点
    - 一般直接修饰一个空方法，如
        ```java
        class A {
            @Pointcut("...")
            public void pointCut(){}
        }
        ```
- `@Aspect`
    - 标识位置: AOP类
    - 功能: 表示当前类是切面类，使配置类可以识别

- AOP切面注解
    - 几种位置的注解，标识方法
    
        |方法名|功能|参数|
        |-|-|-|
        |`@Before(value="切入点")`|前置通知||
        |`@After(value="切入点")`|后置通知<br>无论方法是正常结束还是异常结束，都会调用||
        |`@AfterReturning(value="切入点", returning="result")`|返回通知|`returning` 指定接收返回值的参数名|
        |`@AfterThrowing(value="切入点", throwing = "exception")`|异常通知|`throwing` 指定接收异常的参数名|
        |`@Around(value="切入点")`|环绕通知<br>使用动态代理，手动推进目标方法运行，JoinPoint.procced()||

    - 如何设置切入点
        1. 可以使用当前类中，由`@Pointcut`配置的切入点
            ```java
            class A {
                @Pointcut("...")
                public void pointCut(){}
                
                // 引用类内部的公共切入点
                @Before("pointCut()")
                public void xxx(){...}
            }
            ```
        2. 直接使用 **切入点表达式**
    - 对**被装饰方法**的要求
        - 如果要获取被切面方法的信息，`JoinPoint` 参数必须是方法的第一个参数
            ```java
            @AfterReturning(value="切入点", returning="result")
            public void method(JoinPoint joinPoint, Object result){
              // ...
            }
            ```

## AOP注解的使用流程

- 使用流程
    1. 导入aop模块，Spring AOP，spring-aspects
    2. 定义一个业务类
    3. 定义一个切面类，在业务运行时，附加一些额外的操作
    4. 用注解标识切面类的方法，来标注切入点
    5. 将业务类、切面类，都添加到容器中
    6. 在配置类中，使用 `@EnableAspectJAutoProxy` 装饰类，开启AOP注解支持
    
- 示例
    - 参考代码
        - 业务类
            - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/aop/MathCalculator.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/aop/MathCalculator.java)
        - 切面类
            - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/aop/LogAspects.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/aop/LogAspects.java) 
        - 测试类
            - [/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/aop/AopConfigTest.java](/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/aop/AopConfigTest.java)
    - 业务类
        ```java
        public class MathCalculator {
            public int div(int i, int j){
                return i/j;
            }
        }
        ```
    - 切面类
        ```java
        @Aspect
        public class LogAspects {
            // 设置公共的切入表达式
            @Pointcut("execution(* com.ljs.learn.myspringannotation.aop.MathCalculator.*(..))")
            public void pointCut(){}
        
            @Before("pointCut()")
            public void logStart(JoinPoint joinPoint){
                Object[] args = joinPoint.getArgs();
                System.out.println(joinPoint.getSignature().getName() + " 开始，参数列表: {"+ Arrays.toString(args) +"}");
            }
        
            @After(value="pointCut()")
            public void logEnd(JoinPoint joinPoint){
                System.out.println(joinPoint.getSignature().getName() + " 结束");
            }
        
            // 指定 returning 参数接收函数返回值
            @AfterReturning(value="pointCut()", returning="result")
            public void logReturn(JoinPoint joinPoint, Object result){
                System.out.println(joinPoint.getSignature().getName() + " 正常返回，运行结果: {"+ result +"}");
            }
        
            // 指定 throwing 参数接收异常
            @AfterThrowing(value="pointCut()", throwing = "exception")
            public void logException(JoinPoint joinPoint, Exception exception){
                System.out.println(joinPoint.getSignature().getName() + " 异常，异常信息: " + exception);
            }
        }
        ```
    - 测试内容
        ```java
        @Test
        public void calculatorTest(){
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
            MathCalculator calculator = context.getBean(MathCalculator.class);
            int result = calculator.div(6, 3);
            System.out.println(result);
            // div 开始，参数列表: {[6, 3]}
            // div 结束
            // div 正常返回，运行结果: {2}
            // 2
    
            result = calculator.div(6, 0);
            // div 开始，参数列表: {[6, 0]}
            // div 结束
            // div 异常，异常信息: java.lang.ArithmeticException: / by zero
        }
        ```

# aop的执行顺序
- 没有异常
    - @Around Before
    - @Before
    - @Around After
    - @After
    - @AfterReturning
    - 得到方法的返回结果
- 有异常
    - @Around Before
    - @Before
    - @After
    - @AfterThrowing

# AOP原理
## AOP是如何生效的

- 生效的主要方式，`@EnableAspectJAutoProxy` 注册了一个后置处理器：`AnnotationAwareAspectJAutoProxyCreator`

- `AnnotationAwareAspectJAutoProxyCreator` 本身是一个后置处理器。这样在实例化 + 赋值之后，可以进行拦截，并创建代理对象

- AOP生效的流程
    1. `@EnableAspectJAutoProxy` 开启AOP功能
        - 内部通过 `@Import` 导入 `AspectJAutoProxyRegistrar`
    2. 在 `AspectJAutoProxyRegistrar` 中，向容器中注册后置处理器 `AnnotationAwareAspectJAutoProxyCreator` 
        - 通过 `PostProcessorRegistrationDelegate.registerBeanPostProcessors()` 来注册
        - 后置处理器会被注册为: `org.springframework.aop.config.internalAutoProxyCreator`
    3. 注册完成后，通过 `AbstractApplicationContext.finishBeanFactoryInitialization` 来完成剩余bean的创建
    4. `AbstractAutowireCapableBeanFactory.resolveBeforeInstantiation` 拦截bean的创建
        - 检查是否有该bean可用的增强器，即AOP配置的切入处理
            - 如果有，则创建代理对象
    5. 执行目标方法
        1. 从容器中获取代理对象
        2. 执行方法
            1. `DynamicAdvisedInterceptor.intercept()`， 拦截目标方法
            2. 将AOP配置的切入方法组合成: 拦截器链
            3. **递归**执行每个拦截器
            4. 执行原对象的方法
            5. 回溯处于递归状态的拦截器，完成剩余操作

## @EnableAspectJAutoProxy注入分析

- 主要功能
    1. 向容器中导入 `AspectJAutoProxyRegistrar` 类
    2. 将 `AnnotationAwareAspectJAutoProxyCreator`注册为 `org.springframework.aop.config.internalAutoProxyCreator`
- `@EnableAspectJAutoProxy` 注解源码
    ```java
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    // 向容器中导入 `AspectJAutoProxyRegistrar` 类
    @Import(AspectJAutoProxyRegistrar.class)
    public @interface EnableAspectJAutoProxy {
        boolean proxyTargetClass() default false;
        boolean exposeProxy() default false;
    }
    ```

- 被导入的 `AspectJAutoProxyRegistrar` 的源码分析
    1. `AspectJAutoProxyRegistrar` 实现了[ImportBeanDefinitionRegistrar接口](#ImportBeanDefinitionRegistrar接口--手动注册某些组件)，可以手动的向IOC容器中注册组件
        ```java
        // 1. 继承手动注入组件的接口
        class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar {
            @Override
            public void registerBeanDefinitions(
                    AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
                // 2. 向组件中注册 AnnotationAwareAspectJAutoProxyCreator
                AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);
        
                // 3. 获取 EnableAspectJAutoProxy 的注册信息
                AnnotationAttributes enableAspectJAutoProxy =
                        AnnotationConfigUtils.attributesFor(importingClassMetadata, EnableAspectJAutoProxy.class);
                if (enableAspectJAutoProxy != null) {
                    // 4. 如果存在属性 proxyTargetClass
                    if (enableAspectJAutoProxy.getBoolean("proxyTargetClass")) {
                        AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
                    }
                    // 5. 如果存在属性 exposeProxy
                    if (enableAspectJAutoProxy.getBoolean("exposeProxy")) {
                        AopConfigUtils.forceAutoProxyCreatorToExposeProxy(registry);
                    }
                }
            }
        }
        ```
    2. 尝试注册 `AnnotationAwareAspectJAutoProxyCreator`
        ```java
        public abstract class AopConfigUtils {
            // 1. 由 AspectJAutoProxyRegistrar 调用
            @Nullable
            public static BeanDefinition registerAspectJAnnotationAutoProxyCreatorIfNecessary(BeanDefinitionRegistry registry) {
                // 2. 调用下面的重载方法
                return registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry, (Object)null);
            }
        
            @Nullable
            public static BeanDefinition registerAspectJAnnotationAutoProxyCreatorIfNecessary(BeanDefinitionRegistry registry, @Nullable Object source) {
                // 3. 检查容器是否已注册 AnnotationAwareAspectJAutoProxyCreator，如果未注册，则添加到IOC容器中
                return registerOrEscalateApcAsRequired(AnnotationAwareAspectJAutoProxyCreator.class, registry, source);
            }
        }
        ```
    3. 将 `AnnotationAwareAspectJAutoProxyCreator` 注册为 `internalAutoProxyCreator`
        ```java
        public abstract class AopConfigUtils {
            @Nullable
            private static BeanDefinition registerOrEscalateApcAsRequired(Class<?> cls, BeanDefinitionRegistry registry, @Nullable Object source) {
                Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
                // 检查是否包含 internalAutoProxyCreator 的定义信息
                if (registry.containsBeanDefinition("org.springframework.aop.config.internalAutoProxyCreator")) {
                    BeanDefinition apcDefinition = registry.getBeanDefinition("org.springframework.aop.config.internalAutoProxyCreator");
                    if (!cls.getName().equals(apcDefinition.getBeanClassName())) {
                        int currentPriority = findPriorityForClass(apcDefinition.getBeanClassName());
                        int requiredPriority = findPriorityForClass(cls);
                        if (currentPriority < requiredPriority) {
                            apcDefinition.setBeanClassName(cls.getName());
                        }
                    }
                    return null;
                } else {
                    // 初始化时，没有internalAutoProxyCreator
                    // 所以创建一个 AnnotationAwareAspectJAutoProxyCreator 的定义信息
                    RootBeanDefinition beanDefinition = new RootBeanDefinition(cls);
                    beanDefinition.setSource(source);
                    beanDefinition.getPropertyValues().add("order", -2147483648);
                    beanDefinition.setRole(2);
           
                    // 通过 AnnotationAwareAspectJAutoProxyCreator 创建 internalAutoProxyCreator 的定义信息 
                    registry.registerBeanDefinition("org.springframework.aop.config.internalAutoProxyCreator", beanDefinition);
                    return beanDefinition;
                }
            }
        }
        ```

## AnnotationAwareAspectJAutoProxyCreator分析

### 几个分析的要点
- 主要的分析点
    - 注册，生成代理对象的时间，功能，链式调用实现拦截
- <span style='color:red'>需要分清的两个关键的后置处理器</span>
    - InstantiationAwareBeanPostProcessor, 在实例化对象前后执行
    - BeanPostProcess, 在实例化对象完成、初始化前后执行

### AnnotationAwareAspectJAutoProxyCreator的继承关系与重写内容
- AnnotationAwareAspectJAutoProxyCreator的继承关系

    - ![AnnotationAwareAspectJAutoProxyCreator_impl](imgs/aop/principle/AnnotationAwareAspectJAutoProxyCreator_impl.png)
- 主要的实现
    1. SmartInstantiationAwareBeanPostProcessor，后置处理器
    2. BeanFactoryAware，bean工厂
    3. Ordered，Ordered级别的后置处理器
- 主要的继承关系
    - AnnotationAwareAspectJAutoProxyCreator
        - AspectJAwareAdvisorAutoProxyCreator
            - AbstractAdvisorAutoProxyCreator
                - AbstractAutoProxyCreator
                    - SmartInstantiationAwareBeanPostProcessor，后置处理器
                    - BeanFactoryAware，注入bean工厂
- **需要着重分析 SmartInstantiationAwareBeanPostProcessor、BeanFactoryAware，来了解aop的流程**
- 继承关系中的重要实现方法，与被重写的方法
    - AbstractAutoProxyCreator
        - 实现了后置处理器的方法
            - postProcessBeforeInstantiation()
            - postProcessAfterInstantiation()
            - postProcessBeforeInitialization()
            - postProcessAfterInitialization()
        - setBeanFactory()
            - BeanFactoryAware 的实现，用于注入 beanFactory
    - AbstractAdvisorAutoProxyCreator
        - 重写的方法
            - setBeanFactory
                - 继承自 AbstractAutoProxyCreator
        - 实现的方法
            - initBeanFactory()
    - AnnotationAwareAspectJAutoProxyCreator
        - 重写的方法
            - initBeanFactory
                - 继承自 AbstractAdvisorAutoProxyCreator

### AnnotationAwareAspectJAutoProxyCreator的注册

- AnnotationAwareAspectJAutoProxyCreator的继承关系
  
    - ![AnnotationAwareAspectJAutoProxyCreator_impl](imgs/aop/principle/AnnotationAwareAspectJAutoProxyCreator_impl.png)
- 主要的实现
    1. SmartInstantiationAwareBeanPostProcessor，后置处理器
    2. BeanFactoryAware，bean工厂
    3. Ordered，Ordered级别的后置处理器
- 主要的继承关系
    - AnnotationAwareAspectJAutoProxyCreator
        - AspectJAwareAdvisorAutoProxyCreator
            - AbstractAdvisorAutoProxyCreator
                - AbstractAutoProxyCreator
                    - SmartInstantiationAwareBeanPostProcessor，后置处理器
                    - BeanFactoryAware，注入bean工厂
- **需要着重分析 SmartInstantiationAwareBeanPostProcessor、BeanFactoryAware，来了解aop的流程**
- 继承关系中的重要实现方法，与被重写的方法
    - AbstractAutoProxyCreator
        - 实现了后置处理器的方法
            - postProcessBeforeInstantiation()
            - postProcessAfterInstantiation()
            - postProcessBeforeInitialization()
            - postProcessAfterInitialization()
        - setBeanFactory()
            - BeanFactoryAware 的实现，用于注入 beanFactory
    - AbstractAdvisorAutoProxyCreator
        - 重写的方法
            - setBeanFactory
                - 继承自 AbstractAutoProxyCreator
        - 实现的方法
            - initBeanFactory()
    - AnnotationAwareAspectJAutoProxyCreator
        - 重写的方法
            - initBeanFactory
                - 继承自 AbstractAdvisorAutoProxyCreator

- 注册流程
    1. 传入配置类、创建ioc容器
    2. 注册配置类，刷新容器，`AbstractApplicationContext.refresh()`
        - 通过 `AbstractApplicationContext.registerBeanPostProcessors()` 注册后置处理器，来拦截 bean 的创建
    3. 注册后置处理器: `PostProcessorRegistrationDelegate.registerBeanPostProcessors()` 
        1. 获取IOC容器中已经定义的、需要创建对象的所有 `BeanPostProcessor`
            - org.springframework.context.annotation.internalAutowiredAnnotationProcessor
            - org.springframework.context.annotation.internalCommonAnnotationProcessor
            - **org.springframework.aop.config.internalAutoProxyCreator**
                - <span style='color:red'>AnnotationAwareAspectJAutoProxyCreator aop需要关注的内容</span>
        2. 向容器中添加按照级别顺序添加 `BeanPostProcess`
            1. 优先注册实现了 `PriorityOrdered` 接口的 `BeanPostProcessor`
            2. 再向容器中注册实现了 `Ordered` 接口的 `BeanPostProcessor`
            3. 最后注册没有实现优先级接口的 `BeanPostProcessor`
        3. 因为第一次访问时，还没有bean，所以需要创建 `BeanPostProcessor` 实例，并保存在容器中
            - <span style='color:red'>所以注册实际上就是创建bean，并保存在容器中</span>
    4. AnnotationAwareAspectJAutoProxyCreator 继承了 `Ordered` 接口，按照 `Ordered` 的规则来注册到容器中
        1. 通过 `AbstractBeanFactory.getBean` 获取bean
        2. 通过 `AbstractBeanFactory.doGetBean` 执行获取bean的操作
            - 通过 `DefaultSingletonBeanRegistry.getSingleton()` 获取单例对象
        3. 第一次获取时，还没有创建bean，所以需要创建bean
             - 通过 `AbstractAutowireCapableBeanFactory.doCreateBean()`
        4. `doCreateBean()` 内部调用 `AbstractAutowireCapableBeanFactory.initializeBean()`，来完成: 创建bean、给bean赋值、初始化bean
            1. 因为实现了 `BeanFactoryAware` 接口，先处理 `Aware` 接口的方法
                1. 调用 `AbstractAdvisorAutoProxyCreator.setBeanFactory`
                2. 执行 `AnnotationAwareAspectJAutoProxyCreator.initBeanFactory`
            2. 应用 `BeanPostProcessor` 的 `postProcessBeforeInitialization` 方法
            3. 执行自定义的初始化方法
                - 包括 `@Bean`、JSR250注解、`InitializingBean` 接口
            4. 应用 `BeanPostProcessor` 的 `postProcessorsAfterInitialization` 方法
    5. 将实例化后的 `BeanPostProcessor` 添加到 beanFactory 中
        - 在 `PostProcessorRegistrationDelegate.registerBeanPostProcessors()` 的剩余逻辑中执行添加操作

- <span style='color:red'>可以在 `AnnotationAwareAspectJAutoProxyCreator.initBeanFactory` 内部打断点，来观察整个调用链</span>

- <span style='color:red'>从 `registerBeanPostProcessors` 方法开始，创建并注册这个后置处理器</span>

- AnnotationAwareAspectJAutoProxyCreator 的注册流程的代码分析
    1. 传入配置类、创建ioc容器
    2. 注册配置类，初始化容器
        - 启动初始化容器: `AbstractApplicationContext.refresh()`
            ```java
            //
            public AnnotationConfigApplicationContext(Class<?>... componentClasses) {
                this();
                register(componentClasses);
                refresh();
            }
            ```
       - 通过 `AbstractApplicationContext.registerBeanPostProcessors()` 启动注册后置处理器
            ```java
            public void refresh() throws BeansException, IllegalStateException {
                synchronized (this.startupShutdownMonitor) {
                    //...
                    try {
                        // ...
                        // 启动注册后置处理器
                        registerBeanPostProcessors(beanFactory);
                        // ...
                    }
            }
            }
            ```
    3. 注册后置处理器，`PostProcessorRegistrationDelegate.registerBeanPostProcessors()`，将类作为 Ordered 进行初始化
        ```java
        public static void registerBeanPostProcessors(
            ConfigurableListableBeanFactory beanFactory, AbstractApplicationContext applicationContext) {
            // 1. 获取IOC容器中已经定义的、需要创建对象的所有
            String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);

            // 2. 向容器中添加按照级别顺序添加 `BeanPostProcess`
            int beanProcessorTargetCount = beanFactory.getBeanPostProcessorCount() + 1 + postProcessorNames.length;
            beanFactory.addBeanPostProcessor(new BeanPostProcessorChecker(beanFactory, beanProcessorTargetCount));

            // 2.1 优先注册实现了 `PriorityOrdered` 接口的 `BeanPostProcessor`
            sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
            registerBeanPostProcessors(beanFactory, priorityOrderedPostProcessors);

            // 2.2 再向容器中注册实现了 `Ordered` 接口的 `BeanPostProcessor`
            List<BeanPostProcessor> orderedPostProcessors = new ArrayList<>(orderedPostProcessorNames.size());
            for (String ppName : orderedPostProcessorNames) {
                BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
                orderedPostProcessors.add(pp);
                if (pp instanceof MergedBeanDefinitionPostProcessor) {
                    internalPostProcessors.add(pp);
                }
            }
            sortPostProcessors(orderedPostProcessors, beanFactory);
            registerBeanPostProcessors(beanFactory, orderedPostProcessors);

            // 3. 最后注册没有实现优先级接口的 `BeanPostProcessor`
            List<BeanPostProcessor> nonOrderedPostProcessors = new ArrayList<>(nonOrderedPostProcessorNames.size());
            for (String ppName : nonOrderedPostProcessorNames) {
                BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
                nonOrderedPostProcessors.add(pp);
                if (pp instanceof MergedBeanDefinitionPostProcessor) {
                    internalPostProcessors.add(pp);
                }
            }
            registerBeanPostProcessors(beanFactory, nonOrderedPostProcessors);

            // ...
        }
       ```
    4. AnnotationAwareAspectJAutoProxyCreator 继承了 `Ordered` 接口，按照 `Ordered` 的规则，**创建这个类的 bean，并注册到容器中。**
        1. 接 3 中的 `BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);`，创建 `Ordered` 接口的实现类
        2. 通过 `AbstractBeanFactory.doGetBean` 执行获取bean的操作
            - 先执行 `Object sharedInstance = getSingleton(beanName);`，检查缓存中是否已经注册了单例对象
        3. 在 `AbstractBeanFactory.doGetBean` 内，通过  `AbstractBeanFactory.getSingleton()` 创建单例对象
            ```java
            if (mbd.isSingleton()) {
                // 获取单例对象
                sharedInstance = getSingleton(beanName, () -> {
                    try {
                        // 创建 bean
                        return createBean(beanName, mbd, args);
                    }
                    catch (BeansException ex) {
                        // Explicitly remove instance from singleton cache: It might have been put there
                        // eagerly by the creation process, to allow for circular reference resolution.
                        // Also remove any beans that received a temporary reference to the bean.
                        destroySingleton(beanName);
                        throw ex;
                    }
                });
                bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
            }
            ```
        4. 通过 `AbstractAutowireCapableBeanFactory.createBean` 执行获取bean的操作
            ```java
            try {
                // 调用 doCreateBean 创建bean
                Object beanInstance = doCreateBean(beanName, mbdToUse, args);
                if (logger.isTraceEnabled()) {
                    logger.trace("Finished creating instance of bean '" + beanName + "'");
                }
                return beanInstance;
            }
            ```
        5. 第一次获取时，还没有创建bean，所以需要创建bean: `AbstractAutowireCapableBeanFactory.doCreateBean()`
            ```java
            try {
                // 1. 创建bean
                populateBean(beanName, mbd, instanceWrapper);
                // 2. 给bean赋值、初始化bean
                exposedObject = initializeBean(beanName, exposedObject, mbd);
            }
            ```
        6. 调用 `AbstractAutowireCapableBeanFactory.initializeBean()`，来完成: 创建bean、给bean赋值、初始化bean
            - 总体处理过程
                ```java
                protected Object initializeBean(final String beanName, final Object bean, @Nullable RootBeanDefinition mbd) {
                    if (System.getSecurityManager() != null) {
                        AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                            invokeAwareMethods(beanName, bean);
                            return null;
                        }, getAccessControlContext());
                    }
                    else {
                        // 1. 接下，处理 `Aware` 接口的方法
                        invokeAwareMethods(beanName, bean);
                    }

                    Object wrappedBean = bean;
                    if (mbd == null || !mbd.isSynthetic()) {
                        // 2. 应用 `BeanPostProcessor` 的 `postProcessBeforeInitialization` 方法
                        wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
                    }

                    try {
                        // 3. 执行自定义的初始化方法，包括 `@Bean`、JSR250注解、`InitializingBean` 接口
                        invokeInitMethods(beanName, wrappedBean, mbd);
                    }
                    catch (Throwable ex) {
                        throw new BeanCreationException(
                                (mbd != null ? mbd.getResourceDescription() : null),
                                beanName, "Invocation of init method failed", ex);
                    }
                    if (mbd == null || !mbd.isSynthetic()) {
                        // 4. 应用 `BeanPostProcessor` 的 `postProcessorsAfterInitialization` 方法
                        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
                    }

                    return wrappedBean;
                }
                ```
            - 接上， 处理 `Aware` 接口的方法: `AbstractAutowireCapableBeanFactory.invokeAwareMethods()`
                ```java
            	private void invokeAwareMethods(final String beanName, final Object bean) {
            		if (bean instanceof Aware) {
            			if (bean instanceof BeanNameAware) {
            				((BeanNameAware) bean).setBeanName(beanName);
            			}
            			if (bean instanceof BeanClassLoaderAware) {
            				ClassLoader bcl = getBeanClassLoader();
            				if (bcl != null) {
            					((BeanClassLoaderAware) bean).setBeanClassLoader(bcl);
            				}
            			}
            			if (bean instanceof BeanFactoryAware) {
                            // 接下
            				((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
            			}
            		}
            	}
            	```

            - 接上，调用 `AbstractAdvisorAutoProxyCreator.setBeanFactory`
                ```java
                public void setBeanFactory(BeanFactory beanFactory) {
                    super.setBeanFactory(beanFactory);
                    if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
                        throw new IllegalArgumentException("AdvisorAutoProxyCreator requires a ConfigurableListableBeanFactory: " + beanFactory);
                    } else {
                        // 调用 AnnotationAwareAspectJAutoProxyCreator 重写的 initBeanFactory方法
                        // 接下
                        this.initBeanFactory((ConfigurableListableBeanFactory)beanFactory);
                    }
                }
                ```
            - 接上，执行 `AnnotationAwareAspectJAutoProxyCreator.initBeanFactory`
                ```java
                protected void initBeanFactory(ConfigurableListableBeanFactory beanFactory) {
                    super.initBeanFactory(beanFactory);
                    if (this.aspectJAdvisorFactory == null) {
                        this.aspectJAdvisorFactory = new ReflectiveAspectJAdvisorFactory(beanFactory);
                    }

                    this.aspectJAdvisorsBuilder = new AnnotationAwareAspectJAutoProxyCreator.BeanFactoryAspectJAdvisorsBuilderAdapter(beanFactory, this.aspectJAdvisorFactory);
                }
                ```
    5. 将实例化后的 `BeanPostProcessor` 添加到 beanFactory 中

        - 在 `PostProcessorRegistrationDelegate.registerBeanPostProcessors()` 的剩余逻辑中执行添加操作

### AnnotationAwareAspectJAutoProxyCreator的执行时间
[top](#catalog)

- <span style='color:red'>可以在 `AbstractAutowireCapableBeanFactory.resolveBeforeInstantiation` 内部打断点，来观察整个调用链</span>

- <span style='color:red'> @Around 与其他的切面相比，更加复杂，可以在 @Around 标注的方法内的 obj.process() 调用处打断点，来查看整个调用链</span>

- <span style='color:red'>从 `finishBeanFactoryInitialization` 方法开始<span style='color:red'>

- 执行时间
    1. 创建容器
    2. 注册所有后置处理器，包括 `AnnotationAwareAspectJAutoProxyCreator`
    3. 开始创建普通的bean
    4. 创建后，对所有的bean尝试调用 `AnnotationAwareAspectJAutoProxyCreator.postProcessBeforeInstantiation`
- 执行流程分析
    1. 完成BeanFactory初始化工作，创建剩下的单实例
        - `AbstractApplicationContext.finishBeanFactoryInitialization(beanFactory)`
    2. 处理所有非懒加载的单例对象
        - `DefaultListableBeanFactory.preInstantiateSingletons();`
    3. 遍历获取容器中所有的Bean，依次创建对象
        1. 启动创建 bean
            - `AbstractBeanFactory.getBean`
            - `AbstractBeanFactory.doGetBean`
        2. 先尝试从缓存中获取bean，如果能获取到，说明bean之前已经被创建过了，则直接使用；否则再创建
            - `DefaultSingletonBeanRegistry.getSingleton`
        3. 创建 bean
            - AbstractAutowireCapableBeanFactory.createBean
        4. **先尝试用 `AnnotationAwareAspectJAutoProxyCreator` 后置处理器返回一个代理对象**，如果能返回就使用。**但是好像没有什么用**
            - 调用点: `AbstractAutowireCapableBeanFactory.resolveBeforeInstantiation()`
            - 通过 `AnnotationAwareAspectJAutoProxyCreator.postProcessBeforeInstantiation()` 创建代理对象
            - <span style='color:red'>需要在 AbstractAutoProxyCreator.postProcessBeforeInstantiation 内打断点检测</span>
        getCustomTargetSource 返回了一个 null


        5. 如果无法创建代理，则创建真正的bean
            - AbstractAutowireCapableBeanFactory.doCreateBean

- 代码分析
    1. 完成BeanFactory初始化工作，创建剩下的单实例
        - 启动容器 `AnnotationConfigApplicationContext`
            ```java
            public AnnotationConfigApplicationContext(Class<?>... componentClasses) {
                this();
                register(componentClasses);
                refresh();
            }
            ```
            ```java
            public void refresh() throws BeansException, IllegalStateException {
                synchronized (this.startupShutdownMonitor) {
                    // ...
                    try {
                        // ...
                        // 处理非懒加载的单例对象
                        // Instantiate all remaining (non-lazy-init) singletons.
                        finishBeanFactoryInitialization(beanFactory);

                        finishRefresh();
                    }
                }
            }
            ```
        - 开始处理非懒加载的单例对象 `AbstractApplicationContext.finishBeanFactoryInitialization(beanFactory)`
		    ```java
            protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
                // ...
                // 处理非懒加载的单例对象
                beanFactory.preInstantiateSingletons();
            }
            ```
    2. 处理非懒加载的单例对象

    	- `DefaultListableBeanFactory.preInstantiateSingletons();`
    3. 遍历获取容器中所有的Bean，依次创建对象
        1. 启动创建 bean
            - `AbstractBeanFactory.getBean`
            - `AbstractBeanFactory.doGetBean`
        2. 先尝试从缓存中获取bean，如果能获取到，说明bean之前已经被创建过了，则直接使用；否则再创建

            - `DefaultSingletonBeanRegistry.getSingleton`
        3. 创建 bean
            - `AbstractAutowireCapableBeanFactory.createBean`
                ```java
                @Override
                protected Object createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
                    // ...
                    try {
                        // 希望能先返回一个代理对象。如果无法创建，则执行 doCreateBean
                        // 先尝试用后置处理器返回一个代理对象
                        // 如果能创建出来，就返回
                        // 需要通过切入点 execution(..) 和 bean 进行匹配
                        Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
                        if (bean != null) {
                            return bean;
                        }
                    }

                    // 真正创建bean的方法
                    try {
                        Object beanInstance = doCreateBean(beanName, mbdToUse, args);
                        if (logger.isTraceEnabled()) {
                            logger.trace("Finished creating instance of bean '" + beanName + "'");
                        }
                        return beanInstance;
                    }
                }
                ```
        4. 接上，尝试创建代理对象
            - 调用点: `AbstractAutowireCapableBeanFactory.resolveBeforeInstantiation`
                ```java
                @Nullable
                protected Object resolveBeforeInstantiation(String beanName, RootBeanDefinition mbd) {
                    Object bean = null;
                    if (!Boolean.FALSE.equals(mbd.beforeInstantiationResolved)) {
                        // Make sure bean class is actually resolved at this point.
                        if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
                            Class<?> targetType = determineTargetType(beanName, mbd);
                            if (targetType != null) {
                                // 调用 AnnotationAwareAspectJAutoProxyCreator
                                // 实现的 postProcessBeforeInstantiation 方法
                                bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
                                // 然后执行 postProcessAfterInitialization 方法
                                if (bean != null) {
                                    bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
                                }
                            }
                        }
                        mbd.beforeInstantiationResolved = (bean != null);
                    }
                    return bean;
                }
                ```
            - 通过 `AnnotationAwareAspectJAutoProxyCreator.postProcessBeforeInstantiation()` 创建代理对象
                ```java
                @Nullable
                protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
                    // 遍历所有后置处理器
                    for (BeanPostProcessor bp : getBeanPostProcessors()) {
                        if (bp instanceof InstantiationAwareBeanPostProcessor) {
                            InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
                            // InstantiationAwareBeanPostProcessor 是在创建bean实例之前，先尝试用后置处理器进行创建。相当于拦截并创建代理对象
                            // 如果是 InstantiationAwareBeanPostProcessor 的实例，
                            // 执行处理器的 postProcessBeforeInstantiation 方法
                            // AnnotationAwareAspectJAutoProxyCreator 就是这个接口的实例
                            Object result = ibp.postProcessBeforeInstantiation(beanClass, beanName);
                            if (result != null) {
                                return result;
                            }
                        }
                        //...
                    }
                    return null;
                }
                ```
            - 执行 AnnotationAwareAspectJAutoProxyCreator 的父类 `AbstractAutoProxyCreator.postProcessBeforeInstantiation`，创建代理对象

        5. 如果无法创建代理，则创建真正的bean
            - `AbstractAutowireCapableBeanFactory.doCreateBean`

### AnnotationAwareAspectJAutoProxyCreator的功能

- AnnotationAwareAspectJAutoProxyCreator实现的后置处理器的类型
    - SmartInstantiationAwareBeanPostProcessor
        - InstantiationAwareBeanPostProcessor
- 主要实现的后置处理器方法
    - postProcessBeforeInstantiation
    - postProcessAfterInitialization
- 主要的功能
    - 作为后置处理器
    - 在每个bean创建之前，调用`postProcessBeforeInstantiation()`

- 功能实现的流程，调用点: `AbstractAutowireCapableBeanFactory.resolveBeforeInstantiation`
    1. 每一个bean创建之前，调用后置处理器的 `AbstractAutoProxyCreator.postProcessBeforeInstantiation`，**这一步好像没有什么用**
        1. 判断当前bean是否在advisedBeans中
            - advisedBeans 中保存了所有需要增强的bean
        2. 判断当前bean是否是基础类型或者是切面 @Aspect
            - 基础类型
                - Advice.class
                - Pointcut.class
                - Advisor.class
                - AopInfrastructureBean.class
        3. 是否需要跳过
            1. 搜索候选的增强器
                - **就是使用切面注解配置的方法**
                    - 会被封装为: InstantiationModelAwarePointcutAdvisor
            2. 判断每一个增强器是否是 AspectJPointcutAdvisor 类型的
                - 全部都是 AspectJPointcutAdvisor 类型，返回true
                - 否则返回false
    2. 创建对象: `AbstractAutoProxyCreator.postProcessAfterInitialization`，**作为后置处理器，在实例化+赋值后，拦截对象，并创建代理对象**
        1. 获取当前bean的所有增强器
            1. 找到所有候选的增强器
            2. 找到能切入当前bean的增强器
            3. 给增强器排序
        2. 将bean保存到 advisedBeans 中
        3. 如果当前bean有可用的增强器，则创建当前bean的代理对象
            1. 获取所有可用的增强器
            2. 将所有可用增强器保存到 proxyFactory
            3. 由代理工厂创建对象
                - 两种形式的代理对象，Spring自动决定
                    - JdkDynamicAopProxy
                    - ObjenesisCglibAopProxy
        4. 给容器返回当前bean增强过的代理对象
    3. 之后从容器中获取到的是bean的代理对象
        - 执行方法时，代理对象会执行切入的方法

- 代码分析
    1. 每一个bean创建之前，调用后置处理器的 `AbstractAutoProxyCreator.postProcessBeforeInstantiation`，**这一步好像没有什么用，只是连接[AnnotationAwareAspectJAutoProxyCreator的执行时间](#AnnotationAwareAspectJAutoProxyCreator的执行时间)**
        1. `postProcessBeforeInstantiation()` 方法
            ```java
            public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
                Object cacheKey = this.getCacheKey(beanClass, beanName);
                if (!StringUtils.hasLength(beanName) || !this.targetSourcedBeans.contains(beanName)) {
                    // 1. 判断当前bean是否在advisedBeans中
                    // advisedBeans 中保存了所有需要增强的bean
                    if (this.advisedBeans.containsKey(cacheKey)) {
                        return null;
                    }

                    // 2. 判断当前bean是否是基础类型或者是切面 @Aspect
                    // 3. 判断是否需要跳过
                    if (this.isInfrastructureClass(beanClass) || this.shouldSkip(beanClass, beanName)) {
                        this.advisedBeans.put(cacheKey, Boolean.FALSE);
                        return null;
                    }
                }

                //...
            }
            ```
        2. 判断当前bean是否是基础类型或者是切面 @Aspect
            - 整体的判断逻辑: `AnnotationAwareAspectJAutoProxyCreator.isInfrastructureClass`
                ```java
                protected boolean isInfrastructureClass(Class<?> beanClass) {
                    return super.isInfrastructureClass(beanClass) ||  // 判断基本类型
                           this.aspectJAdvisorFactory != null &&
                           this.aspectJAdvisorFactory.isAspect(beanClass);    // 判断是否是切面
                }
                ```

            - `AbstractAutoProxyCreator.isInfrastructureClass`，判断基本类型
                ```java
                protected boolean isInfrastructureClass(Class<?> beanClass) {
                    boolean retVal = Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass) || AopInfrastructureBean.class.isAssignableFrom(beanClass);
                    if (retVal && this.logger.isTraceEnabled()) {
                        this.logger.trace("Did not attempt to auto-proxy infrastructure class [" + beanClass.getName() + "]");
                    }

                    return retVal;
                }
                ```

            - `AbstractAspectJAdvisorFactory.isAspect`，判断是否是切面
                ```java
                public boolean isAspect(Class<?> clazz) {
                    return this.hasAspectAnnotation(clazz) && !this.compiledByAjc(clazz);
                }

                // 检查【当前类】有没有标识切面注解【@Aspect】
                private boolean hasAspectAnnotation(Class<?> clazz) {
                    return AnnotationUtils.findAnnotation(clazz, Aspect.class) != null;
                }
                ```
        3. 判断是否需要跳过: `AspectJAwareAdvisorAutoProxyCreator.shouldSkip`
            ```java
            protected boolean shouldSkip(Class<?> beanClass, String beanName) {
                // 1. 搜索候选的增强，就是使用切面注解配置的方法
                List<Advisor> candidateAdvisors = this.findCandidateAdvisors();
                Iterator var4 = candidateAdvisors.iterator();

                Advisor advisor;
                do {
                    if (!var4.hasNext()) {
                        return super.shouldSkip(beanClass, beanName);
                    }

                    advisor = (Advisor)var4.next();
                  // 判断增强器是否是 AspectJPointcutAdvisor类型的
                  // 全部都是 AspectJPointcutAdvisor 类型，返回true；否则返回false
                } while(!(advisor instanceof AspectJPointcutAdvisor) || !((AspectJPointcutAdvisor)advisor).getAspectName().equals(beanName));

                return true;
            }
            ```

    2. 调用 `AbstractAutoProxyCreator.postProcessAfterInitialization`
        1. 走
            - AbstractApplicationContext.finishBeanFactoryInitialization --> DefaultListableBeanFactory.preInstantiateSingletons
            - --> AbstractBeanFactory.getBean --> doGetBean --> singleton
            - --> AbstractAutowireCapableBeanFactory.createBean --> doCreateBean --> initializeBean --> postProcessAfterInitialization
            - 接下，拦截对象并创建代理对象
        2. 调用 `postProcessAfterInitialization()`
            ```java
            public Object postProcessAfterInitialization(@Nullable Object bean, String beanName) {
                if (bean != null) {
                    Object cacheKey = this.getCacheKey(bean.getClass(), beanName);
                    if (this.earlyProxyReferences.remove(cacheKey) != bean) {
                        // 包装bean
                        return this.wrapIfNecessary(bean, beanName, cacheKey);
                    }
                }

                return bean;
            }

            protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
                if (StringUtils.hasLength(beanName) && this.targetSourcedBeans.contains(beanName)) {
                    return bean;
                } else if (Boolean.FALSE.equals(this.advisedBeans.get(cacheKey))) {
                    return bean;
                } else if (!this.isInfrastructureClass(bean.getClass()) && !this.shouldSkip(bean.getClass(), beanName)) {
                    // 1. 获取当前bean的所有增强器
                    Object[] specificInterceptors = this.getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, (TargetSource)null);
                    if (specificInterceptors != DO_NOT_PROXY) {
                        // 2. 将bean保存到 advisedBeans 中
                        this.advisedBeans.put(cacheKey, Boolean.TRUE);
                        // 3. 如果当前bean有可用的增强器，则创建当前bean的代理对象
                        Object proxy = this.createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
                        this.proxyTypes.put(cacheKey, proxy.getClass());
                        return proxy;
                    } else {
                        this.advisedBeans.put(cacheKey, Boolean.FALSE);
                        return bean;
                    }
                } else {
                    this.advisedBeans.put(cacheKey, Boolean.FALSE);
                    return bean;
                }
            }
            ```
        3. 获取增强器: `AbstractAdvisorAutoProxyCreator.getAdvicesAndAdvisorsForBean`
            1. 获取增强器
                ```java
                @Nullable
                protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, @Nullable TargetSource targetSource) {
                    // 1. 获取候选增强器
                    List<Advisor> advisors = this.findEligibleAdvisors(beanClass, beanName);
                    return advisors.isEmpty() ? DO_NOT_PROXY : advisors.toArray();
                }

                protected List<Advisor> findEligibleAdvisors(Class<?> beanClass, String beanName) {
                    // 2. 获取候选增强器 AnnotationAwareAspectJAutoProxyCreator.findCandidateAdvisors
                    List<Advisor> candidateAdvisors = this.findCandidateAdvisors();
                    // 3. 找到能切入当前bean的增强器
                    List<Advisor> eligibleAdvisors = this.findAdvisorsThatCanApply(candidateAdvisors, beanClass, beanName);
                    this.extendAdvisors(eligibleAdvisors);
                    if (!eligibleAdvisors.isEmpty()) {
                        // 4. 为增强器排序
                        eligibleAdvisors = this.sortAdvisors(eligibleAdvisors);
                    }

                    return eligibleAdvisors;
                }
                ```
            2. 获取候选增强器: `AnnotationAwareAspectJAutoProxyCreator.findCandidateAdvisors`
                ```java
                protected List<Advisor> findCandidateAdvisors() {
                    List<Advisor> advisors = super.findCandidateAdvisors();
                    if (this.aspectJAdvisorsBuilder != null) {
                        advisors.addAll(this.aspectJAdvisorsBuilder.buildAspectJAdvisors());
                    }

                    return advisors;
                }
                ```
            3. 找到能切入当前bean的增强器
                - 搜索bean可用的增强器: `AbstractAdvisorAutoProxyCreator.findAdvisorsThatCanApply`
                    ```java
                    protected List<Advisor> findAdvisorsThatCanApply(
                            List<Advisor> candidateAdvisors, Class<?> beanClass, String beanName) {

                        ProxyCreationContext.setCurrentProxiedBeanName(beanName);
                        try {
                            // 使用工具检查哪些增强器可以切入当前bean
                            return AopUtils.findAdvisorsThatCanApply(candidateAdvisors, beanClass);
                        }
                        finally {
                            ProxyCreationContext.setCurrentProxiedBeanName(null);
                        }
                    }
                    ```
                - 使用工具检查哪些增强器可以切入当前bean: `AopUtils.findAdvisorsThatCanApply`
                    ```java
                    public static List<Advisor> findAdvisorsThatCanApply(List<Advisor> candidateAdvisors, Class<?> clazz) {
                        if (candidateAdvisors.isEmpty()) {
                            return candidateAdvisors;
                        }
                        List<Advisor> eligibleAdvisors = new ArrayList<>();
                        for (Advisor candidate : candidateAdvisors) {
                            if (candidate instanceof IntroductionAdvisor && canApply(candidate, clazz)) {
                                eligibleAdvisors.add(candidate);
                            }
                        }
                        boolean hasIntroductions = !eligibleAdvisors.isEmpty();
                        for (Advisor candidate : candidateAdvisors) {
                            if (candidate instanceof IntroductionAdvisor) {
                                // already processed
                                continue;
                            }
                            // 判断当前增强器能否切入bean
                            if (canApply(candidate, clazz, hasIntroductions)) {
                                // 如果增强器可以切入当前bean，则添加到增强器列表中
                                eligibleAdvisors.add(candidate);
                            }
                        }
                        return eligibleAdvisors;
                    }

                    // 判断当前增强器能否切入bean
                    public static boolean canApply(Advisor advisor, Class<?> targetClass, boolean hasIntroductions) {
                        if (advisor instanceof IntroductionAdvisor) {
                            return ((IntroductionAdvisor) advisor).getClassFilter().matches(targetClass);
                        }
                        else if (advisor instanceof PointcutAdvisor) {
                            PointcutAdvisor pca = (PointcutAdvisor) advisor;
                            // 计算切入点是否可用，并将计算结果返回
                            return canApply(pca.getPointcut(), targetClass, hasIntroductions);
                        }
                        else {
                            // It doesn't have a pointcut so we assume it applies.
                            return true;
                        }
                    }
                    ```
            4. 对增强器进行排序，接 2 的 `eligibleAdvisors = this.sortAdvisors(eligibleAdvisors);`
                - AspectJAwareAdvisorAutoProxyCreator.sortAdvisors
                    ```java
                    @Override
                    @SuppressWarnings("unchecked")
                    protected List<Advisor> sortAdvisors(List<Advisor> advisors) {
                        List<PartiallyComparableAdvisorHolder> partiallyComparableAdvisors = new ArrayList<>(advisors.size());
                        for (Advisor element : advisors) {
                            partiallyComparableAdvisors.add(
                                    new PartiallyComparableAdvisorHolder(element, DEFAULT_PRECEDENCE_COMPARATOR));
                        }
                        // 接下，对所有的增强器进行排序
                        List<PartiallyComparableAdvisorHolder> sorted = PartialOrder.sort(partiallyComparableAdvisors);
                        if (sorted != null) {
                            List<Advisor> result = new ArrayList<>(advisors.size());
                            for (PartiallyComparableAdvisorHolder pcAdvisor : sorted) {
                                result.add(pcAdvisor.getAdvisor());
                            }
                            return result;
                        }
                        else {
                            return super.sortAdvisors(advisors);
                        }
                    }
                    ```
                - PartialOrder.sort，执行排序
                    ```java
                    public static <T extends PartialOrder.PartialComparable> List<T> sort(List<T> objects) {
                        if (objects.size() < 2) {
                            return objects;
                        } else {
                            List<PartialOrder.SortObject<T>> sortList = new LinkedList();
                            Iterator i = objects.iterator();

                            while(i.hasNext()) {
                                addNewPartialComparable(sortList, (PartialOrder.PartialComparable)i.next());
                            }

                            int N = objects.size();

                            label44:
                            for(int index = 0; index < N; ++index) {
                                PartialOrder.SortObject<T> leastWithNoSmallers = null;
                                Iterator var5 = sortList.iterator();

                                while(true) {
                                    PartialOrder.SortObject so;
                                    do {
                                        do {
                                            if (!var5.hasNext()) {
                                                if (leastWithNoSmallers == null) {
                                                    return null;
                                                }

                                                removeFromGraph(sortList, leastWithNoSmallers);
                                                objects.set(index, leastWithNoSmallers.object);
                                                continue label44;
                                            }

                                            so = (PartialOrder.SortObject)var5.next();
                                        } while(!so.hasNoSmallerObjects());
                                    } while(leastWithNoSmallers != null && so.object.fallbackCompareTo(leastWithNoSmallers.object) >= 0);

                                    leastWithNoSmallers = so;
                                }
                            }

                            return objects;
                        }
                    }
                    ```
        4. 如果**当前bean有可用的增强器**，则创建当前bean的代理对象: `AbstractAutoProxyCreator.createProxy`
            ```java
            protected Object createProxy(Class<?> beanClass, @Nullable String beanName,
                    @Nullable Object[] specificInterceptors, TargetSource targetSource) {
                //...

                // 获取增强器
                Advisor[] advisors = buildAdvisors(beanName, specificInterceptors);
                // 将增强器保存到 proxyFactory
                proxyFactory.addAdvisors(advisors);
                proxyFactory.setTargetSource(targetSource);
                customizeProxyFactory(proxyFactory);

                proxyFactory.setFrozen(this.freezeProxy);
                if (advisorsPreFiltered()) {
                    proxyFactory.setPreFiltered(true);
                }

                // 由代理工厂创建对象
                return proxyFactory.getProxy(getProxyClassLoader());
            }
            ```
        5. 创建代理对象
            1. `ProxyFactory.getProxy`
                ```java
                public Object getProxy(@Nullable ClassLoader classLoader) {
                    return createAopProxy().getProxy(classLoader);
                }
                ```
            2. 获取代理工厂: `ProxyCreatorSupport.createAopProxy`
                ```java
                protected final synchronized AopProxy createAopProxy() {
                    if (!this.active) {
                        activate();
                    }
                    return getAopProxyFactory().createAopProxy(this);
                }
                ```
    	    3. 创建代理对象: `DefaultAopProxyFactory.createAopProxy`
                ```java
                @Override
                public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
                    if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
                        Class<?> targetClass = config.getTargetClass();
                        if (targetClass == null) {
                            throw new AopConfigException("TargetSource cannot determine target class: " +
                                    "Either an interface or a target is required for proxy creation.");
                        }
                        // 根据条件决定使用哪种方式创建代理对象
                        if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
                            // 方式1，如果类本身是一个接口或者设置了spring配置使用JDK代理
                            return new JdkDynamicAopProxy(config);
                        }
                        // 方式2，一般都会用 cglib
                        return new ObjenesisCglibAopProxy(config);
                    }
                    else {
                        return new JdkDynamicAopProxy(config);
                    }
                }
                ```
## 被AOP切入的bean是如何执行的
### 被切入方法的基本执行流程

- 容器中保存了bean的代理对象，代理对象中保存了该对象的详细信息
- 通过容器获取的 bean 对象是代理对象
- 通过代理对象执行指定方法的流程
    1. 拦截目标方法
    2. 将AOP配置的切入方法、原对象的方法，组合成: 拦截器链
    3. 执行拦截器链

### 创建拦截器链

- CglibAopProxy.intercept()，拦截目标方法的执行。
    - 由内部静态类: `DynamicAdvisedInterceptor` 来实现并执行
- 拦截目标方法的执行: `DynamicAdvisedInterceptor.intercept()`
    - 主要过程
        1. 获取被代理的原始对象
        2. 根据ProxyFactory对象，获取目标方法的拦截器链
        3. 如果被调用的方法没有拦截器链，直接执行目标方法
        4. 如果有拦截器链
            1. 使用执行的目标对象、目标方法、拦截器链等信息创建一个 `CglibMethodInvocation` 对象
            2. 执行 `proceed()` 方法
    - 代码分析
        ```java
        @Override
        @Nullable
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Object oldProxy = null;
            boolean setProxyContext = false;
            Object target = null;
            TargetSource targetSource = this.advised.getTargetSource();
            try {
                if (this.advised.exposeProxy) {
                    // Make invocation available if necessary.
                    oldProxy = AopContext.setCurrentProxy(proxy);
                    setProxyContext = true;
                }
                // Get as late as possible to minimize the time we "own" the target, in case it comes from a pool...
                // 1. 获取被代理的原始对象
                target = targetSource.getTarget();
                Class<?> targetClass = (target != null ? target.getClass() : null);
                // 2. 获取拦截器链
                List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
                Object retVal;

                // 3. 如果被调用的方法没有拦截器链，直接执行目标方法
                if (chain.isEmpty() && Modifier.isPublic(method.getModifiers())) {
                    Object[] argsToUse = AopProxyUtils.adaptArgumentsIfNecessary(method, args);
                    retVal = methodProxy.invoke(target, argsToUse);
                }
                else {
                    // 4. 如果有拦截器链
                    retVal = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();
                }
                retVal = processReturnType(proxy, target, method, retVal);
                return retVal;
            }
            finally {
                if (target != null && !targetSource.isStatic()) {
                    targetSource.releaseTarget(target);
                }
                if (setProxyContext) {
                    // Restore old proxy.
                    AopContext.setCurrentProxy(oldProxy);
                }
            }
        }
        ```

- 如何获取拦截器链
    - 主要过程
        1. 创建拦截器list，长度等于增强器的数量 + 1个默认的增强器
        2. 遍历所有增强器，将其转换为 `MethodInterceptor`
            - 如果是 `MethodInterceptor`，则直接加入集合
            - 如果不是，使用 `AdvisorAdapter` 将增强器转换为 `MethodInterceptor`
            - 最后返回一个 `MethodInterceptor` 数组
    - 每一个通知方法会被包装成方法拦截器，利用 `MethodInterceptor` 方法拦截器机制执行
    - 代码分析
        1. 通过 advisorChainFactory 工厂获取拦截器链: `AdvisedSupport.getInterceptorsAndDynamicInterceptionAdvice` 
            ```java
            public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, @Nullable Class<?> targetClass) {
                MethodCacheKey cacheKey = new MethodCacheKey(method);
                List<Object> cached = this.methodCache.get(cacheKey);
                if (cached == null) {
                    // 通过 advisorChainFactory 工厂获取拦截器链
                    cached = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(
                            this, method, targetClass);
                    this.methodCache.put(cacheKey, cached);
                }
                return cached;
            }
            ```
        2. 获取拦截器链: `DefaultAdvisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice`
            ```java
            @Override
            public List<Object> getInterceptorsAndDynamicInterceptionAdvice(
                    Advised config, Method method, @Nullable Class<?> targetClass) {

                AdvisorAdapterRegistry registry = GlobalAdvisorAdapterRegistry.getInstance();
                // 获取所有的增强器，即已配置的切入方法
                Advisor[] advisors = config.getAdvisors();

                // 创建拦截器list，长度等于增强器的数量
                List<Object> interceptorList = new ArrayList<>(advisors.length);
                Class<?> actualClass = (targetClass != null ? targetClass : method.getDeclaringClass());
                Boolean hasIntroductions = null;

                for (Advisor advisor : advisors) {
                    if (advisor instanceof PointcutAdvisor) {
                        // Add it conditionally.
                        PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
                        if (config.isPreFiltered() || pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
                            MethodMatcher mm = pointcutAdvisor.getPointcut().getMethodMatcher();
                            boolean match;
                            if (mm instanceof IntroductionAwareMethodMatcher) {
                                if (hasIntroductions == null) {
                                    hasIntroductions = hasMatchingIntroductions(advisors, actualClass);
                                }
                                match = ((IntroductionAwareMethodMatcher) mm).matches(method, actualClass, hasIntroductions);
                            }
                            else {
                                match = mm.matches(method, actualClass);
                            }
                            if (match) {
                                // 遍历所有增强器，将其转换为 `MethodInterceptor`
                                MethodInterceptor[] interceptors = registry.getInterceptors(advisor);
                                if (mm.isRuntime()) {
                                    // Creating a new object instance in the getInterceptors() method
                                    // isn't a problem as we normally cache created chains.
                                    for (MethodInterceptor interceptor : interceptors) {
                                        interceptorList.add(new InterceptorAndDynamicMethodMatcher(interceptor, mm));
                                    }
                                }
                                else {
                                    interceptorList.addAll(Arrays.asList(interceptors));
                                }
                            }
                        }
                    }
                    else if (advisor instanceof IntroductionAdvisor) {
                        IntroductionAdvisor ia = (IntroductionAdvisor) advisor;
                        if (config.isPreFiltered() || ia.getClassFilter().matches(actualClass)) {
                            Interceptor[] interceptors = registry.getInterceptors(advisor);
                            interceptorList.addAll(Arrays.asList(interceptors));
                        }
                    }
                    else {
                        Interceptor[] interceptors = registry.getInterceptors(advisor);
                        interceptorList.addAll(Arrays.asList(interceptors));
                    }
                }

                return interceptorList;
            }
              
            // 遍历所有增强器，将其转换为 `MethodInterceptor`
            @Override
            public MethodInterceptor[] getInterceptors(Advisor advisor) throws UnknownAdviceTypeException {
                List<MethodInterceptor> interceptors = new ArrayList<>(3);
                Advice advice = advisor.getAdvice();
                // 如果是 `MethodInterceptor`，则直接作为增强器使用
                if (advice instanceof MethodInterceptor) {
                    interceptors.add((MethodInterceptor) advice);
                }
                // 如果不是 MethodInterceptor，通过适配器进行转换
                for (AdvisorAdapter adapter : this.adapters) {
                    if (adapter.supportsAdvice(advice)) {
                        interceptors.add(adapter.getInterceptor(advisor));
                    }
                }
                if (interceptors.isEmpty()) {
                    throw new UnknownAdviceTypeException(advisor.getAdvice());
                }
                // 将转换结果返回
                return interceptors.toArray(new MethodInterceptor[0]);
            }
            ```

### 拦截器链的执行

- 拦截器链的调用过程
    - 为什么要排序？
        - 需要不同类型的拦截器按顺序执行
        - 5种拦截器类型及其顺序
            1. ExposeInvocationInterceptor
            2. AspectJAfterThrowingAdvice，异常处理
            3. AfterReturningAdviceInterceptor，返回处理
            4. AspectJAfterAdvice，后置处理
            5. MethodBeforeAdviceInterceptor，前置处理
    - 排序后的拦截器如何执行？
        - 执行时需要的类
          
            - [intercepter_chain_run](imgs/aop/interceptor/intercepter_chain_run.png)
        - 执行方式
            1. 在 `DynamicAdvisedInterceptor.intercept()` 创建 `CglibMethodInvocation` 对象
               
                - 将拦截器链保存在 `CglibMethodInvocation` 中
            2. `CglibMethodInvocation.proceed()` 开始调用拦截器链
               
                - 在内部通过父类的方法调用拦截器链: `ReflectiveMethodInvocation.proceed`
            3. 在 `ReflectiveMethodInvocation.proceed` 中通过 `currentInterceptorIndex` 来记录执行了多少个拦截器
            4. 在 `ReflectiveMethodInvocation.proceed` 中按顺序调用 5 种已经排好序的拦截器
                1. 5种拦截器、`ReflectiveMethodInvocation` 都分别继承了 `MethodInterceptor`
                2. 第一次在 `ReflectiveMethodInvocation.proceed()` 方法内，通过 `拦截器.invoke(this)` 调用第一个拦截器，并且将 `this` 做为参数来调用方法
                    ```java
                    // 将 this 做为参数调用方法
                    ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
                    ```
                3. 在拦截器内，执行自身的逻辑，然后调用 `this.proceed()`，<span style='color:red'>又回到了 `ReflectiveMethodInvocation` 内部</span>
                4. 在 `ReflectiveMethodInvocation` 内部
                    1. 获取下一个拦截器
                    2. 增加 `currentInterceptorIndex` 变量的计数
                    3. 再次调用 `拦截器.invoke(this)`
                5. 重复 3--4 步，完成对所有拦截器的递归调用
                    - 整个递归的过程中，`this`一直使用的是初始创建的`CglibMethodInvocation`对象
                    - `CglibMethodInvocation` 是 `ReflectiveMethodInvocation` 的子类，所以可以一直递归内部的拦截器链
            5. 当 `currentInterceptorIndex == 拦截器的数量` 时，调用原对象的方法
                
                - 此时，拦截器都已经进入递归状态，但还没有执行完
            - 在调用原对象的方法之前，前置处理已经执行完了，只是还处于递归状态，
            6. 开始回溯整个递归调用的过程，完成后置处理、异常捕获等处理
            7. 调用完成
    
- 代码分析
    1. 捕获方法、创建拦截器链，并开始调用拦截器: `DynamicAdvisedInterceptor.intercept()`
        ```java
        @Override
        @Nullable
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Object oldProxy = null;
            boolean setProxyContext = false;
            Object target = null;
            TargetSource targetSource = this.advised.getTargetSource();
            try {
                if (this.advised.exposeProxy) {
                    oldProxy = AopContext.setCurrentProxy(proxy);
                    setProxyContext = true;
                }
                target = targetSource.getTarget();
                Class<?> targetClass = (target != null ? target.getClass() : null);
                List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
                Object retVal;
                if (chain.isEmpty() && Modifier.isPublic(method.getModifiers())) {
                    Object[] argsToUse = AopProxyUtils.adaptArgumentsIfNecessary(method, args);
                    retVal = methodProxy.invoke(target, argsToUse);
                }
                else {
                    // 创建调用对象并执行调用
                    retVal = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();
                }
                retVal = processReturnType(proxy, target, method, retVal);
                return retVal;
            }
            finally {
                if (target != null && !targetSource.isStatic()) {
                    targetSource.releaseTarget(target);
                }
                if (setProxyContext) {
                    AopContext.setCurrentProxy(oldProxy);
                }
            }
        }
        ```
    2. 启动调用: `CglibMethodInvocation.proceed` 
        ```java
        private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
            @Override
            @Nullable
            public Object proceed() throws Throwable {
                try {
                    // 通过父类 ReflectiveMethodInvocation 的方法，来调用拦截器链
                    return super.proceed();
                }
                catch (RuntimeException ex) {
                    throw ex;
                }
                catch (Exception ex) {
                    if (ReflectionUtils.declaresException(getMethod(), ex.getClass())) {
                        throw ex;
                    }
                    else {
                        throw new UndeclaredThrowableException(ex);
                    }
                }
            }
        }
        ```
    3. 从 `ReflectiveMethodInvocation.proceed` 开始调用拦截器链
        ```java
        @Override
        @Nullable
        public Object proceed() throws Throwable {
            /*
               每次递归时，检查 currentInterceptorIndex 是否等于拦截器的数量 - 1
               如果等于，则执行原对象的方法
               第一次执行时，currentInterceptorIndex = -1
               如果需要执行的方法没有拦截器，则 this.interceptorsAndDynamicMethodMatchers.size() - 1 = -1，会直接执行原对象的方法
            */
            if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
                return invokeJoinpoint();
            }

            // 自增 currentInterceptorIndex 后，获取拦截器
            // 如果是第一次执行，则从 -1 变为 0，获取第一个拦截器
            Object interceptorOrInterceptionAdvice =
                    this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
            if (interceptorOrInterceptionAdvice instanceof InterceptorAndDynamicMethodMatcher) {
                InterceptorAndDynamicMethodMatcher dm =
                        (InterceptorAndDynamicMethodMatcher) interceptorOrInterceptionAdvice;
                Class<?> targetClass = (this.targetClass != null ? this.targetClass : this.method.getDeclaringClass());
                if (dm.methodMatcher.matches(this.method, targetClass, this.arguments)) {
                    return dm.interceptor.invoke(this);
                }
                else {
                    return proceed();
                }
            }
            else {
                // 递归调用拦截器
                // 这里的 this 就是：CglibMethodInvocation
                return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
            }
        }
        ```
    4. 5种拦截器
        - ExposeInvocationInterceptor.invoke
            ```java
            // 接收 CglibMethodInvocation---> ReflectiveMethodInvocation 对象
            // 即: invoke(this) 中的 this
            @Override
            public Object invoke(MethodInvocation mi) throws Throwable {
                MethodInvocation oldInvocation = invocation.get();
                invocation.set(mi);
                try {
                    // 调用下一个拦截器，即又回到 ReflectiveMethodInvocation 中
                    // 下一次，如果currentInterceptorIndex == 拦截器数量 - 1，则执行原始方法
                    return mi.proceed();
                }
                finally {
                    invocation.set(oldInvocation);
                }
            }
	        ```
        - AspectJAfterThrowingAdvice.invoke
            ```java
            // 接收 CglibMethodInvocation---> ReflectiveMethodInvocation 对象
            // 即: invoke(this) 中的 this
            @Override
            public Object invoke(MethodInvocation mi) throws Throwable {
                try {
                    // 调用下一个拦截器，即又回到 ReflectiveMethodInvocation 中
                    // 下一次，如果currentInterceptorIndex == 拦截器数量 - 1，则执行原始方法
                    return mi.proceed();
                }
                catch (Throwable ex) {
                    if (shouldInvokeOnThrowing(ex)) {
                        // 捕获异常，如果发生异常，则调用AOP配置的方法
                        invokeAdviceMethod(getJoinPointMatch(), null, ex);
                    }
                    throw ex;
                }
            }
	        ```
        - AfterReturningAdviceInterceptor.invoke
            ```java
            // 接收 CglibMethodInvocation---> ReflectiveMethodInvocation 对象
            // 即: invoke(this) 中的 this
            @Override
            public Object invoke(MethodInvocation mi) throws Throwable {
                // 调用下一个拦截器，即又回到 ReflectiveMethodInvocation 中
                // 下一次，如果currentInterceptorIndex == 拦截器数量 - 1，则执行原始方法
                Object retVal = mi.proceed();
                // 执行后置处理
                this.advice.afterReturning(retVal, mi.getMethod(), mi.getArguments(), mi.getThis());
                return retVal;
            }
    	    ```
        - AspectJAfterAdvice.invoke
            ```java
            // 接收 CglibMethodInvocation---> ReflectiveMethodInvocation 对象
            // 即: invoke(this) 中的 this	
    		@Override
        	public Object invoke(MethodInvocation mi) throws Throwable {
        		try {
        			// 调用下一个拦截器，即又回到 ReflectiveMethodInvocation 中
                    // 下一次，如果currentInterceptorIndex == 拦截器数量 - 1，则执行原始方法
                    // 如果执行有问题会直接抛出，到AfterThrowing
                    return mi.proceed();
        		}
        		finally {
        			invokeAdviceMethod(getJoinPointMatch(), null, null);
        		}
        	}
            ```
        - MethodBeforeAdviceInterceptor.invoke
            ```java
            // 接收 CglibMethodInvocation---> ReflectiveMethodInvocation 对象
            // 即: invoke(this) 中的 this
            @Override
            public Object invoke(MethodInvocation mi) throws Throwable {
                // 前置处理器在继续递归之前，会将前置处理执行完成
                this.advice.before(mi.getMethod(), mi.getArguments(), mi.getThis());
                // 调用下一个拦截器，即又回到 ReflectiveMethodInvocation 中
                // 下一次，如果currentInterceptorIndex == 拦截器数量 - 1，则执行原始方法
                return mi.proceed();
            }
            ```

# AOP总结
- `@EnableAspectAutoProxy`，开启AOP功能
- `@EnableAspectJAutoProxy` 向容器中注册了一个 `AnnotationAwareAspectJAutoProxyCreator`，这个bean实现了aop的主要功能
    - 这本对象本身是一个 spring 的后置处理器
    - 在 refresh 内的 registerBeanPostProcessors 中进行注册
- bean的创建流程
    - 在 refresh 的 finishBeanFactoryInitialization 初始化业务逻辑组件 + 切面组件
    - `AnnotationAwareAspectJAutoProxyCreator` 会拦截业务组件的创建过程
        - 组件创建后，会执行这个后置处理器，在 postProcessAfterInitalization 中执行 `wrapIfNecessary`，判断组件是否需要增强
        - 判断的方式是匹配 aspectJ 中的 execution 内的匹配条件
        - 如果需要增强
            - 将切面类中的通知方法，全部包装成增强器 Advisor
            - 给业务逻辑组件创建一个代理对象，默认是cglib
- 执行目标方法
    - 由代理对象执行目标方法
    - CglibAopProxy.intercept()
        - 得到目标方法的拦截器链，即增强器包装成的拦截器 MethodInterceptor
            - 需要给拦截器排序
        - 利用链式机制，依次执行每个拦截器
        - 整体顺序是倒序的
            - 5种拦截器类型及其顺序
                1. ExposeInvocationInterceptor
                2. AspectJAfterThrowingAdvice，异常处理
                3. AfterReturningAdviceInterceptor，返回处理
                4. AspectJAfterAdvice，后置处理
                5. 原始方法
                6. MethodBeforeAdviceInterceptor，前置处理
            - 正常执行: 前置处理-->原始方法-->后置处理-->返回处理
            - 发生异常: 前置处理-->原始方法-->后置处理-->异常处理
                - 但是后置处理会直接抛出异常 ?????

