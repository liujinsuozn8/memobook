package com.ljs.learn.myspringMVC.restful.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljs.learn.myspringMVC.bean.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FastjsonController {
    @RequestMapping("/json03/test01")
    public String test01(){
        User user = new User("中文测试", 20);
        return JSONObject.toJSONString(user);
    }
}
