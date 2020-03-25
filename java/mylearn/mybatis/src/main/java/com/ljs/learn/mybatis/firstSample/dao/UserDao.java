package com.ljs.learn.mybatis.firstSample.dao;


import com.ljs.learn.mybatis.common.bean.User;

import java.util.List;

public interface UserDao {
    // 获取所有用户信息
    List<User> getUsers();
}
