<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ljs.test.tag.Customer" %><%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/3
  Time: 5:09 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="ljs" uri="http://www.ljs.com/mytag/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    List<Customer> customers = new ArrayList<>();
    request.setAttribute("customers", customers);
    customers.add(new Customer("1", "aaa", 11));
    customers.add(new Customer("2", "bbb", 22));
    customers.add(new Customer("3", "ccc", 33));
    customers.add(new Customer("4", "ddd", 44));
    customers.add(new Customer("5", "eee", 55));
%>

<ljs:foreach items="${requestScope.customers}" var="node">
    id: ${node.id}, name: ${node.name}, age: ${node.age}<br>
</ljs:foreach>

</body>
</html>
