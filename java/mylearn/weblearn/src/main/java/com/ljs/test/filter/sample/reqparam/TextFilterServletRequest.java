package com.ljs.test.filter.sample.reqparam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class TextFilterServletRequest extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public TextFilterServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        //过滤字符：xxx
        if (parameter.contains("xxx")){
            parameter = parameter.replaceAll("xxx", "***");
        }
        return parameter;
    }
}
