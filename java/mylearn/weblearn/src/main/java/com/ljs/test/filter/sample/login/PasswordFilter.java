package com.ljs.test.filter.sample.login;

import javax.servlet.*;
import java.io.IOException;

public class PasswordFilter implements Filter {
    private String password;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        password = filterConfig.getServletContext().getInitParameter("password");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("PasswordFilter.doFilter");

        // 密码等于1234时，请求放行
        if (password.equals(request.getParameter("password"))){
             chain.doFilter(request,response);
        } else{
            //密码不同，则转发到login.jsp，并显示错误信息
            request.setAttribute("msg", "error password");
            request.getRequestDispatcher("/filter/sample/login/login.jsp").forward(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
