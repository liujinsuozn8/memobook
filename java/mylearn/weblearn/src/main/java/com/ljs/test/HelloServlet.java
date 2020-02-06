package com.ljs.test;

import javax.servlet.*;
import java.io.IOException;


public class HelloServlet implements Servlet {
    public HelloServlet() {
        System.out.println("HelloServlet constructor");
    }

    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("init");
    }

    public ServletConfig getServletConfig() {
        System.out.println("getServletConfig");
        return null;
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("service");
    }

    public String getServletInfo() {
        System.out.println("getServletInfo");
        return null;
    }

    public void destroy() {
        System.out.println("destroy");
    }
}
