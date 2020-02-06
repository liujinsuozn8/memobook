package com.ljs.test.filter.sample.authority.filters;

import com.ljs.test.filter.help.HttpFilter;
import com.ljs.test.filter.sample.authority.bean.Authority;
import com.ljs.test.filter.sample.authority.bean.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        // 从session中获取用户信息
        HttpSession session = req.getSession();
        User userInfo = (User) session.getAttribute("userInfo");

        //首先检查session是否存在信息，如果不存在则重定向到登录页面
        if (userInfo == null){
            resp.sendRedirect(req.getContextPath() + "/filter/sample/authority/login.jsp");
            return;
        }

        //从请求中获取servlet路径
        String servletPath = req.getServletPath();

        //在用户的权限信息中搜索servlet路径
        boolean hasAuthority = false;
        for (Authority authority : userInfo.getAuthorities()) {
            if (authority.getUrl().equals(servletPath)){
                hasAuthority = true;
                break;
            }
        }

        // 如果存在，则放行
        if (hasAuthority){
            chain.doFilter(req,resp);
        } else {
            // 不存在则重定向到403.jsp
            resp.sendRedirect(req.getContextPath() + "/filter/sample/authority/403.jsp");
        }
    }
}
