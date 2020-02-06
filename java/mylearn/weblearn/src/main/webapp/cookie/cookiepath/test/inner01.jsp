<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/1/31
  Time: 8:01 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3> inner01 page</h3>

<%
    // 添加一个只能在当前目录使用Cookie
    Cookie cookie = new Cookie("cookiePathFromInner01", "cookiePathFromInner02Value");
    response.addCookie(cookie);

    // 添加一个在当前应用范围内都可以使用的Cookie
    Cookie all = new Cookie("cookieAllPath", "cookieAllPathValue");
    all.setPath(request.getContextPath());
    response.addCookie(all);

%>

<a href="../out.jsp">to out.jsp</a>

</body>
</html>
