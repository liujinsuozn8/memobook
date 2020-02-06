<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/1
  Time: 3:50 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>hello page</h3>

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
    String username = request.getParameter("username");
%>
username: <%=username%>

<%--跳转到登录页面进行重新登录，并且在username处显示登录的username--%>
<%--通过HttpSession在一次会话中，在多个页面之间传递值--%>
<%
    session.setAttribute("username", username);
%>

<br>
<br>

<%--url重写--%>
<a href="<%=response.encodeURL("login.jsp")%>">重新登录</a>
<br>
<a href="<%=response.encodeURL("logout.jsp")%>">注销</a>


</body>
</html>
