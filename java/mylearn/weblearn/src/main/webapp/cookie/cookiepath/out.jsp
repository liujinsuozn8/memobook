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

<h3> out page</h3>
<%
    Cookie newcookie = new Cookie("cookiePathFromOut", "cookiePathFromOutValue");
    response.addCookie(newcookie);

    Cookie[] cookies = request.getCookies();
    String cookiePathFromInner01 = null;
    String cookieAllPath = null;
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
    }

    out.println("cookiePathFromInner01 : " + cookiePathFromInner01);
    out.println("<br>");
    out.println("cookieAllPath : " + cookieAllPath);
%>

<br>
<a href="test/inner02.jsp"> to test/inner02.jsp </a>

</body>
</html>
