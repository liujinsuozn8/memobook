<%--
  Created by IntelliJ IDEA.
  User: liujinsuo
  Date: 2020/1/31
  Time: 8:19 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    String msg = (String) request.getAttribute("msg");
    if (msg != null){
%>
    <label style="color: red">msg: <%= msg%></label>
<%
    }
%>


<form action="insert.do" method="post">
    name  : <input type="text" name="name" value="<%= request.getParameter("name") == null? "" : request.getParameter("name") %>"><br>
    email : <input type="text" name="email"  value="<%= request.getParameter("email") == null? "" : request.getParameter("email") %>"><br>
    <br>
    <input type="submit" value="insert">
</form>

</body>
</html>
