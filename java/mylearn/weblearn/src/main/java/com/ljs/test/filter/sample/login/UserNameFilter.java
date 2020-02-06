package com.ljs.test.filter.sample.login;

import javax.servlet.*;
import java.io.IOException;

public class UserNameFilter implements Filter {
    private String username;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        username = filterConfig.getInitParameter("username");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("UserNameFilter.doFilter");

        //如果用户名相同，则请求放行
        if (username.equals(request.getParameter("username"))){
            chain.doFilter(request,response);
        } else {
            //用户名不同，则转发到login.jsp，并显示错误信息
            request.setAttribute("msg", "error username");
            request.getRequestDispatcher("/filter/sample/login/login.jsp").forward(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
