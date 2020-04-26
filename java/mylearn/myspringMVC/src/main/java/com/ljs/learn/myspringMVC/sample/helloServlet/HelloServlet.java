package com.ljs.learn.myspringMVC.sample.helloServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if ("add".equals(method)){
            req.getSession().setAttribute("result","run add");
        } else if ("delete".equals(method)){
            req.getSession().setAttribute("result", "run delete");
        }

        req.getRequestDispatcher("/WEB-INF/jsp/hello01.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
