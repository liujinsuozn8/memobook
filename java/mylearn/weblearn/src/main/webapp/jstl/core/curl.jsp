<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/4
  Time: 12:45 下午
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

<h3>c:url page</h3>

<c:url value="/jstl/core/curl.jsp" var="testurl" scope="page">
    <c:param name="name" value="中文测试" />
</c:url>

<%--打印生成的url--%>
${pageScope.testurl}

</body>
</html>
