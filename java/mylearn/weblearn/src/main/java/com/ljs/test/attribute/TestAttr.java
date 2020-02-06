package com.ljs.test.attribute;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TestAttr extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        // 1. 在Servlet中无法得到pageContext对象

        // 2.
        Object requestAttr = req.getAttribute("requestAttr");
        out.println("requestAttr : " + requestAttr);
        out.println("<br><br>");

        // 3. session
        Object sessionAttr = req.getSession().getAttribute("sessionAttr");
        out.println("sessionAttr : " + sessionAttr);
        out.println("<br>");

        // 4. application
        Object applicationAttr = getServletContext().getAttribute("applicationAttr");
        out.println("applicationAttr : " + applicationAttr);
        out.println("<br>");
    }
}
