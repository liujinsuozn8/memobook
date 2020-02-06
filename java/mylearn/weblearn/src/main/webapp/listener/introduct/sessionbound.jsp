<%@ page import="com.ljs.test.listener.introduct.Customer" %><%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/6
  Time: 11:58 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>Session Bound Page</h3>

<%
    Customer customer = new Customer();
    session.setAttribute("customer", customer);
    session.removeAttribute("customer");
%>

</body>
</html>
