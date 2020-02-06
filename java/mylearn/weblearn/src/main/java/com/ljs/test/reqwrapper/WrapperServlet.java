package com.ljs.test.reqwrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WrapperServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //实例化自定义包装类
        MyHttpServletRequest myreq = new MyHttpServletRequest(req);
        //将请求转发到hello.jsp
        req.getRequestDispatcher("/reqwrapper/hello.jsp").forward(myreq, resp);
    }
}
