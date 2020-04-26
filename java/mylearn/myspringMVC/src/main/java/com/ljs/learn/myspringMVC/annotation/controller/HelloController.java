package com.ljs.learn.myspringMVC.annotation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/helloController")
public class HelloController {
    // 该方法的真实访问路径，如：localhost:8080/helloController/hello
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("msg", "SpringMVC annotation");
        return "annotation/hello01"; // 转发的目标页面，由视图解析器进行处理
    }
}
