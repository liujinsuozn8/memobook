package com.ljs.learn.myspringMVC.restful.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljs.learn.myspringMVC.bean.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class JSON2Controller {
    @RequestMapping("/json02/test01")
    public String test01() throws JsonProcessingException {
        User u1 = new User("aaa", 11);
        User u2 = new User("中文测试", 13);
        User u3 = new User("bbb", 15);

        List<User> list = new ArrayList<>(3);
        list.add(u1);
        list.add(u2);
        list.add(u3);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(list);
    }
}
