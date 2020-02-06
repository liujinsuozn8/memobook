<%@ page import="com.ljs.test.listener.introduct.Customer2" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/6
  Time: 1:02 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>Session Actiovation Page</h3>

<%
    Customer2 customer2 = (Customer2) session.getAttribute("customer2");

    if (customer2 == null){
        customer2 = new Customer2();
        //创建一个日期对象并保存
        customer2.setDate(new Date());
        //将对象保存到session中
        session.setAttribute("customer2", customer2);


        System.out.println("-----------------------------------");
        System.out.println("create new customer2");
        System.out.println("obj = " + customer2);
        System.out.println("date = " + customer2.getDate());
        System.out.println("-----------------------------------");
    } else {
        System.out.println("-----------------------------------");
        System.out.println("read customer2");
        System.out.println("obj = " + customer2);
        System.out.println("date = " + customer2.getDate());
        System.out.println("-----------------------------------");
    }

%>

</body>
</html>
