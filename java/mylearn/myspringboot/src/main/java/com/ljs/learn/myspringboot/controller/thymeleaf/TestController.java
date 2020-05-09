package com.ljs.learn.myspringboot.controller.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @RequestMapping("/thymeleaf/test01")
    public String test01(Model model){
        model.addAttribute("msg", "th test");
        return "test";
    }
}
