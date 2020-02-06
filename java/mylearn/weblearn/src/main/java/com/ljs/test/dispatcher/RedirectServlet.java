package com.ljs.test.dispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* 重定向
* */
public class RedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RedirectServlet doget");

        // 设置属性，并在目标Servlet进行检测
        req.setAttribute("name", "request from RedirectServlet");
        System.out.println("RedirectServlet requestAttr : " + req.getAttribute("name"));

        // 执行请求的重定向，直接调用respnose.sendRedirect(path)方法
        // path为重定向目标的地址

        // 绝对路径会直接从服务器根目录下搜索，即：http://localhost:8080/
        String path = "/weblearn_war_exploded/dispatcher/testServlet";
        // 相对路径会接在当前目录下搜索
        // String path = "testServlet";
        resp.sendRedirect(path);
    }
}
