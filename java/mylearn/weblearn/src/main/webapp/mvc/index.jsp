<%@ page import="com.ljs.mvc.bean.Customer" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="query.do" method="post">
    id    : <input type="text" name="id" value="<%= request.getParameter("id") ==null ?"": request.getParameter("id")%>"><br>
    name  : <input type="text" name="name" value="<%= request.getParameter("name") ==null ?"": request.getParameter("name")%>"><br>
    email : <input type="text" name="email" value="<%= request.getParameter("email") ==null ?"": request.getParameter("email")%>"><br>
    <br>
    <input type="submit" value="Search">
    <a href="insert.jsp">insert new customer</a>
</form>

<br>
<br>

<table border="1" cellpadding="10" cellspacing="0">
    <tr>
        <th>id</th>
        <th>name</th>
        <th>email</th>
        <th>update/delete</th>
    </tr>


    <%
        List<Customer> all = (List<Customer>)request.getAttribute("allData");
    %>

    <%
        for (Customer customer : all) {
    %>

        <tr>
            <td><%= customer.getId()%></td>
            <td><%= customer.getName()%></td>
            <td><%= customer.getEmail()%></td>
            <td><a href="edit.do?id=<%= customer.getId()%>">update</a>/<a href="delete.do?id=<%= customer.getId()%>">delete</a></td>
        </tr>

    <%
        }
    %>
</table>



</body>
</html>
