package com.ljs.learn.myspringMVC.restful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RestFulController {

    // 请求url： /add?aaa=1&bbbb=2
    @RequestMapping("/add")
     public String testX(@RequestParam("aaa") int a, @RequestParam("bbb") int b, Model model){
        int result = a + b;
        model.addAttribute("msg", "result="+result);
        return "/restful/result";
     }

    // 请求url： /add/1/2
    @RequestMapping("/add/{a}/{b}")
    public String test(@PathVariable int a, @PathVariable int b, Model model){
        int result = a + b;
        model.addAttribute("msg", "result="+result);
        return "/restful/result";
    }

    // 请求url： /add/1/2/3 + POST
    @RequestMapping(value = "/add/{a}/{b}/{c}", method = RequestMethod.POST)
    public String test02(@PathVariable String a, @PathVariable String b, @PathVariable String c, Model model){
        String result = a + b + c;
        model.addAttribute("msg", "result="+result);
        return "/restful/result";
    }

    // 请求url： /add/1/2 + POST
    @PostMapping("/add/{a}/{b}")
    public String test03(@PathVariable String a, @PathVariable String b, Model model){
        String result = a + b;
        model.addAttribute("msg", "result="+result);
        return "/restful/result";
    }
}
