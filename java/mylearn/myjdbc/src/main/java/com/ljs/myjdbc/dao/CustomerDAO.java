package com.ljs.myjdbc.dao;

import com.ljs.myjdbc.bean.Customers;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface CustomerDAO {
    // 定义针对表Customer的常用操作

    // 插入数据
    void insert(Connection conn, Customers obj);

    // 根据id删除数据
    void deleteById(Connection conn, int id);

    // 根据传入的对象来修改数据
    void update(Connection conn, Customers obj);

    // 根据指定id查询数据
    Customers getCustomerById(Connection conn, int id);

    // 查询所有数据
    List<Customers> getAll(Connection conn);

    // 返回数据量
    Long getCount(Connection conn);

    // 返回最大的生日
    Date getMaxBirth(Connection conn);
}
