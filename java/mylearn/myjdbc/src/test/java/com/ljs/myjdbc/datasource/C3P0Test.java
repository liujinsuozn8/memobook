package com.ljs.myjdbc.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

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
