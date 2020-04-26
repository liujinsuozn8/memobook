package com.ljs.learn.myspringMVC.restful.controller;

import com.ljs.learn.myspringMVC.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ParamController{
    @RequestMapping("/param")
    public String test(User user, Model model){
        model.addAttribute("msg", user.toString());
        return "/restful/result";
    }
}