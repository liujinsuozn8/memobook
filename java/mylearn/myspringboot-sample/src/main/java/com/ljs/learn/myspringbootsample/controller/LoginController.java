package com.ljs.learn.myspringbootsample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model, HttpSession session){
        // 实际业务
        if(!StringUtils.isEmpty(username) && "123".equals(password)){
            // return "dashboard";
            session.setAttribute("loginUser", username);
            // 重定向到指定页面
            return "redirect:/main.html";
        }else{
            //返回失败信息到登录页面
            model.addAttribute("msg", "登录失败");
            return "index";
        }
    }

    @RequestMapping("/user/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index.html";
    }
}
