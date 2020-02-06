<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/2
  Time: 3:05 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>EL Object Page</h3>

<%--- 1. 与范围有关的隐含对象--%>
<h4>1. 与范围有关的隐含对象</h4>
<%
    request.setAttribute("testkey", "requestTestValue");
    session.setAttribute("testkey", "sessionTestValue");
%>

<%--搜索到request时停止，并返回值--%>
requestTestKey: ${testkey}<br>
requestTestKey: ${requestScope.testkey}<br>
sessionTestKey: ${sessionScope.testkey}<br>


<br>
<%--- 2. 与输入有关的隐含对象--%>
<h4>2. 与输入有关的隐含对象</h4>

param: ${param.name}<br>
paramValues: ${paramValues.scores}<br>

<%--- 3. 其他隐含对象--%>
<h3>3. 其他隐含对象</h3>
<%--获取cookie对象--%>
cookie.JSESSIONID : ${cookie.JSESSIONID.value} <br>

Request Header Accept-Language : ${header["Accept-Language"]} <br>

initParam jdbcUrl : ${initParam.jdbcUrl}<br>

<%--4. pageContext--%>
<h3>4.pageContext</h3>
请求的参数字符串 : ${pageContext.request.queryString}<br>
请求的URL : ${pageContext.request.requestURL}<br>
Web应用的路径 : ${pageContext.request.contextPath}<br>
SessionID : ${pageContext.session.id}<br>
remoteUser : ${pageContext.request.remoteUser}<br>
用户的IP地址 : ${pageContext.request.remoteAddr}<br>
主机端的服务信息 : ${pageContext.servletContext.serverInfo}<br>

<%--${pageContext.session.invalidate}--%>



</body>
</html>
