package com.ljs.mvc.controller;


import com.ljs.mvc.bean.Customer;
import com.ljs.mvc.bean.CustomerSqlParam;
import com.ljs.mvc.dao.CustomerDao;
import com.ljs.mvc.dao.CustomerDaoFactory;
import com.ljs.mvc.dao.CustomerDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class ControllerServlet extends HttpServlet {
    // CustomerDao dao = new CustomerDaoImpl();

    //通过修改配置文件，来从工厂类中获取不同的Dao实现
    CustomerDao dao = CustomerDaoFactory.getInstance().getDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        controller(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        controller(req, resp);
    }

    private void controller(HttpServletRequest req, HttpServletResponse resp){
        String[] split = req.getServletPath().split("/");
        String actionPath = split[split.length - 1];
        // /action.do ,将action截取出来
        String action = actionPath.substring(0, actionPath.length() - 3);
        try {
            Method method = getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果action异常，则跳转到异常页面
            try {
                req.getRequestDispatcher("/WEB-INF/mvc/noAction.jsp").forward(req,resp);
            } catch (ServletException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }

    // 检索页面
    private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerSqlParam search = new CustomerSqlParam(
                req.getParameter("id"),
                req.getParameter("name"),
                req.getParameter("email"),
                true
        );

        //进行模糊检索
        List<Customer> all = dao.getAll(search);
        req.setAttribute("allData", all);
        //重定向到index.jsp，并显示结果
        req.getRequestDispatcher("/mvc/index.jsp").forward(req,resp);
    }

    // 修改页面，通过id查询一条信息，并跳转到指定页面
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        CustomerSqlParam param = new CustomerSqlParam(id, null, null, false);
        Customer customer = dao.getCustomer(param);

        req.setAttribute("customer", customer);
        req.getRequestDispatcher("/mvc/edit.jsp").forward(req,resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        String oldname = req.getParameter("oldname");
        String name = req.getParameter("name");
        String email = req.getParameter("email");

        CustomerSqlParam param = new CustomerSqlParam(id, name, email, false);

        //如果用户已经存在
        if (!oldname.equals(name) && dao.hasCustomer(param.getName())) {
            req.setAttribute("msg", "customer exist");
            req.getRequestDispatcher("/mvc/edit.jsp").forward(req, resp);
            return;
        }

        dao.updateCustomer(param);
        resp.sendRedirect("query.do");
    }

    // 删除
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        dao.deleteById(id);

        resp.sendRedirect("deleteSuccess.jsp");
    }

    // 添加
    private void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerSqlParam param = new CustomerSqlParam(
                req.getParameter("id"),
                req.getParameter("name"),
                req.getParameter("email"),
                false
        );

        // 如果name已经存在，则转发会insert页面，并显示错误信息
        if (dao.hasCustomer(param.getName())) {
            req.setAttribute("msg", "customer exists");
            req.getRequestDispatcher("/mvc/insert.jsp").forward(req, resp);
        } else {
            // 如果name不存在，则进行添加
            dao.insertCustomer(param);
            resp.sendRedirect("insertSuccess.jsp");
        }
    }
}
