<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/1
  Time: 9:29 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>step1</h3>

<%=request.getContextPath()%>
<form action="<%=request.getContextPath()%>/processStep1" method="post">
    <table border="1" cellpadding="10" cellspacing="0">
        <tr>
            <th>书名</th>
            <th>购买</th>
        </tr>

        <tr>
            <td>Book_A</td>
            <td><input type="checkbox" name="books" value="Book_A"></td>
        </tr>

        <tr>
            <td>Book_B</td>
            <td><input type="checkbox" name="books" value="Book_B"></td>
        </tr>

        <tr>
            <td>Book_C</td>
            <td><input type="checkbox" name="books" value="Book_C"></td>
        </tr>

        <tr>
            <td>Book_D</td>
            <td><input type="checkbox" name="books" value="Book_D"></td>
        </tr>

        <tr>
            <td colspan="2"> <input type="submit" value="继续"></td>
        </tr>

    </table>
</form>

</body>
</html>
