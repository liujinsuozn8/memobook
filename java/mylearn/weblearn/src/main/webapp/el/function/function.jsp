<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/3
  Time: 11:06 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ljs" uri="http://www.ljs.com/mytag/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    String name = "abcdefg";
    request.setAttribute("name", name);

    String name2 = "123456";
    request.setAttribute("name2", name2);
%>

<%--使用JSTL核心库提供的el函数--%>
${fn:length(requestScope.name)}
<br>
${fn:toUpperCase(requestScope.name)}
<br>

<%--测试自定义的el函数--%>
${ljs:concat(requestScope.name, requestScope.name2)}


</body>
</html>
