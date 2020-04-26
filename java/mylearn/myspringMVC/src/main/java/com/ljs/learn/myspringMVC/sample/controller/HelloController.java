package com.ljs.learn.myspringMVC.sample.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        ModelAndView mv = new ModelAndView();

        // model部分： 封装对象保存到ModelAndView
        mv.addObject("msg", "HelloSpringMVC");
        // view部分： 封装跳转的目标视图，保存到ModelView中
        mv.setViewName("sample/hello02");
        return mv;
    }
}
