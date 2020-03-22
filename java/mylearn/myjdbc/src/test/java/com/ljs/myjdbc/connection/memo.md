- 5中创建数据库连接方法测试
    1. 直接使用MySql手动创建`Driver`和`Connection`
    2. 通过反射创建`Driver`，再创建`Connection`
    3. 通过`DriverManager`来创建
    4. 对`DriverManager`的方式进行简化
        - 因为驱动类加载时，通过静态代码做了同样的引入操作
    5. 将部分配置信息移动到配置文件中，通过加载配置文件来实现