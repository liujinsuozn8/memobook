package com.ljs.learn.myspring.base.ioc.base;

import com.ljs.learn.myspring.base.ioc.base.service.UserService;
import com.ljs.learn.myspring.base.ioc.base.service.UserServiceImpl;
import org.junit.Test;

public class IocBaseTest {
    @Test
    public void test01(){
        UserService service = new UserServiceImpl();
        service.printUserName();
    }
}
