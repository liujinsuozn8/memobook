<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/1
  Time: 4:00 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>

<body>

<h3>login page</h3>

<table border="1" cellpadding="10" cellspacing="0">
    <tr>
        <th>key</th>
        <th>value</th>
    </tr>

    <%--- getId 获取sessionID--%>
    <tr>
        <td>sessionID</td>
        <td><%= session.getId()%></td>
    </tr>

    <%--- getCreationTime 获取session对象的创建时间--%>
    <tr>
        <td>session对象的创建时间</td>
        <td><%=session.getCreationTime()%></td>
    </tr>

    <%--- getLastAccessedTime 获取session对象的上一次访问时间--%>
    <tr>
        <td>session对象的上一次访问时间</td>
        <td><%=session.getLastAccessedTime()%></td>
    </tr>

    <%--- getMaxInactiveInterval 获取过期时间--%>
    <tr>
        <td>session过期时间</td>
        <td><%=session.getMaxInactiveInterval()%></td>
    </tr>

    <%--- isNew 检查当前Servlet中使用的HttpSession对象是不是新的--%>
    <tr>
        <td>isNew</td>
        <td><%=session.isNew()%></td>
    </tr>
</table>

<br>
<br>
<br>

<%
    //如果是第一次登录，则在文本框中显示空白，
    // 如果是从hell.jsp的重新登录连接跳转过来的，则在文本框中显示之前登录的username
    Object username = session.getAttribute("username");
    if (username == null)
        username = "";
%>

<%--url重写--%>
<form action="<%= response.encodeURL("hello.jsp")%>" method="post">
    username: <input name="username" value="<%=username%>">
    <input type="submit" value="登录">
</form>


</body>
</html>
