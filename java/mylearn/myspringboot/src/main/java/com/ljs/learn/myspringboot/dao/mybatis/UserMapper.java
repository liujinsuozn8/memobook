package com.ljs.learn.myspringboot.dao.mybatis;

import com.ljs.learn.myspringboot.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> getUsers();
    User getUserById(int id);
    void insertUser(User u);
    void updateUser(User u);
    void deleteUserById(int id);
}
