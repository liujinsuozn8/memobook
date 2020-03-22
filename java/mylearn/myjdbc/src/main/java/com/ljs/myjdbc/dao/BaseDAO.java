package com.ljs.myjdbc.dao;


import com.ljs.myjdbc.util.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

// 封装针对于数据库表的通用操作　
public abstract class BaseDAO<T> {
    private Class<T> clazz = null;

    // 获取范型类型
    public BaseDAO() {
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

    // 查询多行
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

    // 查询一行
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

    // 获取一个值
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
