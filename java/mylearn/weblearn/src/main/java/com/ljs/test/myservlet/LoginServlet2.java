package com.ljs.test.myservlet;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet2 extends GenericServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        PrintWriter writer = res.getWriter();

        String reqUser = req.getParameter("user");
        String reqPassword = req.getParameter("password");
        String initUser = getServletContext().getInitParameter("user");
        String initPassword = getServletContext().getInitParameter("password");
        if (!initUser.equals(reqUser) || !initPassword.equals(reqPassword)){
            writer.write("Sorry : " + reqUser);
        } else {
            writer.write("Hello : " + reqUser);
        }
    }
}
