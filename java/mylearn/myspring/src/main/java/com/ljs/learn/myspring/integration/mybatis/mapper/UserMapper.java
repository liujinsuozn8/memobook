package com.ljs.learn.myspring.integration.mybatis.mapper;

import com.ljs.learn.myspring.integration.mybatis.bean.User;

import java.util.List;

public interface UserMapper {
    List<User> getAllUsers();
}
