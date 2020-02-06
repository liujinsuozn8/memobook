<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/5
  Time: 5:35 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    String contextPath = request.getContextPath();
%>

<form action="<%=contextPath%>/reqwrapper/wrapperServlert" method="post">
    username:<input type="text" name="username">
    <br>
    <input type="submit" value="Login">
</form>

</body>
</html>
