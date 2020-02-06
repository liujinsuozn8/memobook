<%@ page import="javax.print.DocFlavor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h4>bforward</h4>

<%
    request.setAttribute("forwardRequestParam", "forwardRequestParamValue");
    // 转发页面到c.jsp
    request.getRequestDispatcher("/dispatcher/c.jsp").forward(request,response);
%>
</body>
</html>
