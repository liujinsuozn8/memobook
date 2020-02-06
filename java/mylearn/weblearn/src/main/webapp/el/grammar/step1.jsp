<%@ page import="com.ljs.test.useJavaBean.Customer" %><%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/2
  Time: 2:03 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>Step1 page</h3>

<%--通过javabean来创建对象--%>
<jsp:useBean id="customer1" class="com.ljs.test.useJavaBean.Customer" scope="session"/>
<jsp:setProperty name="customer1" property="name" value="newCustomer"/>
name : <jsp:getProperty name="customer1" property="name"/>
<br>

<%--使用超链接跳转到其他页面，并获取customer中的值--%>
<%--附加一个请求参数：score，在step2.jsp中进行数据类型自动转换的测试--%>
<a href="step2.jsp?score=12">to Step2 page </a>

</body>
</html>
