package com.ljs.learn.ioc.base;

import com.ljs.learn.ioc.base.service.UserService;
import com.ljs.learn.ioc.base.service.UserServiceImpl;
import org.junit.Test;

public class IocBaseTest {
    @Test
    public void test01(){
        UserService service = new UserServiceImpl();
        service.printUserName();
    }
}
