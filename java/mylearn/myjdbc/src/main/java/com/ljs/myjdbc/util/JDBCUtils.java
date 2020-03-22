package com.ljs.myjdbc.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCUtils {
    // c3p0连接池
    // private static DataSource dataSource = new ComboPooledDataSource("testcp");

    // druid连接池
    private static DataSource dataSource ;
    static {
        InputStream is = null;
        try {
            Properties pros = new Properties();
            is = JDBCUtils.class.getResourceAsStream("/druid.properties");
            // is = new FileInputStream("druid.properties");
            pros.load(is);
            dataSource = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // 从数据库连接池中获取连接
    public static Connection getConnectionFromPool() throws SQLException {
        return dataSource.getConnection();
    }

    // 手动创建数据库连接
    public static Connection getConnection() throws Exception {
        InputStream is = JDBCUtils.class.getResourceAsStream("/jdbc.properties");
        Properties props = new Properties();
        props.load(is);

        String url = props.getProperty("url");
        String user = props.getProperty("user");
        String password = props.getProperty("password");
        String driverClass = props.getProperty("driverClass");

        Class.forName(driverClass);

        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public static void closeResources(Connection connection, Statement statement){
        //关闭资源
        try {
            if (statement != null)
                statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            if (connection != null)
                connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet){
        //关闭资源
        // try {
        //     if (resultSet != null)
        //         resultSet.close();
        // }catch (Exception e){
        //     e.printStackTrace();
        // }
        //
        // try {
        //     if (statement != null)
        //         statement.close();
        // }catch (Exception e){
        //     e.printStackTrace();
        // }
        //
        // try{
        //     if (connection != null)
        //         connection.close();
        // }catch (Exception e){
        //     e.printStackTrace();
        // }

        // 使用DBUtils关闭资源
        try {
            // DbUtils.closeQuietly(connection);
            DbUtils.close(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            // DbUtils.closeQuietly(resultSet);
            DbUtils.close(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            // DbUtils.closeQuietly(statement);
            DbUtils.close(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> getRows(Class<T> clazz, String sql, Object ...args){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();

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
            closeResources(connection, ps, resultSet);
        }

        return null;
    }

    public static <T> List<T> safeGetRows(Connection connection, Class<T> clazz, String sql, Object ...args){
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
            closeResources(null, ps, resultSet);
        }

        return null;
    }

    public static <T> T getRow(Class<T> clazz, String sql, Object ...args){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        T obj = null;
        try {
            connection = getConnection();

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
                obj = clazz.getDeclaredConstructor().newInstance();
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
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, ps, resultSet);
        }

        return obj;
    }

    public static <T> T safeGetRow(Connection connection, Class<T> clazz, String sql, Object ...args){
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        T obj = null;
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
                obj = clazz.getDeclaredConstructor().newInstance();
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
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            closeResources(null, ps, resultSet);
        }

        return obj;
    }

    // 查询特殊值，如：select count(*) from table 的值
    public static <T> T getValue(Connection connection, String sql, Object...args)  {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                return (T) rs.getObject(1);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(null, ps, rs);
        }

        return null;
    }

    public static int update(String sql, Object ...args){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            //获取连接
            connection = getConnection();
            ps = connection.prepareStatement(sql);

            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }

            //执行
            return ps.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, ps);
        }

        return 0;
    }

    //用于事务更新，需要手动关闭connection
    public static int safeUpdate(Connection connection, String sql, Object ...args){
        PreparedStatement ps = null;
        try {
            //获取连接
            ps = connection.prepareStatement(sql);

            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }

            //执行
            return ps.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }

        return 0;
    }
}
