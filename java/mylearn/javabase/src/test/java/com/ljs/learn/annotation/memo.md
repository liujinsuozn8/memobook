注解测试
- annotation/base
    - 基本注解测试
    - DocAnnotationTest.java
        - 文档说明注解测试
    - JDKBaseAnnotationTest.java
        - JDK内置的三个基本注解测试
    
- annotation/customize
    - 自定义注解
    - Myannotation.java,TestClass.java
        - 类型注解测试：@Target({ElementType.TYPE_PARAMETER})
    - Myannotation2.java,TestClass2.java
        - 类型注解测试：@Target({ElementType.TYPE_USER})