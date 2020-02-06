<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/5
  Time: 2:35 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>Page List</h3>

username:${sessionScope.userInfo.username}
<br>
<a href="${pageContext.request.contextPath}/filter/sample/authority/pages/page1.jsp">pages/page1</a><br>
<a href="${pageContext.request.contextPath}/filter/sample/authority/pages/page2.jsp">pages/page2</a><br>
<a href="${pageContext.request.contextPath}/filter/sample/authority/pages/page3.jsp">pages/page3</a><br>
<a href="${pageContext.request.contextPath}/filter/sample/authority/pages/page4.jsp">pages/page4</a><br>

<br>
<br>
<br>

<a href="${pageContext.request.contextPath}/authority/authorityController?method=logout">to logout page</a>

</body>
</html>
