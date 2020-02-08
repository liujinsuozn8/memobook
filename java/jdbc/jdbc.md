- 内容整理自：
    - `https://www.bilibili.com/video/av67955358`

<span id="catalog"></span>

### 目录
- [JDBC概述](#JDBC概述)
    - [JDBC的概念](#JDBC的概念)
    - [JDBC接口的两个层次](#JDBC接口的两个层次)
    - [JDBC程序编写步骤](#JDBC程序编写步骤)
    - [JDBC的编程方式](#JDBC的编程方式)
    - [JDBC对结果集的处理](#JDBC对结果集的处理)
- [Java数据类型和数据库数据类型的转换](#Java数据类型和数据库数据类型的转换)
- [获取数据库连接](#获取数据库连接)
    - [获取连接的基本知识](#获取连接的基本知识)
    - [获取连接的四个要素](#获取连接的四个要素)
    - [5种创建Connection对象的方法及示例](#5种创建Connection对象的方法及示例)
- [Statement](#Statement)
    - [Statement接口](#Statement接口)
    - [PreparedStatement接口](#preparedstatement接口)
        - [PreparedStatement的基本概念](#preparedstatement的基本概念)
        - [PreparedStatement执行查询处理](#PreparedStatement执行查询处理)
        - [自定义PreparedStatement通用增删改查](#自定义PreparedStatement通用增删改查)
        - [操作blob数据](#操作blob数据)
        - [批量插入](#批量插入)
    - [CallableStatement](#CallableStatement)
- [事务](#事务)
- [DAO](#dao)
- [数据库连接池](#数据库连接池)
    - [数据库连接池的基本概念](#数据库连接池的基本概念)
    - [c3p0](#c3p0)
    - [Druid](#Druid)
- [DBUtils实现CRUD操作](#dbutils实现crud操作)
- [maven配置](#maven配置)
- [总结](#总结)

# JDBC概述
## JDBC的概念
[top](#catalog)
- JDBC(Java Database Connectivity)
- 一个**独立于特定数据库管理系统、通用的SQL数据库存取和操作的公共接口**（一组API）
- 定义了用来访问数据库的标准Java类库，（**java.sql，javax.sql**）
    - 使用这些类库可以以一种**标准**的方法、方便地访问数据库资源。
- JDBC为访问不同的数据库提供了一种**统一的途径**，为开发者屏蔽了一些细节问题。
- JDBC本身是一种规范，即如何使用java程序来操作数据库


## JDBC接口的两个层次
[top](#catalog)
- **面向应用的API**：Java API，抽象接口，供应用程序开发人员使用（连接数据库，执行SQL语句，获得结果）。
-  **面向数据库的API**：Java Driver API，供开发商开发数据库驱动程序用。

## JDBC程序编写步骤
[top](#catalog)
1. 导入`java.sql`包
    - 添加相应数据库的驱动
2. 加载并注册驱动程序
    - 两种方式
        - 直接使用对应的类创建
        - 通过反射创建(可移植性更好)
        - 使用`class DriverManager`
3. **创建`Connection`对象**
4. **创建`Statement`对象**
5. 执行sql
    - 查询：
        - 使用`ResultSet对象`
        - **关闭`ResultSet对象`**
    - 更新
        - 没有其他特殊操作
6. **关闭`Statement`对象**
7。 **关闭`Connection`对象**

## JDBC的两种编程思想
[top](#catalog)
- 面向接口编程
- ORM(Object Relationship Mapping)对象关系有映射
    - 一个数据集对应一个java类
    - 表中的一条记录对应java类的一个对象
    - 表中的一个字段对应java类的一个属性
        - 使用时，应该是一个字段别名对应一个属性

## JDBC对结果集的处理
[top](#catalog)
- JDBC处理`ResultSet对象`的[两种技术](#mapresultset)
    - 元数据：`ResultSetMetaData`
    - 反射：动态的给属性设值

# Java数据类型和数据库数据类型的转换
[top](#catalog)
- 基本类型转换规则

    |Java类型|SQL类型|
    |-|-|
    |boolean|bit|
    |byte|Tinyint|
    |short|Smallint|
    |int|Int,integer|
    |long|BIgint|
    |String|char, varchar, longvarchar|
    |byte array|binary, var binary|
    |java.sql.Date|date|
    |java.sql.Time|time|
    |java.sql.TimeStamp|timestamp|

- date类型可以使用格式化的字符串来代替，如`1999-10-10`

# 获取数据库连接

## 获取连接的基本知识
[top](#catalog)
- 一个数据库连接就是一个`Socket`连接
- 程序中不需要直接去访问`Driver`接口的`实现类`，而是由驱动程序管理器类(java.sql.DriverManager)去调用这些`Driver`实现
  - Oracle的驱动：`oracle.jdbc.driver.OracleDriver`
  - mySql的驱动： `com.mysql.jdbc.Driver`

## 获取连接的四个要素
[top](#catalog)

1. driver对象
2. url
    - MySql
        - mysql5.7:`jdbc:mysql://127.0.0.1:3306/test?`
        - mysql8:`jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false`
            - 8以后必须添加时区`serverTimezone`
    - Oracle 9i的连接URL编写方式：
        - jdbc:oracle:thin:@主机名称:oracle服务端口号:数据库名称
        - jdbc:oracle:thin:@localhost:1521:testdb

    - SQLServer的连接URL编写方式：
        - jdbc:sqlserver://主机名称:sqlserver服务端口号:DatabaseName=数据库名称
        - jdbc:sqlserver://localhost:1433:DatabaseName=testdb
    - url组成
        - jdbc 主协议
        - mysql子协议
        - test数据库名
3. 用户名
4. 密码

## 5种创建Connection对象的方法及示例
[top](#catalog)
- 5种创建方法
    1. 直接使用MySql手动创建`Driver`和`Connection`
    2. 通过反射创建`Driver`，再创建`Connection`
    3. 通过`DriverManager`来创建
    4. 对`DriverManager`的方式进行简化
        - 因为驱动类加载时，通过静态代码做了同样的引入操作
    5. 将部分配置信息移动到配置文件中，通过加载配置文件来实现

- 示例: 
    - 测试代码：`myjdbc/src/main/java/com/ljs/myjdbc/connection/ConnectionTest.java`
        ```java
        public class ConnectionTest {
            // 连接方式1
            @Test
            public void method01() throws SQLException {
                // Driver driver = new com.mysql.jdbc.Driver();
                Driver driver = new com.mysql.cj.jdbc.Driver();
                // jdbc 主协议，mysql子协议，test数据库
                String url = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false";
                Properties info = new Properties();
                info.setProperty("user", "root");
                info.setProperty("password", "1234");

                // 创建Connection对象
                Connection connect = driver.connect(url, info);
                System.out.println(connect);
            }

            //连接方式2：通过反射创建驱动器,可移植性更好
            @Test
            public void method02() throws Exception {
                Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
                Driver driver = (Driver)clazz.newInstance();

                String url = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false";
                Properties info = new Properties();
                info.setProperty("user", "root");
                info.setProperty("password", "1234");

                // 创建Connection对象
                Connection connect = driver.connect(url, info);
                System.out.println(connect);

            }

            //连接方式3：通过DriverManager来创建驱动
            @Test
            public void method03() throws Exception {
                String url = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false";
                String user = "root";
                String password = "1234";

                //获取driver类对象
                Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
                Driver driver = (Driver)clazz.newInstance();

                //注册驱动
                DriverManager.registerDriver(driver);

                //获取连接
                Connection connection = DriverManager.getConnection(url, user, password);

                System.out.println(connection);
            }

            //连接方式4：对方式3进行优化
            @Test
            public void method04() throws Exception {
                String url = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false";
                String user = "root";
                String password = "1234";

                //加载Driver
                //MySql下也可以省略该操作，在jar包/META-INF/services/java.sql.Driver下引入了该类
                Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");

                //省略注册操作，在Mysql的实现类中已经被执行了
                // public class Driver extends NonRegisteringDriver implements java.sql.Driver {
                //     public Driver() throws SQLException {
                //     }
                //
                //     static {
                //         try {
                //             DriverManager.registerDriver(new Driver());
                //         } catch (SQLException var1) {
                //             throw new RuntimeException("Can't register driver!");
                //         }
                //     }
                // }

                //Driver driver = (Driver)clazz.newInstance();
                //注册驱动
                //DriverManager.registerDriver(driver);

                //获取连接
                Connection connection = DriverManager.getConnection(url, user, password);

                System.out.println(connection);
            }

            //方式5，将部分配置信息移动到配置文件中，通过加载配置文件来实现
            @Test
            public void method05() throws Exception {
                //读取配置文件
                // eclipse
                // InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
                // idea
                FileInputStream is = new FileInputStream("resource/jdbc.properties");
                Properties props = new Properties();

                props.load(is);
                String url = props.getProperty("url");
                String user = props.getProperty("user");
                String password = props.getProperty("password");
                String driverClass = props.getProperty("driverClass");

                Class.forName(driverClass);
                Connection connection = DriverManager.getConnection(url, user, password);
                System.out.println(connection);
            }
        }
        ```
    - jdbc连接配置文件：`myjdbc/resource/jdbc.properties`
        ```xml
        url=jdbc:mysql://127.0.0.1:3306/test?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useUnicode=true&useSSL=false&rewriteBatchedStatements=true
        user=root
        password=1234
        driverClass=com.mysql.cj.jdbc.Driver
        ```

# Statement
## Statement接口
[top](#catalog)
- `Statement`：用于执行静态SQL语句，并返回执行结果对象
- `Statement`的一些弊端
    - 需要拼写SQL语句(拼接查询条件)
    - 批量插入的效率比较差
        - 每次都要重新拼接sql字符串
        - 每个sql都需要编译后才能使用
    - sql注入，如
        - 拼接的sql
            ```java
            String sql  = "SELECT user, password ";
                   sql += "FROM user_table ";
                   sql += "WHERE ";
                   sql += "user = '" + param01 + "' ";
                   sql += "AND password = '" + param02 + "' ";
            ```
        - 输入的条件
            - user:`a' OR 1 = `
            - password:` OR '1' = '1`
        - 但是sql拼接完之后**产生了不同的语义**
            - 由于产生了`OR '1' = '1'`，所以无论输入什么条件都可以进行访问

            ```sql
            SELECT user, password 
            FROM user_table 
            WHERE 
                user = 'a' 
                OR 1 = ' AND password = '
                OR '1' = '1'
            ```

## PreparedStatement接口
### PreparedStatement的基本概念
[top](#catalog)

- `PreparedStatement`是`Statement`接口的子接口
    - 一般都使用`PreparedStatement`来替代`Statement`
- `PreparedStatement`：SQL语句被**预编译**并**存储**在该对象中，可以使用该对象多次高效的执行SQL语句
- 在SQL中使用占位符`?`来替代未知条件
    - `insert into customers(name, email, birth) values (?, ?, ?);`
    - <label style="color:red">如果表名与一些关键字相同需要使用``包裹</label>
        ```sql
        insert into `order` ...
        ```
- `PreparedStatement`优点
    - 解决sql注入
        - 通过**预编译**和**占位符**，保证了检索条件之间的关系，不会因为其他原因而改变
    - 可以操作blob类型的数据
    - 实现更高效的批量插入
        - `Statement`每次都需要校验sql，`PreparedStatement`只校验一次，使用时只是填充占位符

- 基本方法

    |方法|执行内容|返回值|
    |-|-|-|
    |`connection.prepareStatement(sql)`|创建对象，并预编译sql|PreparedStatement接口对象|
    |`void setXXX(int index, XXX value)`|填充sql中的占位符，<label style="color:red">index从1开始</label>|-|
    |`void setObject(int parameterIndex, Object x) throws SQLException;`|通用sql参数设定方法|-|
    |`ResultSet executeQuery() throws SQLException;`|执行查询|返回查询的结果集|
    |`boolean execute() throws SQLException;`|执行`insert`, `update`, `delete`，也可以执行`select`|如果执行select，有返回结果，则返回`true`<br>如果执行增删改，没有结果集，则返回`false`|
    |`int executeUpdate() throws SQLException;`|执行增删改|返回更新的数据行数|
    
## PreparedStatement执行查询处理
[top](#catalog)
- 处理方法
    - 使用`executeQuery()`执行查询，返回一个`ResultSet`对象
    - 使用`next()`方法迭代，返回`true/false`，如果返回`false`则迭代结束
    - 可以通过ORM来处理数据
    -  <span id="mapresultset"></span>通过反射从`ResultSet`对象中取值
    - 从ResultSet和元数据中取值时，起始<label style="color:red">下标都从1开始</label>
- 示例
    ```java
    // 执行并返回结果集
    resultSet = ps.executeQuery();
    // 获取结果集的元数据
    ResultSetMetaData metaData = resultSet.getMetaData();
    //  获取结果中的列数
    int columnCount = metaData.getColumnCount();
    // 处理结果集
    if (resultSet.next()) {
        customers = new Customers();
        // 处理一行数据
        for (int j = 1; j <= columnCount; j++) {
            // 获取第j列的值
            Object value = resultSet.getObject(j);
            // 获取第j列的列名，不推荐使用
            // String columnName = metaData.getColumnName(j);
            // 获取第j列的别名
            String columnName = metaData.getColumnLabel(j);
            // 通过反射，给对应列名的属性设值
            // 获取属性
            Field field = Customers.class.getDeclaredField(columnName);
            // 防止私有属性
            field.setAccessible(true);
            // 设值
            field.set(customers, value);
        }
    }
    ```

### 自定义PreparedStatement通用增删改查
[top](#catalog)
- `myjdbc/src/main/java/com/ljs/myjdbc/util/JDBCUtils.java`
```java
public class JDBCUtils {
    public static Connection getConnection() throws Exception {
        FileInputStream is = new FileInputStream("resource/jdbc.properties");
        Properties props = new Properties();
        props.load(is);

        String url = props.getProperty("url");
        String user = props.getProperty("user");
        String password = props.getProperty("password");
        String driverClass = props.getProperty("driverClass");

        Class.forName(driverClass);

        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public static void closeResources(Connection connection, Statement statement){
        //关闭资源
        try {
            if (statement != null)
                statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            if (connection != null)
                connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet){
        //关闭资源
        try {
            if (resultSet != null)
                resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            if (statement != null)
                statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            if (connection != null)
                connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static <T> List<T> getRows(Class<T> clazz, String sql, Object ...args){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();

            ps = connection.prepareStatement(sql);

            int i = 1;
            for (Object arg : args) {
                ps.setObject(i, arg);
                i++;
            }

            // 执行并返回结果集
            resultSet = ps.executeQuery();
            // 获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //  获取结果中的列数
            int columnCount = metaData.getColumnCount();

            List<T> rows = new ArrayList<>();
            // 处理结果集
            while (resultSet.next()) {
                T obj = clazz.newInstance();
                // 处理一行数据
                for (int j = 1; j <= columnCount; j++) {
                    // 获取第j列的值
                    Object value = resultSet.getObject(j);
                    // 获取第j列的别名
                    String columnName = metaData.getColumnLabel(j);
                    // 通过反射，给对应列名的属性设值
                    // 获取属性
                    Field field = clazz.getDeclaredField(columnName);
                    // 防止私有属性
                    field.setAccessible(true);
                    // 设值
                    field.set(obj, value);
                }

                rows.add(obj);
            }

            return rows;
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, ps, resultSet);
        }

        return null;
    }

    public static <T> T getRow(Class<T> clazz, String sql, Object ...args){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        T obj = null;
        try {
            connection = getConnection();

            ps = connection.prepareStatement(sql);

            int i = 1;
            for (Object arg : args) {
                ps.setObject(i, arg);
                i++;
            }

            // 执行并返回结果集
            resultSet = ps.executeQuery();
            // 获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //  获取结果中的列数
            int columnCount = metaData.getColumnCount();

            // 处理结果集
            if (resultSet.next()) {
                obj = clazz.newInstance();
                // 处理一行数据
                for (int j = 1; j <= columnCount; j++) {
                    // 获取第j列的值
                    Object value = resultSet.getObject(j);
                    // 获取第j列的列名，不推荐使用
                    // String columnName = metaData.getColumnName(j);
                    // 获取第j列的别名
                    String columnName = metaData.getColumnLabel(j);
                    // 通过反射，给对应列名的属性设值
                    // 获取属性
                    Field field = clazz.getDeclaredField(columnName);
                    // 防止私有属性
                    field.setAccessible(true);
                    // 设值
                    field.set(obj, value);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, ps, resultSet);
        }

        return obj;
    }

    public static int update(String sql, Object ...args){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            //获取连接
            connection = getConnection();
            ps = connection.prepareStatement(sql);

            //填充占位符
            int i = 1;
            for (Object arg : args) {
                ps.setObject(i, arg);
                i++;
            }

            //执行
            return ps.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, ps);
        }

        return 0;
    }
}
```

### 操作blob数据
[top](#catalog)
- jdbc只允许插入1M以下的数据，需要在`mysql.ini`中指定:`max_allowed_packet=16M`
    - mysql8没有这种问题
- 插入时，`setBlob()`需要创建一个文件输入流`InputStream`
- 查询时，`blob.getBinaryStream()`返回一个文件输入流`InputStream`，需要自行转换
- 示例: `myjdbc/src/main/java/com/ljs/myjdbc/blob/blobTest.java`
    ```java
    public class blobTest {
        // 插入blob字段
        @Test
        public void insertBlobTest() throws Exception {
            Connection connection = JDBCUtils.getConnection();
            String sql = "insert into customers (name, email, birth, photo) values(?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, "blobTest");
            ps.setObject(2, "test@com");
            ps.setObject(3, "1999-10-10");
            // 创建一个文件输入流`InputStream`
            FileInputStream is = new FileInputStream("resource/blobTest.png");
            ps.setBlob(4, is);
            ps.execute();

            JDBCUtils.closeResources(connection, ps);
        }

        //查询blob字段
        @Test
        public void selectBlobTest() {
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet resultSet = null;
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                connection = JDBCUtils.getConnection();
                String sql = "select photo from customers where name = 'blobTest'";
                ps = connection.prepareStatement(sql);
                resultSet = ps.executeQuery();

                if (resultSet.next()) {
                    Blob blob = resultSet.getBlob(1);
                    // 下载文件并保存到本地
                    is = blob.getBinaryStream();
                    fos = new FileOutputStream("resource/blobTestDownload.png");
                    byte[] bs = new byte[1024];
                    int len;
                    while ((len = is.read(bs)) != -1) {
                        fos.write(bs, 0, len);
                    }
                    fos.flush();
                }
            }catch (Exception e){
                e.printStackTrace();
            } finally {
                try{
                    if (is != null)
                        is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    if(fos != null)
                        fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JDBCUtils.closeResources(connection, ps, resultSet);
            }
        }
    }
    ```

### 批量插入
[top](#catalog)
- `addBatch()`, `executeBatch()`, `clearBatch()`，分别在迭代的过程当中保存sql、批量执行sql、清空执行过的sql
- 批量插入的优化：插入过程中禁止`commit`，全部插入后再`commit`
    - `connection.setAutoCommit(false);`
    - `connection.commit();`
- 示例: `myjdbc/src/main/java/com/ljs/myjdbc/batch/BatchInsertTest.java`
    ```java
    @Test
    public void batchInsertTest03(){
        // 优化：所有的sql执行完之后，再进行commit

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            long start = System.currentTimeMillis();
            connection = JDBCUtils.getConnection();
            // 禁止自动提交数据
            connection.setAutoCommit(false);
            String sql = "insert into goods(name) value(?)";

            ps = connection.prepareStatement(sql);
            for (int i = 0; i <= 20000; i++) {
                ps.setObject(1, "name_" + i);
                // 暂存sql
                ps.addBatch();

                if (i % 500 == 0){
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }

            connection.commit(); //提交数据
            long end = System.currentTimeMillis();
            System.out.println(end-start);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection, ps);
        }
    }
    ```

## CallableStatement
[top](#catalog)
- `CallableStatement`是`Statement`接口的子接口
- `CallableStatement`用于执行SQL存储过程

# 事务
[top](#catalog)
- 隔离级别
    - `connection.getTransactionIsolation()`查看当前数据库的事务隔离级别
    - `connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);`设置事务隔离级别
- 辅助事务的方法
    - `connection.setAutoCommit(false);`关闭自动提交
    - `connection.commit();`全部操作完成后再执行提交
    - `connection.rollback();`出现异常时，回滚任务
    - `connection.setAutoCommit(true);`任务结束时最好恢复自动提交的状态（尤其是在使用数据库连接池时）
- 示例：多次更新
    ```java
    public static int safeUpdate(Connection connection, String sql, Object ...args){
        PreparedStatement ps = null;
        try {
            //获取连接
            ps = connection.prepareStatement(sql);

            //填充占位符
            int i = 1;
            for (Object arg : args) {
                ps.setObject(i, arg);
                i++;
            }

            //执行
            return ps.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }

        return 0;
    }

    @Test
    public void safeUpdateTest(){
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            connection.setAutoCommit(false);
            String sql1 = "update user_table set balance = balance - 100 where user = ?;";
            String sql2 = "update user_table set balance = balance + 100 where user = ?;";

            JDBCUtils.safeUpdate(connection, sql1, "AA");

            System.out.println(1 / 0);//如果出现异常无法保证操作的正确性

            JDBCUtils.safeUpdate(connection, sql2, "BB");

            // 提交操作
            connection.commit();
        }catch (Exception e){
            e.printStackTrace();
            //回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            JDBCUtils.closeResources(connection, null);
        }
    }
    ```

# DAO
[top](#catalog)
- 封装针对于数据库表的通用操作
- 示例
    - `abstract class BaseDAO<T>`，提供基本的增删改查功能
    - `interface CustomersDAO`，表`Customers`的操作接口
    - `class CustomersDAOImpl extends BaseDAO<Customers> implements CustomerDAO`，接口实现
```java
public abstract class BaseDAO<T> {
    private Class<T> clazz = null;

    // 获取范型类型
    {
        // 获取父类
        ParameterizedType superClazz = (ParameterizedType)this.getClass().getGenericSuperclass();
        // 获取父类中的范型
        Type[] typeArguments = superClazz.getActualTypeArguments();
        clazz = (Class<T>) typeArguments[0];
    }

    // 通用的增删改,考虑事务
    public int update(Connection conn, String sql, Object... args){
        PreparedStatement ps = null;
        try {
            //获取连接
            ps = conn.prepareStatement(sql);

            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }

            //执行
            return ps.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(null, ps);
        }

        return 0;
    }

    public List<T> getRows(Connection connection, String sql, Object ...args){
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }

            // 执行并返回结果集
            resultSet = ps.executeQuery();
            // 获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //  获取结果中的列数
            int columnCount = metaData.getColumnCount();

            List<T> rows = new ArrayList<>();
            // 处理结果集
            while (resultSet.next()) {
                T obj = clazz.getDeclaredConstructor().newInstance();

                // 处理一行数据
                for (int j = 1; j <= columnCount; j++) {
                    // 获取第j列的值
                    Object value = resultSet.getObject(j);
                    // 获取第j列的别名
                    String columnName = metaData.getColumnLabel(j);
                    // 通过反射，给对应列名的属性设值
                    // 获取属性
                    Field field = clazz.getDeclaredField(columnName);
                    // 防止私有属性
                    field.setAccessible(true);
                    // 设值
                    field.set(obj, value);
                }

                rows.add(obj);
            }

            return rows;
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(null, ps, resultSet);
        }

        return null;
    }

    public T getRow(Connection connection, String sql, Object ...args){
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }

            // 执行并返回结果集
            resultSet = ps.executeQuery();
            // 获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //  获取结果中的列数
            int columnCount = metaData.getColumnCount();

            // 处理结果集
            if (resultSet.next()) {
                T obj = clazz.getDeclaredConstructor().newInstance();
                // 处理一行数据
                for (int j = 1; j <= columnCount; j++) {
                    // 获取第j列的值
                    Object value = resultSet.getObject(j);
                    // 获取第j列的列名，不推荐使用
                    // String columnName = metaData.getColumnName(j);
                    // 获取第j列的别名
                    String columnName = metaData.getColumnLabel(j);
                    // 通过反射，给对应列名的属性设值
                    // 获取属性
                    Field field = clazz.getDeclaredField(columnName);
                    // 防止私有属性
                    field.setAccessible(true);
                    // 设值
                    field.set(obj, value);
                }

                return obj;
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(null, ps, resultSet);
        }

        return null;
    }

    public <E> E getValue(Connection connection, String sql, Object...args)  {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                return (E) rs.getObject(1);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(null, ps, rs);
        }

        return null;
    }
}
```
```java
public interface CustomerDAO {
    // 定义针对表Customer的常用操作

    // 插入数据
    void insert(Connection conn, Customers obj);

    // 根据id删除数据
    void deleteById(Connection conn, int id);

    // 根据传入的对象来修改数据
    void update(Connection conn, Customers obj);

    // 根据指定id查询数据
    Customers getCustomerById(Connection conn, int id);

    // 查询所有数据
    List<Customers> getAll(Connection conn);

    // 返回数据量
    Long getCount(Connection conn);

    // 返回最大的生日
    Date getMaxBirth(Connection conn);
}
```
```java
public class CustomersDAOImpl extends BaseDAO<Customers> implements CustomerDAO {
    @Override
    public void insert(Connection conn, Customers obj) {
        String sql = "insert into customers(name, email, birth) values(?, ?, ?)";
        update(conn, sql, obj.getName(), obj.getEmail(), obj.getBirth());
    }

    @Override
    public void deleteById(Connection conn, int id) {
        String sql = "delete from customers where id = ?";
        update(conn, sql, id);
    }

    @Override
    public void update(Connection conn, Customers obj) {
        String sql = "update customers set name=?, email=?, birth=? where id=?";
        update(conn, sql, obj.getName(), obj.getEmail(), obj.getBirth(), obj.getId());
    }

    @Override
    public Customers getCustomerById(Connection conn, int id) {
        String sql = "select id, name, email, birth from customers where id = ?";
        return getRow(conn, sql, id);
    }

    @Override
    public List<Customers> getAll(Connection conn) {
        String sql = "select id, name, email, birth from customers";
        return getRows(conn, sql);
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from customers";
        return getValue(conn, sql);
    }

    @Override
    public Date getMaxBirth(Connection conn) {
        String sql = "select max(birth) from customers";
        return getValue(conn, sql);
    }
}
```

# 数据库连接池
## 数据库连接池的基本概念
[top](#catalog)
- 手动创建连接的问题
    - 每次通过`DriverManager`,`DriverManager`获取连接时，都要将`DriverManager`加载到内存，并验证用户名密码，执行后再断开连接会浪费大量的资源和时间
    - **对于每一次数据库连接，使用完后都得断开**，否则，如果程序出现异常而未能关闭，将会导致数据库系统中的内存泄漏，最终将导致重启数据库
    - **无法控制被创建的连接对象数**，如连接过多，也可能导致内存泄漏，服务器崩溃
- 数据库连接池技术
    - 由数据库连接池控制：连接的数量，分配、管理和释放连接
    - 数据库连接池在初始化时根据**最小数据库连接数**来创建连接
        - 无论这些数据库连接是否被使用，连接池都将一直保证至少拥有这么多的连接数量
    - 连接池的**最大数据库连接数量**限定了这个连接池能占有的最大连接数
        - 当应用程序向连接池请求的连接数超过最大连接数量时，这些请求将被加入到**等待队列**中
- 数据库连接池的优点
    - 提高程序的相应速度
    - 降低资源消耗，连接可以重复使用
    - 便于连接的管理
- `javax.sql.DataSource`数据源
    - JDBC 的数据库连接池使用`javax.sql.DataSource`来表示，`DataSource`只是一个接口，
    - DataSource用来取代DriverManager来获取Connection，获取速度快，同时可以大幅度提高数据库访问速度
    - `DataSource`该接口通常由服务器(Weblogic, WebSphere, Tomcat)提供实现,也有一些开源实现：
        - **DBCP** 是Apache提供的数据库连接池。tomcat 服务器自带dbcp数据库连接池。**速度相对c3p0较快**，但因自身存在BUG，Hibernate3已不再提供支持
        - **C3P0** 是一个开源组织提供的一个数据库连接池，**速度相对较慢，稳定性还可以**，hibernate官方推荐使用
        - **Proxool** 是sourceforge下的一个开源项目数据库连接池，有监控连接池状态的功能，**稳定性较c3p0差一点**
        - **BoneCP** 是一个开源组织提供的数据库连接池，速度快
        - **Druid** 是阿里提供的数据库连接池，集DBCP 、C3P0 、Proxool 优点于一身的数据库连接池，但是速度不确定是否有BoneCP快

    - `DataSource`通常被称为数据源，它包含**连接池和连接池管理**两个部分，习惯上也经常把`DataSource`称为连接池
    - `DataSource`和`Connection`不同，`DataSource`是产生数据库连接的工厂，<label style="color:red">因此整个应用只需要一个`DataSource`</label>

- 当数据库访问结束后，使用`conn.close()`关闭连接，但`conn.close()`并没有关闭数据库的物理连接，它仅仅把数据库连接释放，归还给了数据库连接池

## c3p0
[top](#catalog)
- 创建数据库连接池
    ```java
    public class C3P0Test {

        // 手动配置数据库连接池信息
        @Test
        public void connectionTest01() throws Exception {
            //ComboPooledDataSource是DataSource的接口实现类
            //获取数据库连接处
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDriverClass("com.mysql.cj.jdbc.Driver"); //loads the jdbc driver
            cpds.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useUnicode=true&useSSL=false&rewriteBatchedStatements=true");
            cpds.setUser("root");
            cpds.setPassword("1234");

            //通过设置相关参数，管理数据库连接池
            cpds.setInitialPoolSize(10);

            //获取一个连接
            Connection conn = cpds.getConnection();
            System.out.println(conn);

            DataSources.destroy(cpds);
        }

        //通过配置来创建数据库连接池
        @Test
        public void connectionTest02() throws SQLException {
            ComboPooledDataSource cpds = new ComboPooledDataSource("testcp");
            Connection conn = cpds.getConnection();
            System.out.println(conn);
            DataSources.destroy(cpds);
        }
    }
    ```
- 配置文件
    - idea:`resource/c3p0-config.xml`
    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!--https://www.mchange.com/projects/c3p0/#configuration_files-->
    <c3p0-config>
        <!-- This app is massive! -->
        <!--testcp 为配置名，可以自定义；读取时也通过该属性读取-->
        <named-config name="testcp">

            <!--提供获取连接的4个基本信息-->

            <property name="driverClass">com.mysql.cj.jdbc.Driver</property>
            <property name="jdbcUrl">jdbc:mysql://127.0.0.1:3306/test?serverTimezone=Asia/Shanghai&amp;characterEncoding=utf8&amp;useUnicode=true&amp;useSSL=false&amp;rewriteBatchedStatements=true</property>
            <property name="user">root</property>
            <property name="password">1234</property>

            <!--进行数据库连接池管理的基本信息-->
            <!--连接数不足时，向数据库服务器申请的连接数-->
            <property name="acquireIncrement">5</property>
            <!--初始化连接数-->
            <property name="initialPoolSize">10</property>
            <!--最小连接数-->
            <property name="minPoolSize">10</property>
            <!--最大连接数-->
            <property name="maxPoolSize">100</property>
            <!--连接池中维护的Statement个数-->
            <property name="maxStatements">50</property>
            <!--每个连接可以使用的连接数-->
            <property name="maxStatementsPerConnection">2</property>

        </named-config>
    </c3p0-config>
    ```

## Druid
[top](#catalog)
- 配置参数

|配置                            | 缺省     | 说明                                                    |
| ----------------------------- | -------- | ------------------------------------------------------------ |
| name                          |          | 配置这个属性的意义在于，如果存在多个数据源，监控的时候可以通过名字来区分开来。   如果没有配置，将会生成一个名字，格式是：”DataSource-” +   System.identityHashCode(this) |
| url                           |          | 连接数据库的url，不同数据库不一样。例如：mysql :   jdbc:mysql://10.20.153.104:3306/druid2      oracle :   jdbc:oracle:thin:@10.20.149.85:1521:ocnauto |
| username                      |          | 连接数据库的用户名                                           |
| password                      |          | 连接数据库的密码。如果你不希望密码直接写在配置文件中，可以使用ConfigFilter。详细看这里：<https://github.com/alibaba/druid/wiki/%E4%BD%BF%E7%94%A8ConfigFilter> |
| driverClassName               |          | 根据url自动识别   这一项可配可不配，如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName(建议配置下) |
| initialSize                   | 0        | 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时 |
| maxActive                     | 8        | 最大连接池数量                                               |
| maxIdle                       | 8        | 已经不再使用，配置了也没效果                                 |
| minIdle                       |          | 最小连接池数量                                               |
| maxWait                       |          | 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。 |
| poolPreparedStatements        | false    | 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。 |
| maxOpenPreparedStatements     | -1       | 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100 |
| validationQuery               |          | 用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。 |
| testOnBorrow                  | true     | 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 |
| testOnReturn                  | false    | 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能 |
| testWhileIdle                 | false    | 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。 |
| timeBetweenEvictionRunsMillis |          | 有两个含义： 1)Destroy线程会检测连接的间隔时间2)testWhileIdle的判断依据，详细看testWhileIdle属性的说明 |
| numTestsPerEvictionRun        |          | 不再使用，一个DruidDataSource只支持一个EvictionRun           |
| minEvictableIdleTimeMillis    |          |                                                              |
| connectionInitSqls            |          | 物理连接初始化的时候执行的sql                                |
| exceptionSorter               |          | 根据dbType自动识别   当数据库抛出一些不可恢复的异常时，抛弃连接 |
| filters                       |          | 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：   监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall |
| proxyFilters                  |          | 类型是List，如果同时配置了filters和proxyFilters，是组合关系，并非替换关系 |

- 示例
    ```java
    @Test
        public void connectionTest() throws Exception {
            Properties pros = new Properties();
            FileInputStream is = new FileInputStream("resource/druid.properties");
            pros.load(is);
            DataSource ds = DruidDataSourceFactory.createDataSource(pros);
            Connection conn = ds.getConnection();
            System.out.println(conn);
        }
    ```

# DBUtils实现CRUD操作
[top](#catalog)
- JDBC工具类库，是对JDBC的简单封装
- 使用DBUtils可以简化jdbc编码的工作量，同时也不会影响程序的性能。
- 使用方法
    - `runner.update`，执行增删改操作
        ```java
        @Test
        public void updateTest()  {
            QueryRunner runner = new QueryRunner();
            Connection conn = null;
            try {
                conn = JDBCUtils.getConnectionFromPool();
                String sql = "insert into customers(name, email, birth) values(?, ?, ?)";
                int count = runner.update(conn, sql, "QueryRunnerTest", "query@com", Date.valueOf("2019-01-01"));
                System.out.println("insert : " + count);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.closeResources(conn, null);
            }
        }
        ```
    - `runner.query`执行查询，查询结果需要使用`ResultSetHandler`接口的实现来处理
        ```java
        // 使用BeanHandler来封装结果，他是ResultSetHandler接口的实现
        // 只返回一行数据
        @Test
        public void queryTest(){
            QueryRunner runner = new QueryRunner();
            Connection conn = null;
            try {
                conn = JDBCUtils.getConnectionFromPool();
                String sql = "select id, name, email, birth from customers where id = ?";

                BeanHandler<Customers> handler = new BeanHandler<>(Customers.class);
                Customers result = runner.query(conn, sql, handler, 23);
                System.out.println(result);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.closeResources(conn, null);
            }
        }

        // 使用BeanListHandler来封装结果
        // 返回多行数据
        @Test
        public void queryRowsTest(){
            QueryRunner runner = new QueryRunner();
            Connection conn = null;
            try {
                conn = JDBCUtils.getConnectionFromPool();
                String sql = "select id, name, email, birth from customers where id < ?";

                BeanListHandler<Customers> handler = new BeanListHandler<>(Customers.class);
                List<Customers> result = runner.query(conn, sql, handler, 23);
                result.forEach(System.out::println);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.closeResources(conn, null);
            }
        }

        // 使用MapHandler来封装结果
        // 返回一行数据，并将数据封装为KV
        @Test
        public void mapHandlerTest(){
            QueryRunner runner = new QueryRunner();
            Connection conn = null;
            try {
                conn = JDBCUtils.getConnectionFromPool();
                String sql = "select id, name, email, birth from customers where id = ?";

                MapHandler handler = new MapHandler();
                Map<String, Object> result = runner.query(conn, sql, handler, 23);
                result.forEach((k, v) -> System.out.println(k + ":" + v));
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.closeResources(conn, null);
            }
        }

        // 使用MapListHandler来封装结果
        // 返回多行数据，并将每一行数据封装为KV
        @Test
        public void mapListHandlerTest(){
            QueryRunner runner = new QueryRunner();
            Connection conn = null;
            try {
                conn = JDBCUtils.getConnectionFromPool();
                String sql = "select id, name, email, birth from customers where id < ?";

                MapListHandler handler = new MapListHandler();
                List<Map<String, Object>> result = runner.query(conn, sql, handler, 23);
                result.forEach(System.out::println);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.closeResources(conn, null);
            }
        }

        // 查询特殊值
        @Test
        public void scalaHandlerTest(){
            QueryRunner runner = new QueryRunner();
            Connection conn = null;
            try {
                conn = JDBCUtils.getConnectionFromPool();
                String sql = "select count(*) from customers";

                ScalarHandler handler = new ScalarHandler();
                Long result = (Long)runner.query(conn, sql, handler);
                System.out.println(result);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.closeResources(conn, null);
            }
        }
        ```
    - 查询时，使用自定义`ResultSetHandler`接口实现
        ```java
        // 自定义ResultSetHandler接口实现
        @Test
        public void myHandlerTest(){
            QueryRunner runner = new QueryRunner();
            Connection conn = null;
            try {
                conn = JDBCUtils.getConnectionFromPool();
                String sql = "select id, name, email, birth from customers where id = ?";

                ResultSetHandler<Customers> handler = new ResultSetHandler<Customers>(){

                    @Override
                    public Customers handle(ResultSet resultSet) throws SQLException {
                        return null;
                    }
                };

                Customers result = runner.query(conn, sql, handler, 23);
                System.out.println(result);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.closeResources(conn, null);
            }
        }
        ```

# maven配置
[top](#catalog)
- pom.xml
    ```xml
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.18</version>
    </dependency>

    <dependency>
      <groupId>com.mchange</groupId>
      <artifactId>c3p0</artifactId>
      <version>0.9.5.2</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.apache.commons/commons-dbcp2 -->
    <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-dbcp2</artifactId>
      <version>2.7.0</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.1.21</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/commons-dbutils/commons-dbutils -->
    <dependency>
      <groupId>commons-dbutils</groupId>
      <artifactId>commons-dbutils</artifactId>
      <version>1.7</version>
    </dependency>
    ```

# 总结
[top](#catalog)
- 基本流程
    1. 使用`Driver`创建`Connection`
        - 几类创建方法
            - 手动使用各数据的driver类创建，再创建`Connection`
            - 通过反射创建driver，再创建`Connection`
            - 使用DriverManager创建，可以通过配置文件来读取参数
                - 需要注册Driver类，但是实现类中都默认执行了注册操作
            - 使用数据库连接池
                - c3p0:
                    - `new ComboPooledDataSource()`，手动创建对象，并填充配置
                    - `new ComboPooledDataSource("配置文件的【named-config】");`，读取配置文件来创建
                - DBCP
                    - `BasicDataSourceFactory.createDataSource(Properties pros)`，通过配置信息来创建
                - Durid
                    - `DruidDataSourceFactory.createDataSource(pros);`，通过配置信息来创建
        - 通过`xxx.getConnection()`来获取`Connection`对象
            - 如果是手动创建的，需要添加`url`, `user`, `password`等信息
    2. 使用`PreparedStatement`执行sql
        - 创建`PreparedStatement`对象:`connection.prepareStatement(sql)`
            - 预编译sql
        - 填充占位符
            - 通用填充:`ps.setObject(index, Object);`
            - blob对象需要使用一个`InputStream`对象
        - 执行sql
            - 执行方法
                - 全部手动编写代码来执行
                - 使用`QueryRunner`来执行
            - 增删改
                - 执行sql
                    - `executeUpdate()`，执行后会返回发生改变的行数
                    - `execute`，执行后如果没有`ResultSet`，则返回`false`;有则返回`true`
                - 批量执行
                    1. 执行前，禁止自动提交:`connection.setAutoCommit(false);`
                    2. 暂存sql:`ps.addBatch();`
                    3. sql达到一定的数量时，批量执行:`ps.executeBatch();`
                    4. 清空执行过的sql:`ps.clearBatch();`
                    5. 全部执行完后，**提交执行结果：`connection.commit();`**
                - 事务（执行多个sql，保证ACID性）
                    - 设置自动提交
                        - 执行前，禁止自动提交:`connection.setAutoCommit(false);`
                        - 全部执行完后，**提交执行结果：`connection.commit();`**
                        - 如果使用数据库连接池，关闭时最好恢复自动提交的状态:`connection.setAutoCommit(true);`
                    - 事务级别
                        - 查看当前事务级别:`connection.getTransactionIsolation()`
                        - 设置事务级别:`connection.setTransactionIsolation(...);`
                            - 使用`Connection`提供的事务级别常量，如`Connection.TRANSACTION_READ_COMMITTED`
                    - **失败时，执行回滚:`connection.rollback();`**
            - 查
                - `ps.executeQuery();`，执行查询，并返回一个`ResultSet`对象
                - 处理结果的思路
                    - 使用自定义方式
                        - `ResultSet.next()`，遍历结果集，返回`false`，则遍历结束
                        - 通过
                        - `ResultSetMetaData metaData = resultSet.getMetaData();`，返回元数据
                            - `metaData.getColumnCount();`，返回结果列数
                            - `metaData.getColumnLabel(index);`，返回列名，如果有别名则返回别名
                        - `setXXX(index)`，`setXXX(name)`，通过列名或列index来获取结果
                        - 可以使用反射和`setObject(...)`来执行通用处理
                    - 使用`DBUtil.QueryRunner`
                        - 使用`ResultSetHandler`的接口实现来完成处理
                        - 也可以自定义`ResultSetHandler`的实现
    3. 关闭`ResultSet`对象
        - 直接手动关闭，需要`try catch`
        - `DBUtil.close()`，需要`try catch`
        - `DbUtils.closeQuietly`，不用处理异常
    4. 关闭`Statement`对象
        - 直接手动关闭，需要`try catch`
        - `DBUtil.close()`，需要`try catch`
        - `DbUtils.closeQuietly`，不用处理异常
    4. 关闭`Connection`对象
        - 直接手动关闭，需要`try catch`
        - `DBUtil.close()`，需要`try catch`
        - `DbUtils.closeQuietly`，不用处理异常

- DAO
    - 使用过ORM方式编程
        - 一个表类对应一个数据库表
        - 一个对象代表一行数据
        - 一个属性对应一个表字段
    - 封装通用的增删改查执行方法
    - 使用接口固定每个`表类`应该提供的实现方法
    - 实现类可以继承通用类，然后实现接口
[top](#catalog)