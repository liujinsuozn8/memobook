<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/1/31
  Time: 4:38 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    // 1. 获取cookie
    Cookie[] cookies = request.getCookies();

    // tomcat中默认会生成一个Cookie:JSESSIONID=xxxxx
    if (cookies != null && cookies.length > 1){
        // 浏览器-->服务器
        for (Cookie cookie : cookies) {
            out.print(cookie.getName() + ":" + cookie.getValue());
            out.print("<br>");
        }
    } else {
        // 服务器-->浏览器
        out.print("no cookie, creating");
        // 2. 如果没有Cookie则创建一个Cookie
        Cookie cookie = new Cookie("name", "ljs");

        // 3. 设置Cookie的持久化时间:30s
        cookie.setMaxAge(30);

        // 4. 使用response将Cookie发送到浏览器
        response.addCookie(cookie);
    }
%>

</body>
</html>
