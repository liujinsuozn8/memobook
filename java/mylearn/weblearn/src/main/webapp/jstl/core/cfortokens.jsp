<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/4
  Time: 11:15 上午
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

<h4>c:forTokens page</h4>

<c:set var="str" value="a:b:c:d:dfs.rte;xcv" scope="request"></c:set>

<%--使用`:`分割字符串并输出--%>
<c:forTokens items="${str}" delims=":" var="node">
    node=${node}<br>
</c:forTokens>

</body>
</html>
