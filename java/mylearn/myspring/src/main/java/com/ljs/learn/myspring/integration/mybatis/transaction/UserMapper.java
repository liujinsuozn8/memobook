package com.ljs.learn.myspring.integration.mybatis.transaction;

import com.ljs.learn.myspring.integration.mybatis.bean.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> getAllUsers();
    int addUser(Map map);
    int deleteUser(int id);
}
