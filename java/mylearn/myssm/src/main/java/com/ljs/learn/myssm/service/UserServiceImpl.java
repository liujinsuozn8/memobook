package com.ljs.learn.myssm.service;

import com.ljs.learn.myssm.bean.User;
import com.ljs.learn.myssm.dao.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }
}
