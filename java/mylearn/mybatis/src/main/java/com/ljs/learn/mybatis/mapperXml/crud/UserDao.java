package com.ljs.learn.mybatis.mapperXml.crud;


import com.ljs.learn.mybatis.common.bean.User;

import java.util.List;

public interface UserDao {
    // 获取所有用户信息
    List<User> getUsers();

    // 根据ID查询用户
    User getUserById(int id);

    // 插入用户
    int insertUser(User user);

    // 更新用户
    int updateUser(User user);

    // 删除用户
    int deleteUserById(int id);
}
