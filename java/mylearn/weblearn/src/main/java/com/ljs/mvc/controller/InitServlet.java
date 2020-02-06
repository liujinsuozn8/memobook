package com.ljs.mvc.controller;

import com.ljs.mvc.dao.CustomerDaoFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class InitServlet extends HttpServlet {
    // 初始化时读取配置文件
    @Override
    public void init() throws ServletException {
        InputStream is = getServletContext().getResourceAsStream("WEB-INF/classes/config/dao.properties");
        // InputStream is = InitServlet.class.getResourceAsStream("dao.properties");
        // URL resource = InitServlet.class.getResource("dao.properties");
        Properties ps = new Properties();
        try {
            ps.load(is);
            String type = ps.getProperty("type");
            CustomerDaoFactory.getInstance().setType(type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
