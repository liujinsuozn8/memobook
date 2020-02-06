<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/3
  Time: 11:34 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%--导入自定义标签库--%>
<%@ taglib prefix="ljs" uri="http://www.ljs.com/mytag/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<!-- 循环打印value10次 -->
<ljs:show value="${param.name}" count="10"/>

</body>
</html>
