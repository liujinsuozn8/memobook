<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/1/31
  Time: 5:27 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    String username = request.getParameter("username");

    // - 若可以从url中获取到请求参数username
    if (username != null && !username.trim().equals("")){
        // - 把登录信息存储到Cookie中，并设置Cookie的最大时效为30s
        Cookie cookie = new Cookie("username", username);
        cookie.setMaxAge(30);
        response.addCookie(cookie);
    } else {
        // - 从Cookie中读取用户信息
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if ("username".equals(cookieName)){
                    username = cookie.getValue();
                    break;
                }
            }
        }
    }

    //若username存在,则打印欢迎信息
    if (username != null  && !username.trim().equals("")){
        out.print("username :" + username);
    } else {
        // - 若没有请求参数，也没有Cookie，则重定向到login.jsp
        response.sendRedirect("login.jsp");
    }
%>


</body>
</html>
