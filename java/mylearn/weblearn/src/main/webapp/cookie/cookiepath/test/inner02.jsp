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

<h3> inner02 page</h3>

<%

    Cookie[] cookies = request.getCookies();
    String cookiePathFromInner01 = null;
    String cookieAllPath = null;
    String cookiePathFromOut = null;

    for (Cookie cookie : cookies) {
        String cookieName = cookie.getName();

        // 获取子目录中添加的Cookie：cookiePathFromInner01
        if ("cookiePathFromInner01".equals(cookieName)){
            cookiePathFromInner01 = cookie.getValue();
        }

        // 获取子目录中添加的全局Cookie：cookieAllPath
        if ("cookieAllPath".equals(cookieName)){
            cookieAllPath = cookie.getValue();
        }

        // 获取上级目录中添加的Cookie：cookiePathFromOut
        if ("cookiePathFromOut".equals(cookieName)){
            cookiePathFromOut = cookie.getValue();
        }
    }

    out.println("cookiePathFromInner01 : " + cookiePathFromInner01);
    out.println("<br>");
    out.println("cookieAllPath : " + cookieAllPath);
    out.println("<br>");
    out.println("cookiePathFromOut : " + cookiePathFromOut);
    out.println("<br>");

%>

<a href="../out.jsp">to out.jsp</a><br>
<a href="inner01.jsp">to inner01.jsp</a>

</body>
</html>
