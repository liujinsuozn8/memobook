package com.ljs.learn.myspringMVC.restful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ModelController {
    @RequestMapping("/model")
    public String test(ModelMap model){
        return "/restful/result";
    }
}
