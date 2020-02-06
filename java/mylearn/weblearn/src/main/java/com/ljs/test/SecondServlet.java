package com.ljs.test;

import javax.servlet.*;
import java.io.IOException;

public class SecondServlet implements Servlet {
    public SecondServlet() {
        System.out.println("SecondServlet constructor");
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("SecondServlet init");
    }

    @Override
    public ServletConfig getServletConfig() {
        System.out.println("SecondServlet getServletConfig");
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("SecondServlet service");
    }

    @Override
    public String getServletInfo() {
        System.out.println("SecondServlet getServletInfo");
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("SecondServlet destroy");
    }
}
