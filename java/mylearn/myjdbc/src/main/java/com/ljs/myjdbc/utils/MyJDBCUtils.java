package com.ljs.myjdbc.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/*
* 使用C3P0数据库连接池
* 使用QueryRunner来辅助*/
public class MyJDBCUtils {
    // 使用C3P0数据库连接池
    private static DataSource dataSource;
    static {
        // 从：resources/c3p0-config.xml 下读取配置信息
        dataSource = new ComboPooledDataSource("testcp");
    }

    //从数据库连接池中获取连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    //关闭资源
    public static void closeResources(AutoCloseable src) {
        try {
            if (src != null) {
                src.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
