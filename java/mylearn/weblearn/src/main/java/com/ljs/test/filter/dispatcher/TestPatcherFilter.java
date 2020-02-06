package com.ljs.test.filter.dispatcher;

import com.ljs.test.filter.help.HttpFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestPatcherFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("before TestPatcherFilter.doFilter");

        chain.doFilter(req,resp);

        System.out.println("after TestPatcherFilter.doFilter");
    }
}
