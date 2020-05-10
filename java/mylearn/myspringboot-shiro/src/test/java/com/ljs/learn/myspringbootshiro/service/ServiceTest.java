package com.ljs.learn.myspringbootshiro.service;

import com.ljs.learn.myspringbootshiro.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testUser(){
        User user = userService.getUserByName("aaa");
        System.out.println(user);
    }
}
