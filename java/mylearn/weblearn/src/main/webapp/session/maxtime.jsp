<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/1
  Time: 3:05 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    // 创建一个session
    HttpSession session = request.getSession(true);
    out.println(session.getId());
    out.println("<br><br>");

    // 默认以秒为单位
    out.println(session.getMaxInactiveInterval());

    // 重新设定session的过期时间：5s
    session.setMaxInactiveInterval(5);

    // 默认以秒为单位
    out.println(session.getMaxInactiveInterval());
%>

</body>
</html>
