package com.ljs.learn.myspringbootsample.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 登录成功之后，会有用户session
        Object loginUser = request.getSession().getAttribute("loginUser");

        if (loginUser == null){
            //未登录
            request.setAttribute("msg","未登录");
            request.getRequestDispatcher("/index.html").forward(request, response);
            // response.sendRedirect("index.html");
            return false;
        } else {
            return true;
        }
    }
}
