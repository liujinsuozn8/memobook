package com.ljs.learn.myspring.base.ioc.improve.dao;

public class UserDaoOracleImpl implements UserDao {
    @Override
    public String getUserName() {
        return "Oracle User";
    }
}
