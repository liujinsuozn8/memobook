package com.ljs.mvc.dao;

import com.ljs.mvc.bean.Customer;
import com.ljs.mvc.bean.CustomerSqlParam;

import java.util.ArrayList;
import java.util.List;

public class CustomerXmlImpl implements CustomerDao {
    @Override
    public boolean hasCustomer(String name) {
        return false;
    }

    @Override
    public Customer getCustomer(CustomerSqlParam param) {
        return null;
    }

    @Override
    public List<Customer> getAll(CustomerSqlParam param) {
        return new ArrayList<Customer>();
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void updateCustomer(CustomerSqlParam param) {

    }

    @Override
    public void insertCustomer(CustomerSqlParam param) {

    }
}
