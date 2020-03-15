package com.ljs.learn.ioc.base.dao;

public class UserDaoOracleImpl implements UserDao {
    @Override
    public String getUserName() {
        return "Oracle User";
    }
}
