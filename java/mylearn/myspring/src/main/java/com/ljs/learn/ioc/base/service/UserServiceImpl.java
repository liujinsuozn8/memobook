package com.ljs.learn.ioc.base.service;

import com.ljs.learn.ioc.base.dao.UserDao;
import com.ljs.learn.ioc.base.dao.UserDaoMySqlImpl;
import com.ljs.learn.ioc.base.dao.UserDaoOracleImpl;

public class UserServiceImpl implements UserService {

    // 组合一个UserDao
    // private UserDao dao = new UserDaoMySqlImpl();
    // 当需求发生变化时，需要直接修改代码，替换接口实现
    private UserDao dao = new UserDaoOracleImpl();

    @Override
    public void printUserName() {
        System.out.println(dao.getUserName());
    }
}
