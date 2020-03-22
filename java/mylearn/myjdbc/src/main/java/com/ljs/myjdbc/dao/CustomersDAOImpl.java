package com.ljs.myjdbc.dao;

import com.ljs.myjdbc.bean.Customers;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class CustomersDAOImpl extends BaseDAO<Customers> implements CustomerDAO {
    @Override
    public void insert(Connection conn, Customers obj) {
        String sql = "insert into customers(name, email, birth) values(?, ?, ?)";
        update(conn, sql, obj.getName(), obj.getEmail(), obj.getBirth());
    }

    @Override
    public void deleteById(Connection conn, int id) {
        String sql = "delete from customers where id = ?";
        update(conn, sql, id);
    }

    @Override
    public void update(Connection conn, Customers obj) {
        String sql = "update customers set name=?, email=?, birth=? where id=?";
        update(conn, sql, obj.getName(), obj.getEmail(), obj.getBirth(), obj.getId());
    }

    @Override
    public Customers getCustomerById(Connection conn, int id) {
        String sql = "select id, name, email, birth from customers where id = ?";
        return getRow(conn, sql, id);
    }

    @Override
    public List<Customers> getAll(Connection conn) {
        String sql = "select id, name, email, birth from customers";
        return getRows(conn, sql);
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from customers";
        return getValue(conn, sql);
    }

    @Override
    public Date getMaxBirth(Connection conn) {
        String sql = "select max(birth) from customers";
        return getValue(conn, sql);
    }
}
