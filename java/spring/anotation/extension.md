- 参考
    - https://www.bilibili.com/video/BV1gW411W7wy

# Spring扩展原理
## BeanPostProcessor
- bean 的后置处理器，bean对象初始化赋值前后进行拦截

## BeanFactoryPostProcessor
- BeanFactory 的后置处理器
- 执行时间
    - 在BeanFactory标准初始化之后调用
        - 所有bean定义**已经被加载**到beanFactory中
        - 但是bean的实例还未创建
- 执行过程
    1. ioc容器创建
    2. refresh
    3. invokeBeanFactoryPostProcessors(beanFactory);
        - 如何找到所有的 BeanFactoryPostProcessor，并执行其方法
            1. 直接在BeanFactory中找到所有类型是BeanFactoryProcessorPostProcessor的组件，并执行其方法
            2. 会在初始化其他组件之前执行

- 如何找到所有的 BeanFactoryPostProcessor 并执行？
    - 在 BeanFactory 中找到所有类型是 BeanFactoryPostProcessor的组件，并执行

- 可以用来修改 beanFactory 中的数据

- 示例
    ```java
    // 必须设置Component，加入容器
    @Component
    public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException{
            // 可以获取bean定义信息
            beanFactory.getBeanDefinitionCount();
        }
    }
    ```

## BeanDefinitionRegistryPostProcessor

- BeanFactoryPostProcessor 的子接口

- BeanDefinitionRegistry 是Bean定义信息的保存中心。BeanFactory 按照 BeanDefinitionRegistry 内保存的每一个 bean 定义信息创建 bean 实例

- 可以向容器中额外添加某些组件

- 执行时间
    - 在所有bean定义信息**将要被加载**，bean实例还未创建时
        - 和 BeanFactoryPostProcessor 的执行时机不同
- 优先于 BeanFactoryPostProcessor 执行
- 可以用于向容器中再添加一些组件
- 执行过程
    1. ioc容器创建
    2. refresh
    3. invokeBeanFactoryPostProcessors(beanFactory);
    4. 从 beanFactory 中获取所有 BeanDefinitionRegistryPostProcessor 的组件
    5. PostProcessorRegistrationDelegate.invokeBeanDefinitionRegistryPostProcessors，遍历所有组件，并调用 `postProcessBeanDefinitionRegistry` 方法
    6. 再执行 PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors，遍历所有组件，并调用 `postProcessBeanFactory` 方法

- 示例
    ```java
    @Component
    public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor{
        // 2. 再执行这个方法
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        }

        // 1. 先执行这个方法
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException{
            // RootBeanDefinition bd = new RootBeanDefinition(xxx.class);
            AbstractBeanDefinition bd = BeanDefinitionBuilder.rootBeanDefinition(xxx.class).getBeanDefinition();
            registry.registerBeanDefinition("xxx", bd)
        }
    }
    ```

# ApplicationListener
## ApplicationListener的基本使用
- 提供了基于事件驱动开发的功能
- 通过监听容器中的一些发布事件，当这些事件发生时，会触发监听的回调函数
- 监听 ApplicationEvent 及其子事件
- 当容器中发布事件后，触发方法

- 使用步骤
    - 写一个监听器，来监听某个事件 ApplicationEvent 及其子类
    - 把监听器加入到容器
    - 当容器中有相关事件发布，就能监听该事件

- 事件
    - ContextRefreshedEvent, 容器已经加载完所有的 bean，会发布该事件
    - ContextClosedEvent，容器关闭，会发布该事件
    - 如何自己发布事件？
        ```java
        @Component
        public class MyApplicationListener implements ApplicationListener<ApplicationEvent>{
            public void onApplicationEvent(ApplicationEvent event){
                System.out.println("event = " + event);
            }
        }

        public class My08Config{
            public static void main(String[] args){
                AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(My08Config.class);
                // 发布事件
                context.publishEvent(new ApplicationEvent("publish self event"){});
                context.close();
            }
        }
        ```

## ApplicationListener的原理
- ContextRefreshedEvent 的触发
    - fresh
    - AbstractApplicationContext.finishRefresh
    - 创建 ContextRefreshedEvent 对象
    - 事件发布流程
        - AbstractApplicationContext.publishEvent, 发布事件
            - 获取事件的多播器(派发器) getApplicationEventMulticaster()
                - 发送给多个监听器，让它们同时感知
            - 派发事件 getApplicationEventMulticaster().multicastEvent(applicationEvent, eventType);
            - 获取到所有的监听器，并派发事件
                - 如果是异步模式，如果有 Executor，可以支持使用 executor，进行异步派发 ????
                - 否则同步的执行 invokeListener(listener, event);
                    ```java
                    @Override
                    public void multicastEvent(final ApplicationEvent event, @Nullable ResolvableType eventType) {
                        ResolvableType type = (eventType != null ? eventType : resolveDefaultEventType(event));
                        Executor executor = getTaskExecutor();
                        for (ApplicationListener<?> listener : getApplicationListeners(event, type)) {
                            if (executor != null) {
                                executor.execute(() -> invokeListener(listener, event));
                            }
                            else {
                                invokeListener(listener, event);
                            }
                        }
                    }
                    ```
            - 调用 listener 的 onApplicationEvent

- 多播器的创建
    - refresh
    - initApplicationEventMulticaster，如果不存在则创建一个 SimpleApplicationEventMulticaster 对象
        ```java
        protected void initApplicationEventMulticaster() {
            ConfigurableListableBeanFactory beanFactory = getBeanFactory();
            // 从 beanFactory 查找是否存在名字是 applicationEventMulticaster 的组件
            if (beanFactory.containsLocalBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME)) {
                // 如果有，则获取组件
                this.applicationEventMulticaster =
                        beanFactory.getBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
                if (logger.isTraceEnabled()) {
                    logger.trace("Using ApplicationEventMulticaster [" + this.applicationEventMulticaster + "]");
                }
            }
            else {
                // 如果没有，创建一个 SimpleApplicationEventMulticaster 对象
                this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
                // 添加到容器中，在其他组件要派发事件时，自动注入这个组件
                beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
                if (logger.isTraceEnabled()) {
                    logger.trace("No '" + APPLICATION_EVENT_MULTICASTER_BEAN_NAME + "' bean, using " +
                            "[" + this.applicationEventMulticaster.getClass().getSimpleName() + "]");
                }
            }
        }
        ```

- 有哪些listener
    - refresh
    - registerListeners
        ```java
        // 从容器中获取所有监听器
        String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
        // 将监听器注册到 applicationeventmulticaster 中
		for (String listenerBeanName : listenerBeanNames) {
			getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
		}
        ```

## @EventListener
- 示例
    - /java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/extension/ex03/Ex03Service.java
    ```java
    @Service
    public class Ex03Service {
        @EventListener(classes = {ApplicationEvent.class})
        public void listen(ApplicationEvent event){
            System.out.println("Ex03Service listener event = " + event);
        }
    }
    ```
- 执行原理
    - EventListenerMethodProcessor, 通过该处理器来解析 @EventListener 注解 
        - 接口 SmartInitializingSingleton 的实现类
    - refresh
    - finishBeanFactoryInitialization(beanFactory); 初始化单实例bean
    - beanFactory.preInstantiateSingletons(); 实例化bean
        - 先创建所有的bean，getBean
        - 重新获取所有的单实例bean，判断是不是 SmartInitializingSingleton 接口的实现类，如果是则调用 afterSingletonsInstantiated 方法
            ```java
            for (String beanName : beanNames) {
                Object singletonInstance = getSingleton(beanName);
                if (singletonInstance instanceof SmartInitializingSingleton) {
                    final SmartInitializingSingleton smartSingleton = (SmartInitializingSingleton) singletonInstance;
                    if (System.getSecurityManager() != null) {
                        AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                            smartSingleton.afterSingletonsInstantiated();
                            return null;
                        }, getAccessControlContext());
                    }
                    else {
                        smartSingleton.afterSingletonsInstantiated();
                    }
                }
            }
            ```