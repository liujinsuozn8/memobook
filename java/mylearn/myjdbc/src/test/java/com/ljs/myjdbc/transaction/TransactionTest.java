package com.ljs.myjdbc.transaction;

import com.ljs.myjdbc.bean.User;
import com.ljs.myjdbc.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionTest {
    //update user_table set balance = balance - 100 where user = 'AA'
    //update user_table set balance = balance + 100 where user = 'BB'
    @Test
    public void twoUpdateTest(){
        String sql1 = "update user_table set balance = balance - 100 where user = ?;";
        String sql2 = "update user_table set balance = balance + 100 where user = ?;";

        JDBCUtils.update(sql1, "AA");

        System.out.println(1/0);//如果出现异常无法保证操作的正确性

        JDBCUtils.update(sql2, "BB");
    }

    @Test
    public void safeUpdateTest(){
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            connection.setAutoCommit(false);
            String sql1 = "update user_table set balance = balance - 100 where user = ?;";
            String sql2 = "update user_table set balance = balance + 100 where user = ?;";

            JDBCUtils.safeUpdate(connection, sql1, "AA");

            System.out.println(1 / 0);//如果出现异常无法保证操作的正确性

            JDBCUtils.safeUpdate(connection, sql2, "BB");

            // 提交操作
            connection.commit();
        }catch (Exception e){
            e.printStackTrace();
            //回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            JDBCUtils.closeResources(connection, null);
        }
    }

    @Test
    public void transactionSelectTest() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        System.out.println(connection.getTransactionIsolation());
        // connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        connection.setAutoCommit(false);

        String sql = "select user, password, balance from user_table where user=? ";
        User user = JDBCUtils.safeGetRow(connection, User.class, sql, "CC");
        System.out.println(user);
    }

    @Test
    public void transactionUpdateTest() throws Exception {
        Connection connection = JDBCUtils.getConnection();

        connection.setAutoCommit(false);

        String sql = "update user_table set balance = ? where user=? ";
        JDBCUtils.safeUpdate(connection, sql,5000, "CC");
        Thread.sleep(10000);
        System.out.println("update end");
    }
}
