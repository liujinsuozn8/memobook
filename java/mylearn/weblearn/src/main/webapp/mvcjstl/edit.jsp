<%@ page import="com.ljs.mvc.bean.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<c:set var="id" value="${requestScope.customer != null ? customer.id : requestScope.id}" scope="page"/>
<c:set var="name" value="${requestScope.customer != null ? customer.name : requestScope.name}" scope="page"/>
<c:set var="oldname" value="${requestScope.customer != null ? customer.oldname : requestScope.oldname}" scope="page"/>
<c:set var="email" value="${requestScope.customer != null ? customer.email : requestScope.email}" scope="page"/>


<c:if test="${!empty requestScope.msg}">
    <label style="color: red">msg : ${requestScope.msg}</label>
</c:if>

<form action="update.do" method="post">
<%--    使用disabled会导致Servlet无法获取导致，使用readonly--%>
    id    : <input type="text" name="id" value="${pageContext.id}" readonly="true"><br>
    name  : <input type="text" name="name" value="${pageContext.name}"><br>
            <input type="hidden" name="oldname" value="${pageContext.oldname}">
    email : <input type="text" name="email" value="${pageContext.email}"><br>
    <br>
    <input type="submit" value="Update">
</form>

</body>
</html>
