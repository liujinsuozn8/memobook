package com.ljs.test.dispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("TestServlet doget");

        // 测试当前的request 与 转发 和 重定向 的request对象是否相同
        System.out.println("TestServlet requestAttr : " + req.getAttribute("name"));
    }
}
