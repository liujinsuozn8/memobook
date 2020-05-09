package com.ljs.learn.myspringboot.controller.mybatis;

import com.ljs.learn.myspringboot.bean.User;
import com.ljs.learn.myspringboot.dao.mybatis.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MybatisController {
    // 自动装配mapper

    @Autowired
    UserMapper dao;

    // 查询所有用户
    @RequestMapping("/mybatis/user/userlist")
    public List<User> userList(){
        List<User> users = dao.getUsers();
        return users;
    }

    // 查询
    @RequestMapping("/mybatis/user/get/{id}")
    public User userList(@PathVariable("id") int id){
        User users = dao.getUserById(id);
        return users;
    }

    // 添加
    @RequestMapping("/mybatis/user/adduser")
    public String addUser(){
        String sql = "insert into test01.user(id, name, pwd) values(, )";
        dao.insertUser(new User(200, "xxxx", "xxxpwd"));
        return "add end";
    }

    // 修改
    @RequestMapping("/mybatis/user/upuser/{id}")
    public String updateUser(@PathVariable("id") int id){
        dao.updateUser(new User(id, "yyy", "yyypwd"));
        return "update end";
    }
    // 删除
    @RequestMapping("/mybatis/user/deluser/{id}")
    public String deleteUser(@PathVariable("id") int id){
        dao.deleteUserById(id);
        return "delete end";
    }
}
