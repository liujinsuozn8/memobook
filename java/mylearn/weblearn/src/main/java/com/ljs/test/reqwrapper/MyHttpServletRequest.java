package com.ljs.test.reqwrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyHttpServletRequest extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public MyHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    //修改获取paramter的方式，获取时，添加一些额外的文字
    @Override
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        return "before " + parameter + " end";
    }
}
