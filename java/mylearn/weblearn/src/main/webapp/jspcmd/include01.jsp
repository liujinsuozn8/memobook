
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--通过JSP指令，在include01.jsp中包含include02.jsp--%>

<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>include01 page start</h3>
<%
    String str = "this is str";
%>
<%--包含include02.jsp的内容--%>
<%@ include file="include02.jsp"%>

<h3>include01 page end</h3>

</body>
</html>
