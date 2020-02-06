<%--
  Created by IntelliJ IDEA.
  User: liujinsuo
  Date: 2020/1/31
  Time: 8:19 上午
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

<c:if test="${!empty requestScope.msg}">
    <label style="color: red">msg: ${requestScope.msg}</label>
</c:if>


<form action="insert.do" method="post">
    name  : <input type="text" name="name" value="${requestScope.name}"><br>
    email : <input type="text" name="email"  value="${requestScope.email}"><br>
    <br>
    <input type="submit" value="insert">
</form>

</body>
</html>
