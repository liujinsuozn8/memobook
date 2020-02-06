package com.ljs.test.filter.introduct;

import javax.servlet.*;
import java.io.IOException;

public class SecondFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter.init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("3. before Filter.doFilter");
        chain.doFilter(request, response);
        System.out.println("4. after Filter.doFilter");
    }

    @Override
    public void destroy() {

    }
}
