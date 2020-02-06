
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>page jspforward02</h3>

<!-- 在目标页面中获取通过 jsp:param 传送的参数 -->
<%= request.getParameter("user") %>

</body>
</html>
