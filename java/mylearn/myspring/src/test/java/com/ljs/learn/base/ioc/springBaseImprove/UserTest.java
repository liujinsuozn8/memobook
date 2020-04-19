package com.ljs.learn.base.ioc.springBaseImprove;

import com.ljs.learn.base.ioc.springBaseImprove.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserTest {
    // 不修改任何代码，直接通过ID，从Spring中获取聚合了两种dao实例的service对象
    @Test
    public void test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("base/ioc/springBaseImprove/bean.xml");
        UserServiceImpl serviceMysql = (UserServiceImpl) context.getBean("serviceMysql");
        serviceMysql.printUserName();
    }

    @Test
    public void test02(){
        ApplicationContext context = new ClassPathXmlApplicationContext("base/ioc/springBaseImprove/bean.xml");
        UserServiceImpl serviceOracle = (UserServiceImpl) context.getBean("serviceOracle");
        serviceOracle.printUserName();
    }
}
