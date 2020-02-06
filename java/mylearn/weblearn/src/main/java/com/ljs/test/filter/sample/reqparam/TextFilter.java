package com.ljs.test.filter.sample.reqparam;

import com.ljs.test.filter.help.HttpFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TextFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        //使用自定义HttpServletRequest类来包装req
        chain.doFilter(new TextFilterServletRequest(req), resp);
    }
}
