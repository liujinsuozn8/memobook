package com.ljs.myjdbc.dbutils;

import com.ljs.myjdbc.bean.Customers;
import com.ljs.myjdbc.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class QueryRunnerTest {

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
                //     ResultSetMetaData metaData = resultSet.getMetaData();
                //     int columnCount = metaData.getColumnCount();
                //
                //     if (resultSet.next()){
                //         for (int i = 0; i < columnCount; i++) {
                //
                //         }
                //         return new Customers(1,"22", "44", Date.valueOf("1999-12-12"));
                //     }

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
}
