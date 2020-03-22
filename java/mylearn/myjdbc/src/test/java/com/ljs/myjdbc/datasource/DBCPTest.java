package com.ljs.myjdbc.datasource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

public class DBCPTest {
    // Public Key Retrieval is not allowed
    @Test
    public void connectionTest() throws Exception {

        Properties pros = new Properties();
        FileInputStream is = new FileInputStream("resource/dbcp.properties");
        pros.load(is);
        BasicDataSource ds = BasicDataSourceFactory.createDataSource(pros);

        Connection conn = ds.getConnection();
        System.out.println(conn);
    }

}
