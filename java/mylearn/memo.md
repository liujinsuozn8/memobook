- 参考
    - https://www.bilibili.com/video/av71110355 
    - https://blog.kuangstudy.com/index.php
    - 官方说明文档地址：https://docs.spring.io/spring/docs/
        - 直接在官网可能找不到入口了

<span id="catalog"></span>


   
   
- xml配置文件
    - 文件需要保存在`main/resources`目录下，文件名任意，使用时作为参数注入到上下文对象中
    - 官方配置参考
        - https://docs.spring.io/spring/docs/5.2.4.RELEASE/spring-framework-reference/core.html#beans-factory-metadata
- Spring对于类的要求：在Spring中，所有对象都称为bean，并且bean需要提供无参构造器
- 从Spring中获取类时，可以通过Spring的上下文对象来获取
    - `ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");`
