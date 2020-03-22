package com.ljs.mvc.dao;

import com.ljs.mvc.bean.CustomerSqlParam;
import com.ljs.mvc.bean.Customer;

import java.util.List;

public class CustomerDaoImpl extends BaseDao<Customer> implements CustomerDao {
    @Override
    public boolean hasCustomer(String name) {
        String sql = "select count(*) from customers where name = ?";
        long count = queryValue(sql, name);
        if (count > 0){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public Customer getCustomer(CustomerSqlParam param) {
        String sql = "select id, name, email from customers where id = ?";
        return queryRow(sql, param.getId());
    }

    @Override
    public List<Customer> getAll(CustomerSqlParam param) {
        String sql = "select id, name, email from customers where id like ? and name like ? and email like ?";
        return queryRows(sql, param.getId(),param.getName(), param.getEmail());
    }

    @Override
    public void deleteById(String id) {
        String sql = "delete from customers where id = ?";
        update(sql, id);
    }

    @Override
    public void updateCustomer(CustomerSqlParam param) {
        String sql = "update customers set name=?, email=? where id = ?";
        update(sql, param.getName(), param.getEmail(), param.getId());
    }

    @Override
    public void insertCustomer(CustomerSqlParam param) {
        String sql = "insert into customers (name,email) values (?, ?)";
        update(sql, param.getName(), param.getEmail());
    }
}
