package com.ljs.learn.base.ioc.springBaseImprove.service;

import com.ljs.learn.base.ioc.springBaseImprove.dao.UserDao;

public class UserServiceImpl implements UserService {

    // 将组合dao改为聚合dao，并在setter中完成依赖关系的传递
    private UserDao dao;

    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public void printUserName() {
        System.out.println(dao.getUserName());
    }
}
