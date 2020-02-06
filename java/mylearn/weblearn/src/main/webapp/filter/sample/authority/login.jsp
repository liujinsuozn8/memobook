<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/5
  Time: 2:32 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>Login Page</h3>

<c:if test="${!empty requestScope.msg}">
    <label style="color: red">${requestScope.msg}</label>
</c:if>

<form action="${pageContext.request.contextPath}/authority/authorityController?method=login" method="post">
    username:<input type="text" name="username" value="${param.username}">
    <br>
    <input type="submit" value="Login">
</form>

<br>
<br>

<a href="${pageContext.request.contextPath}/filter/sample/authority/index.jsp">to index page</a>

</body>
</html>
