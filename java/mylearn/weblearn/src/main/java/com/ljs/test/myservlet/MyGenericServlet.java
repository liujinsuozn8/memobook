package com.ljs.test.myservlet;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;

/*
* 自定义的一个Servlet接口的实现类
* 让开发的任何Servlet都继承该类，以简化开发*/
public abstract class MyGenericServlet implements Servlet, ServletConfig {
    ServletConfig servletConfig;
    @Override
    public void init(ServletConfig config) throws ServletException {
        servletConfig = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    public abstract void service(ServletRequest req, ServletResponse res) throws ServletException, IOException;

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }

    ////////////////////////////
    // ServletConfig接口方法
    // 无法直接提供响应的实现，
    // 但是可以通过init中获取的ServletConfig来参数值
    @Override
    public String getServletName() {
        return servletConfig.getServletName();
    }

    @Override
    public ServletContext getServletContext() {
        return servletConfig.getServletContext();
    }

    @Override
    public String getInitParameter(String name) {
        return servletConfig.getInitParameter(name);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return servletConfig.getInitParameterNames();
    }
}
