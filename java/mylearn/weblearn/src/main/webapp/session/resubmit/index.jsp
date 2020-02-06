<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/1
  Time: 11:44 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>index page</h3>

<%--主动请求页或刷新时会执行JSP脚本片段，
后退时使用浏览器缓存则JSP脚本片段不会执行--%>
<%
    // 构造随机值token
    String token = new Date().getTime() + "";

    // 将token保存到session中
    session.setAttribute("token", token);
%>

<form action="<%=request.getContextPath()%>/resubmitTest" method="post">
<%--    将token保存到form的隐藏域，一同提交到Servlet，进行验证--%>
    <input type="hidden" name="token" value="<%=token%>">

    name: <input type="text" name="name">
    <input type="submit" value="submit">
</form>

</body>
</html>
