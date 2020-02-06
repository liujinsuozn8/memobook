<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/4
  Time: 8:39 上午
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

<h3>c:if page</h3>

<c:set value="19" var="age" scope="request"/>

<%--1. 判断并输出标签体内容--%>
<c:if test="${requestScope.age > 18}">a adult</c:if>
<br>
<%--2. 将判断结果保存在域对象中，在后续处理中使用--%>
<c:if test="${requestScope.age > 18}" var="isAdult" scope="request"/>
isAdult = ${requestScope.isAdult}

</body>
</html>
