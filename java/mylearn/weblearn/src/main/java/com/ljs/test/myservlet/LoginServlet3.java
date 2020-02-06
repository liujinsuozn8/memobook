package com.ljs.test.myservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class LoginServlet3 extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        String password = request.getParameter("password");

        Connection connection =null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            Class clazz = Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://127.0.0.1:3306/weblearn?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false";
            String mysqlUser = "root";
            String mysqlPassword = "1234";

            connection = DriverManager.getConnection(url, mysqlUser, mysqlPassword);
             ps = connection.prepareStatement("select count(*) from test_users where user = ? and password =?");
            ps.setObject(1, user);
            ps.setObject(2, password);

            resultSet = ps.executeQuery();


            if(resultSet.next()){
                PrintWriter writer = response.getWriter();
                int count = resultSet.getInt(1);
                if (count > 0){
                    writer.write("Hello : " + user);
                }else {
                    writer.write("Sorry : " + user);
                }

            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection !=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
