
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    // request.getRequestDispatcher("https://www.baidu.com").forward(request,response);
    response.sendRedirect("https://www.baidu.com");
%>

</body>
</html>
