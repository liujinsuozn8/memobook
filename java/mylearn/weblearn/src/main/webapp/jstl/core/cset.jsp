<%@ page import="com.ljs.test.tag.Customer" %><%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/4
  Time: 7:41 上午
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

<h4>c:set page</h4>

<%--1. 将name=testValue保存到pageContext域对象中--%>
<c:set value="testValue" var="name" scope="page" />
    <%--打印name--%>
name = ${pageScope.name}
<br>

<%--2. 使用el表达式设置value值--%>
<%
request.setAttribute("elStr","elTestValue");
%>

<c:set value="${requestScope.elStr}" var="elTest" scope="page"/>
elTest = ${pageScope.elTest}
<br>
<br>

<%--3. 使用c:set 来设置JavaBean中的属性--%>
<%
    Customer customer = new Customer("abcdef", "zxccv", 16);
    request.setAttribute("customer", customer);
%>
    <%--打印customer.id--%>
custome.id = ${customer.id}
<br>
    <%--使用cset重新设置customer.id--%>
<c:set target="${requestScope.customer}" property="id" value="1233456"/>
custome.id = ${customer.id}
</body>
</html>
