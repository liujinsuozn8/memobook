package com.ljs.test.session.resubmit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ReSubmitTestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 模拟网络延迟
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //从session获取token
        HttpSession session = req.getSession();
        Object sessionToken = session.getAttribute("token");

        // 从request获取token
        String requestToken = req.getParameter("token");

        System.out.println("sessionToken=" + sessionToken);
        System.out.println("requestToken=" + requestToken);

        //如果sessionToken存在，并且与requestToken相同，则接收请求
        //并从session中清除token，防止重复提交
        //否则跳转到提示页面
        if (sessionToken != null && requestToken.equals(sessionToken)){
            session.removeAttribute("token");
        } else {
            resp.sendRedirect(req.getContextPath() + "/session/resubmit/info.jsp");
            return;
        }

        String name = req.getParameter("name");
        System.out.println(name);

        // 转发到success页面
        req.getRequestDispatcher("/session/resubmit/success.jsp").forward(req, resp);
        // resp.sendRedirect(req.getContextPath() + "/session/resubmit/success.jsp");
    }
}
