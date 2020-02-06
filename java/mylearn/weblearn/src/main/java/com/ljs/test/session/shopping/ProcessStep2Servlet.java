package com.ljs.test.session.shopping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProcessStep2Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面信息并保存到类对象中
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String cardType = req.getParameter("cardType");
        String cardId = req.getParameter("cardId");

        Customer customer = new Customer(name, address, cardType, cardId);

        //将数据保存到session中
        HttpSession session = req.getSession();
        session.setAttribute("customer", customer);

        //重定向到下一个页面
        resp.sendRedirect(req.getContextPath() + "/session/shopping/step3.jsp");
    }
}
