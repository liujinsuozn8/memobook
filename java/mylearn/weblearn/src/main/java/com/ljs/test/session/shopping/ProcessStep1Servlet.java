package com.ljs.test.session.shopping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

public class ProcessStep1Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取书名
        String[] books = req.getParameterValues("books");

        //将数据保存到session中
        HttpSession session = req.getSession();
        session.setAttribute("books", books);

        //并重定向到下一个页面
        resp.sendRedirect(req.getContextPath() + "/session/shopping/step2.jsp");
    }
}
