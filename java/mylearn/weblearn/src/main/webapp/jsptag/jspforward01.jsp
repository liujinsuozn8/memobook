
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3> page jsp forward</h3>

<jsp:forward page="jspforward02.jsp">
    <jsp:param name="user" value="0987"/>
</jsp:forward>

<%--<%--%>
<%--    request.getRequestDispatcher().forward(request,response);--%>
<%--%>--%>
</body>
</html>
