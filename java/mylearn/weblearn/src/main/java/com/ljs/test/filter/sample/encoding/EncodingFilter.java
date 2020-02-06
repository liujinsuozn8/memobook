package com.ljs.test.filter.sample.encoding;

import com.ljs.test.filter.help.HttpFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter extends HttpFilter {
    private String encoding;

    @Override
    public void init() {
        encoding = getFilterConfig().getServletContext().getInitParameter("encoding");
    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println(encoding);
        // 为所有页面设置字符编码
        req.setCharacterEncoding(encoding);
        chain.doFilter(req, resp);
    }
}
