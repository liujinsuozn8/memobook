package com.ljs.learn.myspringboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello/test01")
    public String test01(){
        return "hello test01";
    }
}
