package com.ljs.learn.myspringannotation.jdbctemplate.jt01.service;

import com.ljs.learn.myspringannotation.jdbctemplate.jt01.dao.UserDao;
import com.ljs.learn.myspringannotation.jdbctemplate.jt01.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    // 添加
    public void addUser(User user){
        userDao.add(user);
    }

    // 修改
    public void updateUser(User user){
        userDao.updateUser(user);
    }

    // 删除
    public void deleteUser(String userId){
        userDao.deleteUser(userId);
    }

    // 查询表中的记录数
    public int findCount(){
        return userDao.selectCount();
    }

    // 查询返回一个对象
    public User findUser(String userId){
        return userDao.findUser(userId);
    }

    // 查询返回一个集合
    public List<User> findAll(){
        return userDao.findAllUser();
    }

    // 批量添加
    public void beachAdd(List<Object[]> batchArgs){
        userDao.batchAddUser(batchArgs);
    }

    // 批量修改
    public void batchUpdate(List<Object[]> batchArgs){
        userDao.batchUpdateUser(batchArgs);
    }

    // 批量删除
    public void batchDelete(List<Object[]> batchArgs){
        userDao.batchDeleteUser(batchArgs);
    }
}
