<span id="catalog"></span>

### 目录
- 容器注解
    - [Configuration](#Configuration)
    - [Bean](#Bean)
    - [ComponentScan](#ComponentScan)
    - [FilterType--指定扫描过滤规则](#FilterType--指定扫描过滤规则)
    - [TypeFilter自定义包扫描过滤规则](#TypeFilter自定义包扫描过滤规则)
    - [Scope定义bean的作用域](#Scope定义bean的作用域)
- [](#)

# 容器注解
## Configuration
[top](#catalog)
- @Configuration 的功能
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

## Bean
[top](#catalog)
- `@Bean(id="指定beanid")`的功能
    - 用于向容器中注册一个Bean
    - 用于装饰方法
    - Bean的类型 = 返回值的类型
    - Bean的默认id = 方法名
- 示例
    - 配置类
        - 参考代码
            - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/bean/BeanConfig.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/bean/BeanConfig.java)
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
            - [/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/bean/BeanConfigTest.java](/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/bean/BeanConfigTest.java)
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

## ComponentScan
[top](#catalog)
- `@ComponentScan(value="指定要扫描的包")`
    - 配置扫描包的策略
    - 可以同时配置多个
- 配置方法
    - `@ComponentScan` 的配置方法
        ```java
        @ComponentScan(
            value="指定要扫描的包路径",
            excludeFilters={@ComponentScan.Filter, ....}, // 指定需要排除哪些包
            includeFilters = {@ComponentScan.Filter, ...},  // 只扫描指定的类型
            // 如果使用 includeFilters，则useDefaultFilters 必须为 false
            // 关闭默认de过滤规则，按照指定的过滤规则扫描
            useDefaultFilters = false  
        )
        class xxx{...}
        ```
    - `@ComponentScan.Filter` 过滤器配置方法
        - 参考: [FilterType--指定扫描过滤规则](#FilterType--指定扫描过滤规则)
        
- 示例
    - 参考代码
        - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/componentScan/ComponentScanConfig.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/componentScan/ComponentScanConfig.java)
        - [/Users/liujinsuo/myGit/memobook/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/componentScan/ComponentScanConfig02.java](/Users/liujinsuo/myGit/memobook/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/componentScan/ComponentScanConfig02.java)
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
        ```java
        @Configuration // 通过注解声明一个个配置类
        @ComponentScan(value="com.ljs.learn.myspringannotation.componentScan", includeFilters = { // 只扫描指定的类型
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
        }, useDefaultFilters = false)
        public class ComponentScanConfig02 {
        }
        ```

## FilterType--指定扫描过滤规则
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

## TypeFilter自定义包扫描过滤规则
[top](#catalog)
- 自定义 TypeFilter 接口的实现类
    - 参考代码
        - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/componentScan/filter/MyTypeFilter.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/componentScan/filter/MyTypeFilter.java)
    - 实现方式
        - 实现 TypeFilter 接口的 match 方法
        - match返回 true 则当前扫描的类过滤成功，false 则过滤失败
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
        - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/componentScan/filter/TypeFilterConfig.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/componentScan/filter/TypeFilterConfig.java)
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

## Scope定义bean的作用域
[top](#catalog)
- 每个 bean 默认的 scope 为 singleton 单例的
- scope 的可选值
    - 2种基本的选择
        - `ConfigurableBeanFactory.SCOPE_PROTOTYPE = "prototype"`，多实例
        - `ConfigurableBeanFactory.SCOPE_SINGLETON = "singleton"`，单实例，默认值

    - 在web环境下，即：request 和 session。很少会使用，一般会手动放入 请求域 或 session域
        - WebApplicationContext.SCOPE_REQUEST，同一次请求创建一个实例
        - WebApplicationContext.SCOPE_SESSION，同一个session创建一个实例

- `singleton` 对象的创建、保存、获取
    - 在ioc容器启动会主动调用创建方法，保存到容器中
    - 以后每次获取就是直接从容器中获取，相当于通过`map.get()`获取对象

- `prototype` 对象的创建、保存、获取
    - ioc容器启动时，不会主动调用创建方法
    - 在从容器中获取对象时，才会调用创建方法。**每次调用都会创建**

- 示例
    - scope设置
        - 参考代码
            - [/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/scope/ScopeConfig.java](/java/mylearn/myspring-annotation/src/main/java/com/ljs/learn/myspringannotation/scope/ScopeConfig.java)
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
            - [/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/scope/ScopConfigTest.java](/java/mylearn/myspring-annotation/src/test/java/com/ljs/learn/myspringannotation/scope/ScopConfigTest.java)
        - 测试内容
            ```java
            public class ScopConfigTest {
                // 单例scope测试
                @Test
                public void test01(){
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
                public void test02(){
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