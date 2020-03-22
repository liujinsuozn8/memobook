package com.ljs.myjdbc.batch;

import com.ljs.myjdbc.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BatchInsertTest {
    /* 使用PreparedStatement实现批量数据的操作
        update、delete本身就有批量操作的效果
        使用PreparedStatement实现更高效的批量插入

        create table goods (
            id int primary key auto_increment,
            name varchar(20)
        );
        方式1；statement
        Connection connection = JDBCUtils.getConnection();
        Statement st = connection.createStatement();
        for (int i = 0; i < 20000; i++) {
            String sql = "inster into goods (name) values("+ i +")";
            st.execute(sql);
        }

     */

    @Test
    public void batchInsertTest() {
        // 批量插入方式2，使用PrepareStatement
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            long start = System.currentTimeMillis();
            connection = JDBCUtils.getConnection();
            String sql = "insert into goods(name) value(?)";

            ps = connection.prepareStatement(sql);
            for (int i = 0; i < 20000; i++) {
                ps.setObject(1, "name_" + i);
                ps.execute();
            }

            long end = System.currentTimeMillis();
            System.out.println(end-start);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection, ps);
        }
    }

    @Test
    public void batchInsertTest02(){
        // 方式3：addBatch(),executeBatch(),clearBatch()
        // mysql默认是关闭批处理的，需要通过一个参数，让mysql开启批处理的支持
        // rewriteBatchedStatements=true
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            long start = System.currentTimeMillis();
            connection = JDBCUtils.getConnection();
            String sql = "insert into goods(name) value(?)";

            ps = connection.prepareStatement(sql);
            for (int i = 0; i <= 20000; i++) {
                ps.setObject(1, "name_" + i);
                // 暂存sql
                ps.addBatch();

                if (i % 500 == 0){
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }

            long end = System.currentTimeMillis();
            System.out.println(end-start);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection, ps);
        }
    }

    @Test
    public void batchInsertTest03(){
        // 优化：所有的sql执行完之后，在进行commit
        //

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            long start = System.currentTimeMillis();
            connection = JDBCUtils.getConnection();
            // 禁止自动提交数据
            connection.setAutoCommit(false);
            String sql = "insert into goods(name) value(?)";

            ps = connection.prepareStatement(sql);
            for (int i = 0; i <= 20000; i++) {
                ps.setObject(1, "name_" + i);
                // 暂存sql
                ps.addBatch();

                if (i % 500 == 0){
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }

            connection.commit(); //提交数据
            long end = System.currentTimeMillis();
            System.out.println(end-start);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection, ps);
        }
    }


}
