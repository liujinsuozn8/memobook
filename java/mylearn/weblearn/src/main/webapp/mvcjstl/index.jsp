<%@ page import="com.ljs.mvc.bean.Customer" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="query.do" method="post">
    id    : <input type="text" name="id" value="${requestScope.id}"><br>
    name  : <input type="text" name="name" value="${requestScope.name}"><br>
    email : <input type="text" name="email" value="${requestScope.email}"><br>
    <br>
    <input type="submit" value="Search">
    <a href="insert.jsp">insert new customer</a>
</form>

<br>
<br>

<table border="1" cellpadding="10" cellspacing="0">
    <tr>
        <th>id</th>
        <th>name</th>
        <th>email</th>
        <th>update/delete</th>
    </tr>

    <c:forEach items="${requestScope.allData}" var="customer" >
        <tr>
            <td>${customer.id}</td>
            <td>${customer.name}</td>
            <td>${customer.email}</td>
            <td>
                <c:url value="edit.do" var="editurl">
                    <c:param name="id" value="${customer.id}"/>
                </c:url>
                <a href="${editurl}">update</a>
                /

                <c:url value="delete.do" var="deleteurl">
                    <c:param name="id" value="${customer.id}"/>
                </c:url>
                <a href="${deleteurl}">delete</a>
            </td>
        </tr>
    </c:forEach>

</table>



</body>
</html>
