<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/1/31
  Time: 2:06 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>
<%--不使用session对象--%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    //在JavaWEB规范中使用Cookie类代表cookie

    // 服务器-->浏览器
    // 1. 创建一个Cooke对象（在服务端创建）
    Cookie newCookie = new Cookie("name", "ljs");

    // 2. 调用response的一个方法把Cookie传给客户端
    response.addCookie(newCookie);
%>

</body>
</html>
