<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/4
  Time: 7:58 上午
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

<h4>c:remove</h4>
<c:set value="testValue" var="testKey" scope="session"/>
testKey = ${sessionScope.testKey}
<br>
<%--使用cremove从session中删除属性--%>
<c:remove var="testKey" scope="session"/>
testKey = ${sessionScope.testKey}


</body>
</html>
