package com.ljs.learn.myspringbootswagger.controller;

import com.ljs.learn.myspringbootswagger.pojo.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

// @ApiModel("HelloController控制类") //无效
@Controller
public class HelloController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello world";
    }

    @ApiOperation("获取用户")
    @PostMapping("/user")
    @ResponseBody
    public User user(@RequestBody @ApiParam("用户名") String name){
        return new User(1, name, "testpwd", "testperms");
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(String str){
        return str+"xxxx";
    }
}
