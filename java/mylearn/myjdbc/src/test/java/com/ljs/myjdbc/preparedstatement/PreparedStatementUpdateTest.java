package com.ljs.myjdbc.preparedstatement;

import com.ljs.myjdbc.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

// 测试：增删改
public class PreparedStatementUpdateTest {
    private void dml(String sql, Object ...args){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            //获取连接
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);

            //填充占位符
            int i = 1;
            for (Object arg : args) {
                ps.setObject(i, arg);
                i++;
            }

            //执行
            ps.execute();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection, ps);
        }
    }

    @Test
    public void insertTest() throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            //获取连接
            connection = JDBCUtils.getConnection();
            //预编译SQL，返回PreparedStatement实例
            String sql = "insert into customers(name, email, birth) values (?, ?, ?);";
            ps = connection.prepareStatement(sql);

            //填充占位符
            ps.setString(1, "psTest");
            ps.setString(2, "email@com");

            // 使用Date
            // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            // java.util.Date date = sdf.parse("2019-02-03");
            // ps.setDate(3, new Date(date.getTime()));

            // 使用LocalDate
            // Long instant = LocalDate
            //         .of(2014, 01, 06)
            //         .atStartOfDay(ZoneId.systemDefault())
            //         .toInstant()
            //         .toEpochMilli();
            // ps.setDate(3, new Date(instant));
            ps.setDate(3, Date.valueOf("1998-10-23"));

            //执行
            ps.execute();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection, ps);
        }
    }

    @Test
    public void updateTest() throws ParseException {
        String sql = "update customers set birth=? where name=?";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date sqlDate = new Date(sdf.parse("2001-01-02").getTime());

        dml(sql, sqlDate,"psTest");
    }

    @Test
    public void deleteTest() throws ParseException {
        String sql = "delete from customers where name=?";
        dml(sql,"psTest");
    }

    @Test
    public void updateResultTest(){
        String sql = "insert into customers(name, email, birth) values (?, ?, ?);";
        int count = JDBCUtils.update(sql, "xxx", "xxx@com", "01-02");
        if (count > 0)
            System.out.println("insert success");
        else
            System.out.println("insert failure");
    }
}
