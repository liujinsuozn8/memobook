package com.ljs.learn.ioc.improve;

import com.ljs.learn.ioc.improve.dao.UserDaoMySqlImpl;
import com.ljs.learn.ioc.improve.dao.UserDaoOracleImpl;
import com.ljs.learn.ioc.improve.service.UserService;
import com.ljs.learn.ioc.improve.service.UserServiceImpl;
import org.junit.Test;

public class IocImporveTest {
    @Test
    public void test01(){
        // 实例化service对象的同时，注入dao的依赖
        UserService service01 = new UserServiceImpl(new UserDaoMySqlImpl());
        service01.printUserName();

        System.out.println("-----");

        // 当需求变化，需要改变dao的实现时，不需要修改源码，直接修改注入的dao对象
        UserService service02 = new UserServiceImpl(new UserDaoOracleImpl());
        service02.printUserName();

    }
}
