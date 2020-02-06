package com.ljs.test.filter.help;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class HttpFilter implements Filter {
    private FilterConfig filterConfig;

    // 对外提供一个可以获取filterConfig引用的方法
    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //保存 filterConfig对象的引用
        this.filterConfig = filterConfig;
        init();
    }

    // 供子类重写初始化方法
    public void init() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //参数类型强转
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        doFilter(req, resp, chain);
    }

    // 抽象方法，让子类来提供具体的实现
    public abstract void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException;

    @Override
    public void destroy() {

    }
}
