package com.ljs.learn.base.ioc.base.dao;

public class UserDaoMySqlImpl implements UserDao {
    @Override
    public String getUserName() {
        return "Mysql User";
    }
}
