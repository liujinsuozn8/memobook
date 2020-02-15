<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/1/24
  Time: 8:30 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h4>webinf error page</h4>
Error Message : <%= exception.getMessage()%>
<br>
<%=exception%>
</body>
</html>
