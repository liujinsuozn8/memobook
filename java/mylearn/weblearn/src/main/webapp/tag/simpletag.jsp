<%@ page import="java.util.ArrayList" %>
<%@ page import="com.ljs.test.tag.Customer" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/2
  Time: 5:40 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
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

<%
    for (Customer customer : customers) {
%>
        <%=customer.getId()%>&nbsp;<%=customer.getName()%>&nbsp;<%=customer.getAge()%><br>
<%
    }
%>


<c:forEach items="${requestScope.customers}" var="customer">
    -- ${customer.id}, ${customer.name}, ${customer.age}<br>
</c:forEach>

</body>
</html>
