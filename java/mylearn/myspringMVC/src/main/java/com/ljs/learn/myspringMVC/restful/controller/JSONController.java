package com.ljs.learn.myspringMVC.restful.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljs.learn.myspringMVC.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JSONController {
    @RequestMapping("/json/test01")
    @ResponseBody // 阻止视图解析器的解析
    public String test01(){
        User user = new User();
        user.setName("中文测试");
        user.setAge(18);
        return user.toString(); // 以字符串的形式返回
    }

    // 在注解中手动指定编码格式
    @RequestMapping(value = "/json/test02", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String test02() throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();

        User user = new User();
        user.setName("中文测试");
        user.setAge(18);

        // 返回json
        return objMapper.writeValueAsString(user);
    }

    // 使用springmvc-servlet.xml中的消息处理配置来自动转换json的编码
    @RequestMapping(value = "/json/test03")
    @ResponseBody
    public String test03() throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();

        User user = new User();
        user.setName("中文测试");
        user.setAge(18);

        // 返回json
        return objMapper.writeValueAsString(user);
    }
}
