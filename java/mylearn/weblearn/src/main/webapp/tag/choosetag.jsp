<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/3
  Time: 10:15 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="ljs" uri="http://www.ljs.com/mytag/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<ljs:choose>
    <ljs:when test="${param.score > 60}">level A</ljs:when>
    <ljs:when test="${param.score > 40}">level B</ljs:when>
    <ljs:when test="${param.score > 20}">level C</ljs:when>
    <ljs:otherwise>others</ljs:otherwise>
</ljs:choose>

</body>
</html>
