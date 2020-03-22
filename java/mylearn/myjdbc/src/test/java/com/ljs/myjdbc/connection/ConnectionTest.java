package com.ljs.myjdbc.connection;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
    // 连接方式1
    @Test
    public void method01() throws SQLException {
//        Driver driver = new com.mysql.jdbc.Driver();
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
        InputStream is = getClass().getResourceAsStream("/jdbc.properties");
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
