package com.ljs.test.filter.sample.authority.controller;

import com.ljs.test.filter.sample.authority.bean.Authority;
import com.ljs.test.filter.sample.authority.bean.User;
import com.ljs.test.filter.sample.authority.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ControllerServlet extends HttpServlet {
    public UserDao dao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        controller(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        controller(req,resp);
    }

    private void controller(HttpServletRequest req, HttpServletResponse resp){
        String method = req.getParameter("method");
        Method action = null;
        try {
            action = getClass().getDeclaredMethod(method, HttpServletRequest.class, HttpServletResponse.class);
            action.invoke(this, req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req,resp);
            } catch (ServletException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //获取用户信息
    private void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从请求参数中获取用户名
        String username = req.getParameter("username");
        User user = dao.get(username);
        if (user == null){
            req.setAttribute("msg", "username is not exists");
        } else {
            //如果用户数据获取成功，保存信息，并获取通用权限数据
            req.setAttribute("user", user);
            List<Authority> authorities = UserDao.getCommonAuthorities();
            req.setAttribute("commonAuthorities", authorities);
        }

        //重新转发到authorityManage页面
        req.getRequestDispatcher("/filter/sample/authority/authorityManage.jsp").forward(req, resp);
    }

    //更新用户信息
    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 从req中获取请求参数
        String username = req.getParameter("username");
        String[] selectedUrls = req.getParameterValues("selectedUrls");

        //获取通用权限信息
        List<Authority> commonAuthorities = UserDao.getCommonAuthorities();

        //根据请求参数，从通用权限信息中获取需要更新的数据
        List<Authority> updateAuthorities = new ArrayList<>();
        for (String url : selectedUrls) {
            for (Authority common : commonAuthorities) {
                //如果匹配则保存
                if (common.getUrl().equals(url)){
                    updateAuthorities.add(common);
                    break;
                }
            }
        }

        // 更新用户信息
        dao.update(username, updateAuthorities);

        //重新转发到authorityManage页面
        req.getRequestDispatcher("/filter/sample/authority/authorityManage.jsp").forward(req, resp);
    }

    // 登录
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String username = req.getParameter("username");
        User userInfo = dao.get(username);

        if (userInfo == null){
            // 失败则转发回login.jsp，显示错误信息
            req.setAttribute("msg", "user is not exists");
            req.getRequestDispatcher("/filter/sample/authority/login.jsp").forward(req, resp);
        } else {
            // 成功则将用户信息保存到session中，并跳转到pageList.jsp
            HttpSession session = req.getSession();
            session.setAttribute("userInfo", userInfo);

            resp.sendRedirect(req.getContextPath() + "/filter/sample/authority/pageList.jsp");
        }
    }

    public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session = req.getSession();
        session.invalidate();

        resp.sendRedirect(req.getContextPath() + "/filter/sample/authority/index.jsp");
    }

}
