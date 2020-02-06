<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%@ page errorPage="/WEB-INF/error.jsp" %>
<%@ page pageEncoding="UTF-8" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<%--<%= session.getId()%>--%>
<%= new Date()%>
<%
    int a =1/ 0;
%>

</body>
</html>
