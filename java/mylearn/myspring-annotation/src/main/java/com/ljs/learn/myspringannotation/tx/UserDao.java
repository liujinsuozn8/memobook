package com.ljs.learn.myspringannotation.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public class UserDao {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public void insert() {
        // 1. 插入
        String sql = "insert into `tbl_user`(username,age) values (?, ?)";
        String username = UUID.randomUUID().toString().substring(0, 5);
        jdbcTemplate.update(sql, username, 19);
        System.out.println("dao 插入完成");

        // 2. 模拟失败的db操作
        int i = 10/0;
    }
}
