package com.ljs.test.myservlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

public class LoginServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("new request is coming!!!");
        System.out.println(servletRequest);

        // servletRequest
        // 1. getParameter
        String user = servletRequest.getParameter("user");
        System.out.println("param_user = " + user);

        String password = servletRequest.getParameter("password");
        System.out.println("param_password = " + password);

        // 2. getParameterValues
        // 如果该参数有多个值，通过getParameter只能获取到第一个值
        String interesting = servletRequest.getParameter("interesting");
        System.out.println("param_interesting = " + interesting);

        String[] interestings = servletRequest.getParameterValues("interesting");
        for (String interest : interestings) {
            System.out.println("param_interesting_list = " + interest);
        }

        // 3. getParameterNames
        System.out.println("getParameterNames test : ");
        // servletRequest.getParameterNames().asIterator().forEachRemaining(
        //         name -> System.out.println(name + "  : " + servletRequest.getParameter(name))
        // );
        Enumeration<String> names = servletRequest.getParameterNames();
        while (names.hasMoreElements()){
            String name = names.nextElement();
            String[] value = servletRequest.getParameterValues(name);
            System.out.println("name = " + name + ", value = " + Arrays.toString(value));
        }

        // 4.getParameterMap
        System.out.println("getParameterMap test:");
        Map<String, String[]> parameterMap = servletRequest.getParameterMap();
        parameterMap.forEach(
                (k, v) -> System.out.println("k = " + k + ", v = " + Arrays.toString(v))
        );

        // 5. 获取uri
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        StringBuffer url = httpRequest.getRequestURL();
        System.out.println("url = " + url.toString());

        String uri = httpRequest.getRequestURI();
        System.out.println("uri = " + uri);

        // 6. 获取请求方式
        String method = httpRequest.getMethod();
        System.out.println("method = " + method);

        // 7. 获取请求字符串
        String queryString = httpRequest.getQueryString();
        System.out.println("queryString = " + queryString);

        // 8. 获取servlet名
        String servletPath = httpRequest.getServletPath();
        System.out.println("servletPath = " + servletPath);

        // servletResponse
        // 返回响应
        PrintWriter out = servletResponse.getWriter();
        out.write("helloworld");

        // 返回响应类型
        // 设置响应类型为excel
        servletResponse.setContentType("application/vnd.ms-excel");

        // 使用输出流
        // servletResponse.getOutputStream()
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
