
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h4>error page</h4>
Error Message = <%= exception.getMessage()%>
<br>
exception object = <%=exception%>
<br>
request = <%=request%>
<br>
sessionID = <%=session.getId()%>
<br>
testSessionParam = <%=session.getAttribute("testSessionParam")%>
<br>
testRequestParam =  <%=request.getParameter("testRequestParam")%>
<br>
param.name = <%=request.getParameter("name")%>

</body>
</html>
