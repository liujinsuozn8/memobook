<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/5
  Time: 12:16 下午
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

<h3>Manage Page</h3>

<%--1. 查询form--%>
<%--查询成功后，将结果显示在：更新form中--%>
<form action="${pageContext.request.contextPath}/authority/authorityController?method=get" method="post">
<%--    如果用户名异常则显示异常信息--%>
    <c:if test="${!empty requestScope.msg}">
        <label style="color: red">${requestScope.msg}</label>
        <br>
    </c:if>

    username:<input type="text" name="username">
    <br>
    <input type="submit" value="Search">
</form>

<%--2. 更新form--%>
<%--判断是否获取成功--%>
<c:if test="${!empty requestScope.user}">
    <form action="${pageContext.request.contextPath}/authority/authorityController?method=update" method="post">
        <input type="hidden" name="username" value="${requestScope.user.username}">
        authority of user: ${requestScope.user.username}
        <br>
<%--        循环user.authorities 获取有效的权限，并将对应权限的check选中--%>
<%--        先循环通用权限数据--%>
        <c:forEach items="${requestScope.commonAuthorities}" var="commonNode">

<%--            再循环用户权限数据，并和通用权限数据进行匹配--%>
            <c:set var="flg" value="false"/>
            <c:forEach items="${requestScope.user.authorities}" var="node">
                <c:if test="${commonNode.displayName == node.displayName}">
                    <c:set var="flg" value="true"/>
                </c:if>
            </c:forEach>

<%--            如果权限数据之间匹配成功，则选中对应的checkbox--%>
            <c:if test="${flg == true}">
                <input type="checkbox" name="selectedUrls" value="${commonNode.url}" checked="checked"/>${commonNode.displayName}
            </c:if>
            <c:if test="${flg == false}">
                <input type="checkbox" name="selectedUrls" value="${commonNode.url}"/>${commonNode.displayName}
            </c:if>
            <br>
        </c:forEach>

        <input type="submit" value="Update">
    </form>
</c:if>

<br>

<a href="${pageContext.request.contextPath}/filter/sample/authority/index.jsp">to index page</a>

</body>
</html>
