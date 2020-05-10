package com.ljs.learn.myspringbootshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
    @RequestMapping({"/", "/index.html"})
    public String index(Model model){
        model.addAttribute("msg", "helo world");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add(){
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping("tologin")
    public String tologin(){
        return "login";
    }

    // 登录时，进行认证和授权
    @RequestMapping("/login")
    public String login(String user, String pwd, Model model){
        // 获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        // 封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(user, pwd);
        // 执行登录，如果没有异常
        try{
            subject.login(token);
        } catch (UnknownAccountException e) {
            // 用户名不存在
            model.addAttribute("msg", "用户不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            // 密码不正确
            model.addAttribute("msg", "密码不正确");
            return "login";
        }

        return "index";
    }

    // 设置检测到未授权时的401页面
    @RequestMapping("/noauth")
    @ResponseBody
    public String unAuthorized(){
        return "unAuthorized";
    }

    // 登出操作
    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/index.html";
    }
}
