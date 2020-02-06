<%@ page import="com.ljs.test.session.shopping.Customer" %><%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/1
  Time: 10:07 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    Customer customer = (Customer) session.getAttribute("customer");
    String[] books = (String[]) session.getAttribute("books");
%>

<h3>Step3:确认</h3>
<table border="1" cellpadding="10" cellspacing="0">
    <tr>
        <td>姓名</td>
        <td><%=customer.getName()%></td>
    </tr>

    <tr>
        <td>地址</td>
        <td><%=customer.getAddress()%></td>
    </tr>

    <tr>
        <td colspan="2">付款信息</td>
    </tr>

    <tr>
        <td>信用卡类型</td>
        <td><%=customer.getCardType()%></td>
    </tr>
    <tr>
        <td>卡号</td>
        <td><%=customer.getCardId()%></td>
    </tr>

    <tr>
        <td>订货项目</td>
        <td>
        <%
            for (String book : books) {
        %>
            <%=book%><br>
        <%
            }
        %>
        </td>
    </tr>
</table>

</body>
</html>
