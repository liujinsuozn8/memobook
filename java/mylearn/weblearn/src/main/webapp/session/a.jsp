<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/1
  Time: 1:46 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h4> AAA page</h4>

<%
    HttpSession session2 = request.getSession(true);
    HttpSession session3 = request.getSession(true);
%>

<%= session2.getId()%>
<br><br>

<a href="b.jsp">To B Page</a>
<%
    session.setAttribute("time", new Date());
%>

<br><br>
currentTime: <%= new Date()%>
<br><br>

sessionTime:<%= session.getAttribute("time")%>

</body>
</html>
