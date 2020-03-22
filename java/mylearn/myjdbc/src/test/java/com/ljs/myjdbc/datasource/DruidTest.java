package com.ljs.myjdbc.datasource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.ljs.myjdbc.util.JDBCUtils;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidTest {

    @Test
    public void connectionTest() throws Exception {
        Properties pros = new Properties();
        InputStream is = getClass().getResourceAsStream("/druid.properties");
        pros.load(is);
        DataSource ds = DruidDataSourceFactory.createDataSource(pros);
        Connection conn = ds.getConnection();
        System.out.println(conn);
    }

    @Test
    public void connectionTest02() throws Exception {
        Connection conn = JDBCUtils.getConnectionFromPool();
        System.out.println(conn);
    }
}
