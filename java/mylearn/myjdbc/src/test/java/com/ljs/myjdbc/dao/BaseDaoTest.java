package com.ljs.myjdbc.dao;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BaseDaoTest {

    @Test
    public void queryRow() {
        CustomersDao dao = new CustomersDao();
        Customer customer = dao.queryRow(
            "select id, name, email, birth from customers where id = ?",
            "1"
        );
        System.out.println(customer);
    }

    @Test
    public void queryRows() {
        CustomersDao dao = new CustomersDao();
        List<Customer> customers = dao.queryRows(
                "select id, name, email, birth from customers where id > ?",
                10
        );
        System.out.println(customers.toString());
    }

    @Test
    public void queryValue() {
        CustomersDao dao = new CustomersDao();
        Long i = (Long) dao.queryValue(
                "select count(*) from customers where id > ?",
                10
        );
        System.out.println(i);
    }

    @Test
    public void update() {
        CustomersDao dao = new CustomersDao();
        dao.update(
                "update customers set name=?  where id = ?",
                "updateTest",
                34
        );
    }

    @Test
    public void delete(){
        CustomersDao dao = new CustomersDao();
        dao.update(
                "delete from customers where id = ?",
                33
        );
    }
}
