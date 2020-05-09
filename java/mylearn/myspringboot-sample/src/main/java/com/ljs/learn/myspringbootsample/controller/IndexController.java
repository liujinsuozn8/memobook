package com.ljs.learn.myspringbootsample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    // 逻辑移动到 MyMvcController
    // @RequestMapping({"/", "/index.html"})
    // public String index(){
    //     return "index";
    // }
}
