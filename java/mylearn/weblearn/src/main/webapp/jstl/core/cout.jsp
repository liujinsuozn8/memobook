<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/4
  Time: 7:28 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h4>c:out</h4>

<%
    request.setAttribute("book", "<<tab test>>");
%>

book by el: ${requestScope.book}
<br>
book by jstl: <c:out value="${requestScope.book}"/>

</body>
</html>
