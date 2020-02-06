package com.ljs.test.filter.introduct;

import javax.servlet.*;
import java.io.IOException;

public class HelloFilter implements Filter {
    // 与Servlet的过程类似


    // 在Filter被创建之后立刻被调用
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String name = filterConfig.getInitParameter("name");

        System.out.println("HelloFilter.init...");
        System.out.println("HelloFilter.init param : name = " + name);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("1. before HelloFilter.doFilter");
        // 放行请求
        chain.doFilter(request,response);
        System.out.println("2. after HelloFilter.doFilter");
    }

    @Override
    public void destroy() {
        System.out.println("HelloFilter.destroy");
    }
}
