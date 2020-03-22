package com.ljs.myjdbc.preparedstatement;

import com.ljs.myjdbc.bean.Customers;
import com.ljs.myjdbc.util.JDBCUtils;
import org.junit.Test;

import java.util.List;

// 通用查询
public class PreparedStatementQueryTest {

    @Test
    public void customerTest(){
        String sql = "select name, email, birth from customers where id=?;";
        Customers customers = JDBCUtils.getRow(Customers.class, sql, 1);
        System.out.println(customers);
    }

    @Test
    public void rowsTest(){
        String sql = "select name, email, birth from customers where id=? or id=?;";
        List<Customers> rows = JDBCUtils.getRows(Customers.class, sql, 1,2);
        System.out.println(rows);
    }
}
