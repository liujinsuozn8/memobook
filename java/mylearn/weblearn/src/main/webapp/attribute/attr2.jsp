<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h2>Attr 2 Page <%= new Date() %></h2>
pageContextAttr:<%= pageContext.getAttribute("pageContextAttr")%>
<br>
requestAttr:<%= request.getAttribute("requestAttr")%>
<br>
sessionAttr:<%= session.getAttribute("sessionAttr")%>
<br>
applicationAttr:<%= application.getAttribute("applicationAttr")%>
<br>

</body>
</html>
