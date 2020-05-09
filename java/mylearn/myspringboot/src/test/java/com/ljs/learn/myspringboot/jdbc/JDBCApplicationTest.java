package com.ljs.learn.myspringboot.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class JDBCApplicationTest {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        // 1. 检查默认数据源
        System.out.println(dataSource.getClass());

        // 默认数据源：
        // class com.zaxxer.hikari.HikariDataSource

        // 2. 获取数据库连接
        Connection connection = dataSource.getConnection();
        // connection.prepareStatement("");
        // System.out.println(connection);
        connection.close();

    }
}
