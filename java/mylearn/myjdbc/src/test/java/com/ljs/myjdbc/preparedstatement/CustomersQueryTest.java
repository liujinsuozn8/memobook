package com.ljs.myjdbc.preparedstatement;

import com.ljs.myjdbc.bean.Customers;
import com.ljs.myjdbc.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

public class CustomersQueryTest {
    private Customers dqlCustomers(String sql, Object ...args){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Customers customers = null;
        try {
            connection = JDBCUtils.getConnection();

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
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection, ps, resultSet);
        }

        return customers;
    }

    @Test
    public void queryTest() throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();

            String sql = "select id, name, email, birth from customers where id=?;";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, 1);

            // 执行并返回结果集
            resultSet = ps.executeQuery();
            // 处理结果集
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

                //处理数据的方式
                //1 打印
                // System.out.println("id="+ id + ", name=" + name
                //         + ",email=" + email + ", birth=" + birth);

                //2 保存到 Object[] 数组中
                // Object[] objList = new Object[]{id, name, email, birth};

                //3 保存到java bean中
                Customers customers = new Customers(id, name, email, birth);
                System.out.println(customers);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection, ps, resultSet);
        }
    }

    @Test
    public void customerTest(){
        String sql = "select name, email, birth from customers where id=?;";
        Customers customers = dqlCustomers(sql, 1);
        System.out.println(customers);
    }
}
