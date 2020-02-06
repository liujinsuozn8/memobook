<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/5
  Time: 6:09 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/WEB-INF/error.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>test error</h3>

<%
    System.out.println(request);
    // session.setAttribute("testSessionParam","testSessionParamValue");
    request.setAttribute("testRequestParam","testRequestParamValue");
    int a = 10 / 0;
%>

</body>
</html>
