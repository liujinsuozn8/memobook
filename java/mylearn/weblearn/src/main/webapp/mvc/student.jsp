
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    Object students = request.getAttribute("students");
    out.println(students);
%>

</body>
</html>
