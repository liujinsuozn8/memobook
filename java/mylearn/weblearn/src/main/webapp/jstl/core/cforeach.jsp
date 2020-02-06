<%@ page import="java.util.List" %>
<%@ page import="com.ljs.test.tag.Customer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/4
  Time: 10:27 上午
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

<h4>c:foreach Page</h4>

<%--1. 遍历Collection 遍历数组与遍历Collection相同--%>
<%--1.1. 不设定集合，只是进行迭代，并输出index--%>
<c:forEach begin="0" end="20" step="2" var="i">
    index = ${i} <br>
</c:forEach>
<br>
<br>

<%
    List<Customer> customers = new ArrayList<>();
    request.setAttribute("customers", customers);
    customers.add(new Customer("111", "aaa", 11));
    customers.add(new Customer("222", "bbb", 22));
    customers.add(new Customer("333", "ccc", 33));
    customers.add(new Customer("444", "ddd", 44));
    customers.add(new Customer("555", "eee", 55));
%>

<%--1.2. 从0开始打印值和信息--%>
<c:forEach items="${customers}" varStatus="status" var="node">
    index=${status.index}, count=${status.count}, isfirst=${status.first}, islast=${status.last}, name=${node.name}<br>
</c:forEach>
<br>

<%--1.3. 从2开始打印值和信息--%>
<c:forEach items="${customers}" varStatus="status" var="node" begin="2">
    index=${status.index}, count=${status.count}, isfirst=${status.first}, islast=${status.last}, name=${node.name}<br>
</c:forEach>
<br>
<br>

<%--2. 遍历Map--%>
<%
    Map<String, Customer> custMap = new HashMap<>();
    request.setAttribute("custMap", custMap);
    custMap.put("a1", new Customer("111", "aaa", 11));
    custMap.put("a2", new Customer("222", "bbb", 22));
    custMap.put("a3", new Customer("333", "ccc", 33));
    custMap.put("a4", new Customer("444", "ddd", 44));
    custMap.put("a5", new Customer("555", "eee", 55));
%>

<c:forEach items="${custMap}" var="node">
    node.key=${node.key}, node.value.id=${node.value.id}, node.value.name=${node.value.name}<br>
</c:forEach>
<br>

<%--3. 遍历Enumeration--%>
<%--必须通过pageContext.request来获取request对象
    不能通过requestScope获取，这只是代表一个范围，不是真正的request对象--%>
<c:forEach items="${pageContext.request.attributeNames}" var="node">
    attributeName = ${node}<br>
</c:forEach>
<br>


</body>
</html>
