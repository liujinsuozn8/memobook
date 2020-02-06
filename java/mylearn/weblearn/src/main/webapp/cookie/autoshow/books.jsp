<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/1/31
  Time: 7:07 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<a href="book.jsp?book=AAA">AAA</a><br>
<a href="book.jsp?book=BBB">BBB</a><br>
<a href="book.jsp?book=CCC">CCC</a><br>
<a href="book.jsp?book=DDD">DDD</a><br>
<a href="book.jsp?book=EEE">EEE</a><br>
<a href="book.jsp?book=FFF">FFF</a><br>
<a href="book.jsp?book=GGG">GGG</a><br>
<a href="book.jsp?book=HHH">HHH</a><br>
<a href="book.jsp?book=III">III</a><br>
<a href="book.jsp?book=JJJ">JJJ</a><br>
<br>
<%
    //- 从Cookie中获取所有以`BOOK_`开头的Cookies，并显示

    Cookie[] cookies = request.getCookies();
    if (cookies != null && cookies.length > 0){
        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();
            if (cookieName.startsWith("BOOK_")){
                out.println(cookie.getValue());
                out.println("<br>");
            }
        }
    }
%>

</body>
</html>
