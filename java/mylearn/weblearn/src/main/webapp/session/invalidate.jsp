<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/1
  Time: 2:59 下午
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

    // 然后将session销毁
    session.invalidate();
%>

</body>
</html>
