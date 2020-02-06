package com.ljs.test.dispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* 转发Servlet
* */
public class ForwardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ForwardServlet doget");

        // 设置属性，并在目标Servlet进行检测
        req.setAttribute("name", "request from ForwardServlet");
        System.out.println("ForwardServlet requestAttr : " + req.getAttribute("name"));

        // 请求的转发
        // 1. 获取 RequestDispatcher 对象
        // 调用HttpServletRequest 的 getRequestDispatcher() 方法 获取 RequestDispatcher 对象
        // 调用getRequestDispatcher()时，需要传入要转发的地址

        // 绝对路径会从当前应用的根目录开始搜索，即：http://localhost:8080/weblearn_war_exploded/
        // String path = "/dispatcher/testServlet";
        // 相对路径会从当前目录开始搜索
        String path = "testServlet";
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);

        // 2. 调用HttpServletRequest的forward(request, response)进行请求转发
        requestDispatcher.forward(req,resp);
    }
}
