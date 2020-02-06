<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/4
  Time: 5:36 下午
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

<h3> login page </h3>
<%--如果被Filter拦截并转发回来，则输出错误信息--%>
<c:if test="${!empty requestScope.msg}">
    <label style="color: red">${requestScope.msg}</label>
    <br>
</c:if>

<form action="${pageContext.request.contextPath}/filter/sample/login/hello.jsp"
      method="post">

    username:<input type="text" name="username" value="${param.username}"><br>
    password:<input type="password" name="password"><br>
    <input type="submit" value="Login">
</form>

</body>
</html>
