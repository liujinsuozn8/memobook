package com.ljs.myjdbc.dao;

import com.ljs.myjdbc.utils.MyJDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BaseDao<T> {
    private Class<T> clazz;
    private QueryRunner queryRunner;

    public BaseDao(){
        ParameterizedType superClazz = (ParameterizedType)this.getClass().getGenericSuperclass();
        Type[] typeArguments = superClazz.getActualTypeArguments();
        clazz = (Class<T>) typeArguments[0];

        queryRunner = new QueryRunner();
    }

    //获取一行数据
    public T queryRow(String sql, Object...sqlArgs) {
        Connection conn = null;

        try {
            conn = MyJDBCUtils.getConnection();

            BeanHandler<T> handler = new BeanHandler<>(clazz);
            T result = queryRunner.query(conn, sql, handler, sqlArgs);
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MyJDBCUtils.closeResources(conn);
        }

        return null;
    }

    //获取多行数据
    public List<T> queryRows(String sql, Object...sqlArgs){
        Connection conn = null;
        try {
            conn = MyJDBCUtils.getConnection();
            BeanListHandler<T> handler = new BeanListHandler<>(clazz);

            List<T> query = queryRunner.query(conn, sql, handler, sqlArgs);
            return query;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            MyJDBCUtils.closeResources(conn);
        }

        return null;
    }

    //获取某个字段
    public <T> T queryValue(String sql, Object...sqlArgs){
        Connection conn = null;
        try {
            conn = MyJDBCUtils.getConnection();
            return (T) queryRunner.query(conn, sql, new ScalarHandler<>(), sqlArgs);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MyJDBCUtils.closeResources(conn);
        }

        return null;
    }

    //更新,删除,添加
    public void update(String sql,Object...sqlArgs){
        Connection conn = null;
        try {
            conn = MyJDBCUtils.getConnection();
            int update = queryRunner.update(conn, sql, sqlArgs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MyJDBCUtils.closeResources(conn);
        }
    }

}
