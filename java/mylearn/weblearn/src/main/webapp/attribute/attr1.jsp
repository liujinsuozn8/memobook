<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    pageContext.setAttribute("pageContextAttr","pageContextValue");
    request.setAttribute("requestAttr", "requestValue");
    session.setAttribute("sessionAttr", "sessionValue");
    application.setAttribute("applicationAttr","applicationValue");

%>
</body>

<h2>Attr 1 Page  <%= new Date() %> </h2>
pageContextAttr:<%= pageContext.getAttribute("pageContextAttr")%>
<br>
requestAttr:<%= request.getAttribute("requestAttr")%>
<br>
sessionAttr:<%= session.getAttribute("sessionAttr")%>
<br>
applicationAttr:<%= application.getAttribute("applicationAttr")%>
<br>
<br>

<!--开启一个新的请求-->
<a href="attr2.jsp">To attr 2 page</a>

<a href="testAttr">To testAttr page</a>
</html>
