package com.ljs.learn.myspring.integration.mybatis.transaction;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserMapperTest {
    @Test
    public void testTransaction(){
        ClassPathXmlApplicationContext content = new ClassPathXmlApplicationContext("integration/mybatis/transaction/applicationContext.xml");
        UserMapperImpl userMapper = (UserMapperImpl) content.getBean("userMapper");
        // userMapper.getAllUsers();
        userMapper.transcation();
    }
}
