package com.ljs.test;

import javax.servlet.*;
import java.io.IOException;
import java.io.InputStream;

public class ParamInitServlet implements Servlet {
    public ParamInitServlet() {
        System.out.println("ParamInitServlet");
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("ParamInitServlet init");

        /*
        ServletConfig
        */
        //输出初始化参数
        System.out.println(servletConfig.getInitParameter("user"));
        servletConfig.getInitParameterNames()
                .asIterator()
                .forEachRemaining(
                        (name) -> System.out.println(name + ":" + servletConfig.getInitParameter((String) name))
                );

        System.out.println(servletConfig.getServletName());

        //从ServletContext中获取当前web应用的初始化参数
        ServletContext context = servletConfig.getServletContext();

        /*
        ServletContext
        */
        //获取web应用的初始化参数
        System.out.println("driver : " + context.getInitParameter("driver"));
        context.getInitParameterNames()
                .asIterator()
                .forEachRemaining(
                        (name) -> System.out.println("initParam : " + name + ":" + context.getInitParameter((String) name))
                );

        // 获取当前web应用的某一个文件在服务器上的绝对路径，不是部署前的路径
        String realPath = context.getRealPath("/mycode.png");
        System.out.println("realPath : " + realPath);

        // 获取当前web应用的名称
        String contextPath = context.getContextPath();
        System.out.println(contextPath);

        // 获取当前web应用的某一个文件对应的输入流
        // path 的/为当前web应用的根目录
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream("jdbc.properties");
        System.out.println("is = " + is);

        //路径需要使用相对于web应用的路径
        InputStream is2 = context.getResourceAsStream("WEB-INF/classes/jdbc.properties");
        System.out.println("is2 = " + is2);
    }

    @Override
    public ServletConfig getServletConfig() {
        System.out.println("ParamInitServlet getServletConfig");
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("ParamInitServlet service");
    }

    @Override
    public String getServletInfo() {
        System.out.println("ParamInitServlet getServletInfo");
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("ParamInitServlet destroy");
    }
}
