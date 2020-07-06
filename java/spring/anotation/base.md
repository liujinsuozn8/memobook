<span id="catalog"></span>

### 目录
- 组件注册注解
    - [Configuration--创建配置类](#Configuration--创建配置类)
    - [向容器中注册组件的几种方法](#向容器中注册组件的几种方法)
    - [Bean](#Bean)
    - [ComponentScan--包扫描](#ComponentScan--包扫描)
        - [ComponentScan的基本使用](#ComponentScan的基本使用)
        - [FilterType--指定扫描过滤规则](#FilterType--指定扫描过滤规则)
        - [TypeFilter--自定义包扫描过滤规则](#TypeFilter--自定义包扫描过滤规则)
    - [Scope--定义bean的作用域](#Scope--定义bean的作用域)
    - [Lazy--懒加载](#Lazy--懒加载)
    - [Conditional--按条件注册bean](#Conditional--按条件注册bean)
    - Import--向容器中导入组件
        - [Import--快速导入](#Import--快速导入)
        - [ImportSelector接口--返回组件数组](#ImportSelector接口--返回组件数组)
        - [ImportBeanDefinitionRegistrar接口--手动注册某些组件](#ImportBeanDefinitionRegistrar接口--手动注册某些组件)
        - [Import示例](#Import示例)
    - [FactoryBean接口--工厂Bean](#FactoryBean接口--工厂Bean)
- [](#)
- [](#)


# 组件注册注解
## Configuration--创建配置类
[top](#catalog)
- `@Configuration` 的功能
    - 用于将一个普通类标识为Spring配置类

- 源码分析
    - @Configuration 使用 `@Component`装饰，所以自身也是一个 Compnent
        - 在Spring启动后，**容器中也会包含** @Configuration 标识的配置类
        ```java
        @Target(ElementType.TYPE)
        @Retention(RetentionPolicy.RUNTIME)
        @Documented
        @Component
        public @interface Configuration {
        
            @AliasFor(annotation = Component.class)
            String value() default "";
        
            boolean proxyBeanMethods() default true;
        
        }
        ```

## 向容器中注册组件的几种方法
[top](#catalog)
1. 配置类的包扫描注解 `ComponentScan` + 组件标识注解 `@Component/@Service/@Controller/@Repository`
2. `@Bean`，导入第三方包中的组件
3. `@Conditional`，按条件注册bean
4. `Import`，向容器中快速导入一个组件
    - `@Import(组件的类对象)`，容器会自动注册组件，id默认是**全类名**
    - `ImportSelector` 接口，返回需要导入的组件的**全类名数组**
        - 方法可以返回一个空数组，但是不能返回null，会产生空数组异常
    - `ImportBeanDefinitionRegistrar` 接口
        - 对所有需要添加到容器中的bean，调用`BeanDefinitionRegistry.registerBeanDefinition()` 手动注册
5. 使用Spring提供的FactoryBean，即工厂bean
    - <label style='color:red'>与其他框架整合时</label>，该接口使用的非常多

## Bean
[top](#catalog)
- `@Bean(id="指定beanid")` 的功能
    - 用于向容器中注册一个Bean
    - 用于装饰方法
    - Bean的类型 = 返回值的类型
    - Bean的默认id = 方法名
- 示例
    - 配置类
        - 参考代码
            - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/bean/BeanConfig.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/bean/BeanConfig.java)
        - 代码内容
            ```java
            // 配置类 == 配置文件
            @Configuration // 通过注解声明一个个配置类
            public class BeanConfig {
                @Bean
                public Person person(){
                    return new Person("personA", 22);
                }
            
                @Bean
                public Person person01(){
                    return new Person("personB", 23);
                }
            
                // 在Bean注解中，手动指定Bean的别名
                @Bean("personAlias")
                public Person person02(){
                    return new Person("personC", 24);
                }
            }
            ```
    - 测试类
        - 参考代码
            - [/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/bean/BeanConfigTest.java](/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/bean/BeanConfigTest.java)
        - 测试内容
            ```java
            @Test
            public void test01(){
                // 1. 创建容器
                ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        
                // 2. 根据配置类的方法名，或者Bean中的别名获取bean实例对象
                Person person = (Person) context.getBean("person");
                System.out.println(person);
        
                Person personAlias = (Person) context.getBean("personAlias");
                System.out.println(personAlias);
        
                // 3. 通过类型找到IOC容器内部的Bean名
                String[] beanNamesForType = context.getBeanNamesForType(Person.class);
                for (String s : beanNamesForType) {
                    System.out.println(s);
                }
                // 配置类中有 3个 Bean的类型是Person，所以输出：
                // person
                // person01
                // personAlias
            }
            ```

## ComponentScan--包扫描
### ComponentScan的基本使用
[top](#catalog)
- `@ComponentScan(value="指定要扫描的包")` 的功能
    - 配置扫描包的策略
    - 可以同时配置多个

- 具体配置方法
    - `@ComponentScan` 的配置方法
        ```java
        @ComponentScan(
            value="指定要扫描的包路径",
            excludeFilters={@ComponentScan.Filter, ....}, // 指定需要排除哪些包
            includeFilters = {@ComponentScan.Filter, ...},  // 只扫描指定的类型
            // 如果使用 includeFilters，则useDefaultFilters 必须为 false
            // 关闭默认的过滤规则，按照指定的过滤规则扫描
            useDefaultFilters = false  
        )
        class xxx{...}
        ```
    - `@ComponentScan.Filter` 过滤器配置方法
        - 参考: [FilterType--指定扫描过滤规则](#FilterType--指定扫描过滤规则)
        
- 示例
    - exclude示例
        - 实现内容
            - 参考代码
                - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/componentScan/ComponentScanConfig.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/componentScan/ComponentScanConfig.java)
            - 代码内容
                ```java
                @Configuration // 通过注解声明一个个配置类
                // 指定扫描时按照什么规则排除组件
                @ComponentScan(value="com.ljs.learn.myspringannotation.componentScan.layers", excludeFilters = {
                        // 扫描时排除包含：Controller、Service 注解的类
                        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
                })
                public class ComponentScanConfig {
                }
                ```
        - 测试内容
            - 参考代码
                - [/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/componentScan/ComponentScanConfigTest.java](/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/componentScan/ComponentScanConfigTest.java)
            - 测试内容
                ```java
                @Test
                public void tesConfigExclude(){
                    ApplicationContext context = new AnnotationConfigApplicationContext(ComponentScanConfig.class);
                    String[] beanNames = context.getBeanDefinitionNames();
                    for (String beanName : beanNames) {
                        System.out.println(beanName);
                    }

                    // 输出结果中会包含DemoConfig自身，因为 @Configuration 本身就是一个 @Component

                    // 输出:
                    // org.springframework.context.annotation.internalConfigurationAnnotationProcessor
                    // org.springframework.context.annotation.internalAutowiredAnnotationProcessor
                    // org.springframework.context.event.internalEventListenerProcessor
                    // org.springframework.context.event.internalEventListenerFactory
                    // componentScanConfig
                    // demoDao
                }
                ```
    - include示例
        - 实现内容
            - 参考代码
                - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/componentScan/ComponentScanConfig02.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/componentScan/ComponentScanConfig02.java)
            - 代码内容
                ```java
                @Configuration // 通过注解声明一个个配置类
                @ComponentScan(value="com.ljs.learn.myspringannotation.componentScan", includeFilters = { // 只扫描指定的类型
                        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
                }, useDefaultFilters = false)
                public class ComponentScanConfig02 {
                }
                ```
        - 测试内容
            - 参考代码
                - [/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/componentScan/ComponentScanConfigTest.java](/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/componentScan/ComponentScanConfigTest.java)
            - 测试代码
                ```java
                @Test
                public void tesConfigInclude(){
                    ApplicationContext context = new AnnotationConfigApplicationContext(ComponentScanConfig02.class);
                    String[] beanNames = context.getBeanDefinitionNames();
                    for (String beanName : beanNames) {
                        System.out.println(beanName);
                    }

                    // 输出结果中会包含DemoConfig自身，因为 @Configuration 本身就是一个 @Component

                    // 输出:
                    // org.springframework.context.annotation.internalConfigurationAnnotationProcessor
                    // org.springframework.context.annotation.internalAutowiredAnnotationProcessor
                    // org.springframework.context.event.internalEventListenerProcessor
                    // org.springframework.context.event.internalEventListenerFactory
                    // componentScanConfig02
                    // demoController
                    // demoService
                }
                ```
### FilterType--指定扫描过滤规则
[top](#catalog)
- FilterType 源码
    ```java
    public enum FilterType {
    	ANNOTATION,         // 按照注解过滤
    	ASSIGNABLE_TYPE,    // 按照给定类型过滤
    	ASPECTJ,            // 根据aspectj表达式过滤
    	REGEX,              // 根据正则表达式过滤
    	CUSTOM              // 自定义规则
    }
    ```

- FilterType.ANNOTATION 按照注解过滤，**常用**
    ```java
    @ComponentScan.Filter(
        type = FilterType.ANNOTATION,
        // 过滤目标为包含 Controller 和 Service 注解的类
        classes = {Controller.class, Service.class}
    )
    class xxx{}
    ```
- FilterType.ASSIGNABLE_TYPE 按照给定类型过滤，**常用**
    ```java
    @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        // 过滤类型为指定类型的组件，包括子类和实现类
        lasses={DemoService.class}
    )
    class xxx{}
    ```

- FilterType.ASPECTJ，根据aspectj表达式来过滤，**不常用**
- FilterType.REGEX 根据正则表达式过滤，**不常用**
- FilterType.CUSTOM，自定义规则
    - 参考：[TypeFilter自定义包扫描过滤规则](#TypeFilter自定义包扫描过滤规则)

### TypeFilter--自定义包扫描过滤规则
[top](#catalog)
- 自定义规则的实现方式
    - 实现 TypeFilter 接口的 match 方法
    - `match函数` 返回 `true` 则当前扫描的类过滤成功，返回 `false` 则过滤失败

- 自定义 TypeFilter 接口的实现类
    - 参考代码
        - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/componentScan/filter/MyTypeFilter.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/componentScan/filter/MyTypeFilter.java)
    - TypeFilter 接口的实现类
        ```java
        public class MyTypeFilter implements TypeFilter {
            // 返回值：true=匹配成功，false=匹配失败
            // MetadataReader 当前正在扫描的类的信息
            // MetadataReaderFactory 可以获取到其他任何类的信息
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                // 1. 获取当前类注解的信息
                AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        
                // 2. 获取当前正在扫描的类的信息
                ClassMetadata classMetadata = metadataReader.getClassMetadata();
        
                // 3. 获取当前类的资源信息 (如: 路径)
                Resource resource = metadataReader.getResource();
        
                // 4. 获取当前正在扫描的类的类名（全类名）
                String className = classMetadata.getClassName();
                // 输出正在扫描的类名
                System.out.println("-----" + className);
        
                // 5. 设置过滤规则
                if (className.contains("er")){
                    return true;
                }
        
                return false;
            }
        }
        ```
- 使用自定义规则
    - 参考代码
        - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/componentScan/filter/TypeFilterConfig.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/componentScan/filter/TypeFilterConfig.java)
    - 代码内容        
        ```java
        @Configuration
        @ComponentScan(
            value = "com.ljs.learn.myspringannotation.componentScan.layers",
            includeFilters = {
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes={MyTypeFilter.class})
            },
            useDefaultFilters = false
        )
        public class TypeFilterConfig {}
        ```

- 测试内容
    - 参考代码
        - [/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/componentScan/filter/TypeFilterConfigTest.java](/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/componentScan/filter/TypeFilterConfigTest.java)
    - 测试代码
        ```java
        @Test
        public void testCustomTypeFilter(){
            ApplicationContext context = new AnnotationConfigApplicationContext(TypeFilterConfig.class);
            String[] names = context.getBeanDefinitionNames();
            for (String name : names) {
                System.out.println("name = " + name);
            }

            // 输出:
            // -----com.ljs.learn.myspringannotation.componentScan.layers.controller.DemoController
            // -----com.ljs.learn.myspringannotation.componentScan.layers.dao.DemoDao
            // -----com.ljs.learn.myspringannotation.componentScan.layers.service.DemoService
            // name = org.springframework.context.annotation.internalConfigurationAnnotationProcessor
            // name = org.springframework.context.annotation.internalAutowiredAnnotationProcessor
            // name = org.springframework.context.event.internalEventListenerProcessor
            // name = org.springframework.context.event.internalEventListenerFactory
            // name = typeFilterConfig   <<<<< 当前配置类
            // name = demoController
            // name = demoDao
            // name = demoService
        }
        ```

## Scope--定义bean的作用域
[top](#catalog)
- 每个 bean 默认的 scope 为 singleton 单例的
- scope 的可选值
    - 2种基本的选择
        - `ConfigurableBeanFactory.SCOPE_PROTOTYPE = "prototype"`，多实例
        - `ConfigurableBeanFactory.SCOPE_SINGLETON = "singleton"`，单实例，**默认值**

    - 在web环境下，即：request 和 session。很少会使用，一般会手动放入 请求域 或 session域
        - WebApplicationContext.SCOPE_REQUEST，同一次请求创建一个实例
        - WebApplicationContext.SCOPE_SESSION，同一个session创建一个实例

- `singleton` 对象的创建、保存、获取
    - 在ioc容器启动时，会主动调用创建方法，保存到容器中
    - 以后每次获取就是直接从容器中获取，相当于通过`map.get()`获取对象

- `prototype` 对象的创建、保存、获取
    - ioc容器启动时，不会主动调用创建方法
    - 在从容器中获取对象时，才会调用创建方法。**每次调用都会创建新对象**

- 示例
    - scope设置
        - 参考代码
            - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/scope/ScopeConfig.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/scope/ScopeConfig.java)
        - 代码内容
            ```java
            @Configuration
            @ComponentScan(
                value = "com.ljs.learn.myspringannotation.scope"
            )
            public class ScopeConfig {
                // 测试 prototype 的调用次数
                private int prototype_count = 0;
                
                // 默认使用单例
                @Bean
                public Person person(){
                    System.out.println("singleton person create");
                    return new Person("personA", 22);
                }
            
                // 使用多实例
                @Scope("prototype")
                @Bean
                public Person person02(){
            
                    System.out.println("prototype person create: " + ++prototype_count);
                    return new Person("personB", 33);
                }
            }
            ```
    - 测试内容
        - 参考代码
            - [/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/scope/ScopConfigTest.java](/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/scope/ScopConfigTest.java)
        - 测试内容
            ```java
            public class ScopConfigTest {
                // 单例scope测试
                @Test
                public void testSingleton(){
                    ApplicationContext context = new AnnotationConfigApplicationContext(ScopeConfig.class);
                    System.out.println("ioc 容器创建完成");
                    Person personA = (Person) context.getBean("person");
                    Person personB = (Person) context.getBean("person");
            
                    assert (personA == personB);
            
                    // 输出
                    // singleton person create
                    // ioc 容器创建完成
                }
            
                // 原型cope测试
                @Test
                public void testPrototype(){
                    ApplicationContext context = new AnnotationConfigApplicationContext(ScopeConfig.class);
                    System.out.println("ioc 容器创建完成");
                    Person personA = (Person) context.getBean("person02");
                    Person personB = (Person) context.getBean("person02");
            
                    assert (personA != personB);
            
                    // 输出
                    // singleton person create
                    // ioc 容器创建完成
                    // prototype person create: 1
                    // prototype person create: 2
                }
            }
            ```

## Lazy--懒加载
[top](#catalog)
- `@Lazy` 懒加载
    - 对于单例bean，默认在容器启动的时候创建对象
    - 通过懒加载，控制容器在启动时，不主动创建某个对象。在第一次获取bean的时候，再创建对象，并初始化
- 示例
    - 使用`@Lazy`
        - 参考代码
            - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/scope/ScopeConfig.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/scope/ScopeConfig.java)
        - 代码内容
            ```java
            @Configuration
            @ComponentScan(
                value = "com.ljs.learn.myspringannotation.scope"
            )
            public class ScopeConfig {
                // 使用懒加载
                @Lazy
                @Bean
                public Student student(){
                    System.out.println("create student");
                    return new Student("testStudent", 22);
                }
            }
            ```
    - 测试内容
        - 参考代码
            - [/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/scope/ScopConfigTest.java](/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/scope/ScopConfigTest.java)
        - 测试代码
            ```java
            // 懒加载测试
            @Test
            public void testLazy(){
                AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScopeConfig.class);
                System.out.println("ioc 容器创建完成");
                // 获取懒加载实例
                context.getBean("student");
                context.getBean("student");

                // 输出:
                // singleton person create
                // ioc 容器创建完成           <<<先创建容器
                // create student           <<<第一次获取实例时，创建对象
                // construct student, name=testStudent, age=22

                // 因为是单例的，所以只会在第一次获取时打印一次
            }
            ```

## Conditional--按条件注册bean
[top](#catalog)
- `@Conditional` 的功能
    - 按照一定的条件进行判断，满足条件时才注册bean
    
- `@Conditional` 可以修饰方法，也可以修饰类
    - 修饰类时，只有条件满足，类中配置的bean才能有效
    
- 注解设置方法
    - `@Conditional({Condition数组})`
    - 注解参数是一个Condition接口实现类的数组

- 自定义判断条件的接口 `Condition`
    - 源代码
        ```java
        @FunctionalInterface
        public interface Condition {
            /**
             * 返回 false时，不作为bean添加到容器中
             * @param context  判断条件可以使用的上下文环境
             * @param metadata 注释信息
             * @return
             */
            boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata);
        }
        ```
    - `matches()` 方法中， 可以通过`context`获得的对象
        ```java
        // 1. 获取ioc容器中使用的beanFactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
    
        // 2. 获取类加载器
        ClassLoader classLoader = context.getClassLoader();
    
        // 3。 获取当前的环境信息
        Environment envObj = context.getEnvironment();
    
        // 4. 获取bean定义的注册类
        // 可以判断容器中的bean的注册情况，也可以向容器中注册bean
        BeanDefinitionRegistry registry = context.getRegistry();
        ```

- 示例
    - 配置类
        - 参考代码
            - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/conditional/ConditionalConfig.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/conditional/ConditionalConfig.java)
        - 配置类代码内容
            ```java
            @Configuration
            public class ConditionalConfig {
                @Conditional({MacCondition.class})
                @Bean("mac")
                public Person person01(){
                    return new Person("aaa", 11);
                }
            
                @Conditional({WinCondition.class})
                @Bean("window")
                public Person person02(){
                    return new Person("bbb", 22);
                }
            
                @Conditional({LinuxCondition.class})
                @Bean("linux")
                public Person person03(){
                    return new Person("ccc", 33);
                }
             }
             ```
    - Condition实现类
        - 参考代码
            - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/conditional/LinuxCondition.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/conditional/LinuxCondition.java)
        - 代码内容
            ```java
            // 用于判断系统类型的条件
            public class LinuxCondition implements Condition {
                @Override
                public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
                    Environment envObj = context.getEnvironment();
                    String envStr = envObj.getProperty("os.name");
            
                    if (envStr.contains("Linux")){
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            ```
    - 测试内容
        - 参考代码
            - [/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/conditional/ConditionalConfigTest.java](/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/conditional/ConditionalConfigTest.java)
        - 测试代码
            ```java
            @Test
            public void testConditionalConfig(){
                AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConditionalConfig.class);
                ConfigurableEnvironment environment = context.getEnvironment();
        
                // 检查输入
                String envStr = environment.getProperty("os.name");
                System.out.println(envStr);
        
                // 输出 类型是 Person的类
                String[] names = context.getBeanNamesForType(Person.class);
                for (String name : names) {
                    System.out.println(name);
                }
        
            }
            ```

## Import--向容器中导入组件
### Import--快速导入
[top](#catalog)
- 组件id 默认是组件的**全类名**
- 需要组件具有**无参构造器**，否则会报错
- 可以导入一个，也可以导入多个，但是 `@Import` 注解只能用一次

### ImportSelector接口--返回组件数组
[top](#catalog)
- `ImportSelector` 接口，返回需要导入的组件的**全类名数组**
    - 方法可以返回一个空数组，但是不能返回null，会产生空数组异常
- 示例
    - 参考代码
        - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/importconfig/MyImportSelector.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/importconfig/MyImportSelector.java)
    - 实现代码
        ```java
        public class MyImportSelector implements ImportSelector {
            // AnnotationMetadata: 当前标注@Import注解的类的所有注解信息
            @Override
            public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        
                return new String[] {
                    "com.ljs.learn.myspringannotation.regist.importconfig.classes.ClassA",
                    "com.ljs.learn.myspringannotation.regist.importconfig.classes.ClassC",
                };
        
            }
        }
        ```

### ImportBeanDefinitionRegistrar接口--手动注册某些组件
[top](#catalog)
- 对所有需要添加到容器中的bean，调用BeanDefinitionRegistry.registerBeanDefinition 手动注册
- 示例
    - 参考代码
        - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/importconfig/MyImportBeanDefinitionRegistrar.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/importconfig/MyImportBeanDefinitionRegistrar.java)
    - 实现代码
        ```java
        public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
        
            /**
             * 对所有需要添加到容器中的bean，调用
             *  BeanDefinitionRegistry.registerBeanDefinition 手动注册
             * @param importingClassMetadata 当前类的注解信息
             * @param registry BeanDefinition 的注册类
             */
            @Override
            public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
                // 根据容器的已有的bean来添加其他的bean
                // 1. 判断容器中是否有红色和蓝色
                boolean hasRed = registry.containsBeanDefinition("com.ljs.learn.myspringannotation.regist.importconfig.color.ColorRed");
                boolean hasBlue = registry.containsBeanDefinition("com.ljs.learn.myspringannotation.regist.importconfig.color.ColorBlue");
        
                // 2. 如果容器中有红色和蓝色，则添加 RainBow
                if (hasRed && hasBlue){
                    // 指定bean的定义信息
                    RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
                    // 指定bean名，注册bean
                    registry.registerBeanDefinition("rainBow", beanDefinition);
                }
            }
        }
        ```

### Import示例
[top](#catalog)
- 示例内容
    - 参考代码
        - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/importconfig/ImportConfig.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/importconfig/ImportConfig.java)
    - 示例内容
        ```java
        @Configuration
        // @Import(Student.class) // 只导入一个组件
        // @Import({Student.class,Teacher.class}) // 导入多个组件
        // @Import({Student.class, Teacher.class, MyImportSelector.class}) // 导入多个组件
        @Import({Student.class, Teacher.class, MyImportSelector.class,
                ColorRed.class, ColorBlue.class, MyImportBeanDefinitionRegistrar.class}) // 导入多个组件 + 手动导入组件
        public class ImportConfig {
            @Bean
            public Person person(){
                return new Person("test", 33);
            }
        }
        ```
- 测试内容
    - 参考代码
        - [/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/importconfig/ImportConfigTest.java](/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/importconfig/ImportConfigTest.java)
    - 测试代码
        ```java
        @Test
        public void test01(){
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ImportConfig.class);
            String[] names = context.getBeanDefinitionNames();
            for (String name : names) {
                System.out.println(name);
            }

            // 输出
            // org.springframework.context.annotation.internalConfigurationAnnotationProcessor
            // org.springframework.context.annotation.internalAutowiredAnnotationProcessor
            // org.springframework.context.event.internalEventListenerProcessor
            // org.springframework.context.event.internalEventListenerFactory
            // importConfig
            // com.ljs.learn.myspringannotation.regist.importconfig.person.Student
            // com.ljs.learn.myspringannotation.regist.importconfig.person.Teacher
            // com.ljs.learn.myspringannotation.regist.importconfig.classes.ClassA  <<<<ImportSelector接口导入
            // com.ljs.learn.myspringannotation.regist.importconfig.classes.ClassC  <<<<ImportSelector接口导入
            // com.ljs.learn.myspringannotation.regist.importconfig.color.ColorRed
            // com.ljs.learn.myspringannotation.regist.importconfig.color.ColorBlue
            // person
            // rainBow <<<< ImportBeanDefinitionRegistrar接口手动导入
        }
        ```

## FactoryBean接口--工厂Bean
[top](#catalog)
- 普通bean与工厂bean的区别
    - 普通bean
        - 由容器调用无参构造器，创建对象后注册到容器中，然后从容器中直接获取实例对象
    - 工厂Bean
        - 是一个工厂，也是一个接口
        - 注册到容器后，也是有容器负责创建并注册工厂bean对象自身
        - 通过ID获取工厂bean时，会调用 `getObject()` 方法，将方法返回值作为结果返回给使用者
- FactoryBean接口源码
    ```java
    public interface FactoryBean<T> {
        String OBJECT_TYPE_ATTRIBUTE = "factoryBeanObjectType";
    
        // 通过ID获取工厂bean时，主动调用该方法，并将返回值作为结果返回给使用者
        @Nullable
        T getObject() throws Exception;
    
        // 返回对象类型
        @Nullable
        Class<?> getObjectType();
    
        // 控制对象是否是单例的
        default boolean isSingleton() {
            return true;
        }
    }
    ```

- FactoryBean的使用方法
    1. 创建接口实现类
    2. 将FactoryBean接口实现类注册到配置类中
    3. 通过`id`获取时，会调用 `getObject()` 方法，并将其返回值作为 `bean` 返回
    4. 通过`&id`获取时，会直接返回 `FactoryBean` 接口实例对象本身

- 示例
    - 实现内容
        - 参考代码
            - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/factorybean/color/ColorFactoryBean.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/factorybean/color/ColorFactoryBean.java)
            - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/factorybean/FactoryBeanConfig.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/regist/factorybean/FactoryBeanConfig.java)

        - 工厂bean实现类
            ```java
            public class ColorFactoryBean implements FactoryBean<Color> {
            
                // 返回一个Color对象
                @Override
                public Color getObject() throws Exception {
                    return new Color("red");
                }
            
                // 返回对象类型
                @Override
                public Class<?> getObjectType() {
                    return Color.class;
                }
            
                // 设置为单例
                @Override
                public boolean isSingleton() {
                    // return false;
                    return true;
                }
            }
            ```
        - 配置类
            ```java
            @Configuration
            public class FactoryBeanConfig {
                /** 注册到容器中
                 * 虽然注册到容器的是 `ColorFactoryBean`，但是通过 `colorFactoryBean` 获取对象时，
                 * 会调用 `getObject()` 方，返回的 Color 对象
                 */
                @Bean
                public ColorFactoryBean colorFactoryBean(){
                    return new ColorFactoryBean();
                }
            }
            ```
    - 测试内容
        - 参考代码
            - [/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/factorybean/FactoryBeanConfigTest.java](/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/regist/factorybean/FactoryBeanConfigTest.java)
        - 测试代码
            ```java
            // 测试通过工厂类获取bean
            @Test
            public void colorFactoryBean() {
                AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FactoryBeanConfig.class);
                String[] beanDefinitionNames = context.getBeanDefinitionNames();
                for (String name : beanDefinitionNames) {
                    System.out.println(name);
                }
                // 输出:
                // factoryBeanConfig
                // colorFactoryBean
                // 不会输出color
        
                // 通过 colorFactoryBean 获取实例时，返回的是Color的实例对象
                Object color1 = context.getBean("colorFactoryBean");
                assert color1.getClass() == Color.class;
        
                // 测试单实例设置
                Object color2 = context.getBean("colorFactoryBean");
                assert color1 == color2;
            }
        
            // 测试获取工厂bean自己
            public void testGetFactoryBeanSelf(){
                AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ColorFactoryBean.class);
        
                // 通过 & 获取工厂bean自身
                Object bean = context.getBean("&colorFactoryBean");
        
                assert bean.getClass() == ColorFactoryBean.class;
            }
            ```
       
[top](#catalog)