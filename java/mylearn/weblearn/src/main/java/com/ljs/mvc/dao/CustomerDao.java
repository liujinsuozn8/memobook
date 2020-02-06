package com.ljs.mvc.dao;

import com.ljs.mvc.bean.Customer;
import com.ljs.mvc.bean.CustomerSqlParam;

import java.util.List;

public interface CustomerDao {
    boolean hasCustomer(String name);
    Customer getCustomer(CustomerSqlParam param);
    List<Customer> getAll(CustomerSqlParam param);
    void deleteById(String id);
    void updateCustomer(CustomerSqlParam param);
    void insertCustomer(CustomerSqlParam param);
}
