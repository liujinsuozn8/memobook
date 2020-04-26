package com.ljs.learn.myssm.controller;

import com.ljs.learn.myssm.bean.User;
import com.ljs.learn.myssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @RequestMapping("/user/all")
    public String getAllUser(Model model){
        List<User> allUser = userService.getAllUser();
        model.addAttribute("users", allUser);
        return "result";
    }
}
