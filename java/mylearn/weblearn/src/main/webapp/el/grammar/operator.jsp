<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ljs.test.useJavaBean.Customer" %><%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/2
  Time: 4:33 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    // 1.关系运算符测试
    request.setAttribute("score", 60);

    // 2.集合测试
    List<String> list1 = new ArrayList<>();
    list1.add("aaa");
    request.setAttribute("list1", list1);

    List<String> list2 = new ArrayList<>();
    request.setAttribute("list2", list2);

    // 3. 字符串测试
    //保存一个空字符串
    request.setAttribute("str1", "");

    //保存一个非空的字符串
    request.setAttribute("str2", "abcd");

    // 4. 数组测试
    int[] intArray1 = new int[0];
    request.setAttribute("intArray1", intArray1);

    int[] intArray2 = new int[]{1,2,3,4,5};
    request.setAttribute("intArray2", intArray2);
%>

<%--域对象属性名不标准时使用：[""]--%>
<%
    Customer customer2 = new Customer();
    customer2.setName("customer2");
    session.setAttribute("com.ljs.customer2", customer2);
%>

<h3>域对象属性名不标准时使用[""]获取</h3>
customer2.name: ${sessionScope["com.ljs.customer2"].name}
<br>

<h3>1. 关系运算符测试</h3>
"score > 60?" : ${score >= 60}<br>
"score > 60?" : ${score >= 60 ? "及格":"不及格"}<br>

<h3>2. 集合测试</h3>
list1 value: ${requestScope.list1}<br>
empty list1: ${empty requestScope.list1}<br>

list2 value : ${requestScope.list2}<br>
empty list2: ${empty requestScope.list2}<br>

<h3>3. 空对象测试</h3>
null Object: ${empty requestScope.xxxx}<br>

<h3>4. 空字符串测试</h3>
str1 value : ${requestScope.str1}<br>
empty str1 : ${empty requestScope.str1}<br>
str2 value: ${requestScope.str2}<br>
not empty str2 : ${empty requestScope.str2}<br>

<h3>5. 数组测试</h3>
intArray1 value : ${requestScope.intArray1}<br>
empty intArray1 : ${empty requestScope.intArray1}<br>

intArray2 value : ${requestScope.intArray2}<br>
not empty intArray2 : ${empty requestScope.intArray2}<br>

</body>
</html>
