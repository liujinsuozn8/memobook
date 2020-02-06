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

<h3>Step2 page</h3>

<%--el基本语法测试--%>
customer.name: ${sessionScope.customer1.name}
<br>
customer.name: ${sessionScope.customer1["name"]}
<br>
<br>

<%--el数据类型自动转换测试--%>
score from el : ${param.score}
<br>
score from el + 10  : ${param.score + 10}
<br>
score from requset : <%=request.getParameter("score")%>
<br>
score from requset + 10 : <%=request.getParameter("score") + 10%>

</body>
</html>
