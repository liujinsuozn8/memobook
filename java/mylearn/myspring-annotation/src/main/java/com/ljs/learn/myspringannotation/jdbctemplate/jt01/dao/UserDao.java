package com.ljs.learn.myspringannotation.jdbctemplate.jt01.dao;

import com.ljs.learn.myspringannotation.jdbctemplate.jt01.entity.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    void updateUser(User user);

    void deleteUser(String userId);

    int selectCount();

    User findUser(String userId);

    List<User> findAllUser();


    void batchAddUser(List<Object[]> batchArgs);

    void batchUpdateUser(List<Object[]> batchArgs);

    void batchDeleteUser(List<Object[]> batchArgs);
}
