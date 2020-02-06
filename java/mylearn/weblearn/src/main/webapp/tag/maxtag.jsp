<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/3
  Time: 1:36 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="ljs" uri="http://www.ljs.com/mytag/core" %>
<html>
<head>
    <title>title</title>
</head>
<body>

<%--异常字符测试--%>
<ljs:max num1="10" num2="AAAAA"/>

<ljs:max num1="10" num2="20"/>

<ljs:max num1="10" num2="5"/>

</body>
</html>
