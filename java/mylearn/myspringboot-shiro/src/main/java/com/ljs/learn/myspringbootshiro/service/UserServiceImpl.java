package com.ljs.learn.myspringbootshiro.service;

import com.ljs.learn.myspringbootshiro.dao.UserMapper;
import com.ljs.learn.myspringbootshiro.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }
}
