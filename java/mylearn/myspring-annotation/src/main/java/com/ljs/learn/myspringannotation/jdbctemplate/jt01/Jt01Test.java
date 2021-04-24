package com.ljs.learn.myspringannotation.jdbctemplate.jt01;

import com.ljs.learn.myspringannotation.jdbctemplate.jt01.entity.User;
import com.ljs.learn.myspringannotation.jdbctemplate.jt01.service.UserService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class Jt01Test {
    @Test
    public void testInsert(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate/bean.xml");
        UserService userService = context.getBean("userService", UserService.class);
        User user = new User();
        user.setUserId("1234567890");
        user.setUsername("aaaa");
        user.setUstatus("sa");
        userService.addUser(user);
    }

    @Test
    public void testDelete(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate/bean.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.deleteUser("1234567890");
    }

    @Test
    public void testUpdate(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate/bean.xml");
        UserService userService = context.getBean("userService", UserService.class);
        User user = new User();
        user.setUserId("1234567890");
        user.setUsername("uxxx");
        user.setUstatus("xxxx");
        userService.updateUser(user);
    }

    @Test
    public void testSelectCount(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate/bean.xml");
        UserService userService = context.getBean("userService", UserService.class);
        int count = userService.findCount();
        System.out.println("count = " + count);
    }

    @Test
    public void testSelectObject(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate/bean.xml");
        UserService userService = context.getBean("userService", UserService.class);
        User user = userService.findUser("1234567890");
        System.out.println("user = " + user);
    }

    @Test
    public void testSelectList(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate/bean.xml");
        UserService userService = context.getBean("userService", UserService.class);
        List<User> all = userService.findAll();
        System.out.println(all);
    }

    @Test
    public void testBatchInsert(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate/bean.xml");
        UserService userService = context.getBean("userService", UserService.class);
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] o1 = {"1","a1", "a1s"};
        Object[] o2 = {"2","a2", "a2s"};
        Object[] o3 = {"3","a3", "a3s"};
        batchArgs.add(o1);
        batchArgs.add(o2);
        batchArgs.add(o3);

        userService.beachAdd(batchArgs);
    }

    @Test
    public void testBatchUpdate(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate/bean.xml");
        UserService userService = context.getBean("userService", UserService.class);
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] o1 = {"a11", "a11s", "1"};
        Object[] o2 = {"a22", "a22s", "2"};
        Object[] o3 = {"a33", "a33s", "3"};
        batchArgs.add(o1);
        batchArgs.add(o2);
        batchArgs.add(o3);

        userService.batchUpdate(batchArgs);
    }

    @Test
    public void testBatchDelete(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate/bean.xml");
        UserService userService = context.getBean("userService", UserService.class);
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] o1 = {"1"};
        Object[] o3 = {"3"};
        batchArgs.add(o1);
        batchArgs.add(o3);

        userService.batchDelete(batchArgs);
    }
}
