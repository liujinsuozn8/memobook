package com.ljs.learn.myspring.base.ioc.springBaseImprove.dao;

public class UserDaoMySqlImpl implements UserDao {
    @Override
    public String getUserName() {
        return "Mysql User";
    }
}
