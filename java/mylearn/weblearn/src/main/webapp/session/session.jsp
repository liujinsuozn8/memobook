<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/1/31
  Time: 10:37 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%= session.getId()%>
<%
    // 命名为同名的Cookie
    Cookie cookie = new Cookie("JSESSIONID", session.getId());
    // 设定存活时间来持久化cookie
    cookie.setMaxAge(40);
    response.addCookie(cookie);
%>

</body>
</html>
