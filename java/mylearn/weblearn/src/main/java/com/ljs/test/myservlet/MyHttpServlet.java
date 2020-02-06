package com.ljs.test.myservlet;

import com.ljs.test.myservlet.MyGenericServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* 针对HTTP协议定义的一个Servlet基类*/
public abstract class MyHttpServlet extends MyGenericServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        if (req instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest)req;

            if (res instanceof HttpServletResponse){
                HttpServletResponse response = (HttpServletResponse)res;

                service(request, response);
            }
        }
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //1. 获取请求方式
        String method = request.getMethod();

        //2. 根据请求方式调用对应的处理方法
        if ("GET".equalsIgnoreCase(method)){
            doGet(request, response);
        }

        if ("POST".equalsIgnoreCase(method)){
            doPost(request, response);
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    }

}
