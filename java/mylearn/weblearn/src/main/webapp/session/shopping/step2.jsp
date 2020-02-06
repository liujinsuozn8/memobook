<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/1
  Time: 9:39 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>step2</h3>

<form action="<%=request.getContextPath()%>/processStep2" method="post">
    <table border="1" cellspacing="0" cellpadding="10">
        <tr>
            <td colspan="2">基本信息</td>
        </tr>

        <tr>
            <td>姓名</td>
            <td><input type="text" name="name"></td>
        </tr>

        <tr>
            <td>地址</td>
            <td><input type="text" name="address"></td>
        </tr>

        <tr>
            <td colspan="2">信用卡信息</td>
        </tr>

        <tr>
            <td>种类</td>
            <td>
                <input type="radio" name="cardType" value="visa"> visa<br>
                <input type="radio" name="cardType" value="master"> master<br>
            </td>
        </tr>
        <tr>
            <td>卡号</td>
            <td><input type="text" name="cardId"></td>
        </tr>

        <tr>
            <td colspan="2"><input type="submit" name="提交"></td>
        </tr>
    </table>
</form>

</body>
</html>
