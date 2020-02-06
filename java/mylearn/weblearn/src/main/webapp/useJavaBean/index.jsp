<%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/2/2
  Time: 12:41 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>useJavaBean Test</h3>

<%--1. 第一种语法：实例化一个对象并设值和取值--%>
javaBean:customer1 <br>
<%--实例化一个JavaBean对象--%>
<jsp:useBean id="customer1" class="com.ljs.test.useJavaBean.Customer" scope="request"/>

<%--为javaBean对象设值--%>
<jsp:setProperty name="customer1" property="age" value="11"/>
<jsp:setProperty name="customer1" property="name" value="newCustomer11"/>

<%--从javaBean对象取值--%>
name：<jsp:getProperty name="customer1" property="name"/> <br>
age：<jsp:getProperty name="customer1" property="age"/> <br>

<br><br>
<%--2. 使用所有请求参数来为JavaBean设值--%>
javaBean:customer2 <br>

<jsp:useBean id="customer2" class="com.ljs.test.useJavaBean.Customer" scope="request"/>
<jsp:setProperty name="customer2" property="*"/>

id:<jsp:getProperty name="customer2" property="id"/><br>
name:<jsp:getProperty name="customer2" property="name"/><br>
age:<jsp:getProperty name="customer2" property="age"/><br>

<br><br>
<%--3. 第二种语法：实例化一个对象并设值和取值--%>
javaBean:customer3 <br>
<%--实例化一个JavaBean对象--%>
<jsp:useBean id="customer3" beanName="com.ljs.test.useJavaBean.Customer" type="com.ljs.test.useJavaBean.Customer" scope="request"/>

<%--为javaBean对象设值--%>
<jsp:setProperty name="customer3" property="age" value="33"/>
<jsp:setProperty name="customer3" property="name" value="newCustomer33"/>

<%--从javaBean对象取值--%>
name：<jsp:getProperty name="customer3" property="name"/> <br>
age：<jsp:getProperty name="customer3" property="age"/> <br>


<br><br>
<%--4. 第二种语法：type测试--%>
javaBean:customer4 <br>
<%--实例化一个JavaBean对象--%>
<jsp:useBean id="customer4" beanName="com.ljs.test.useJavaBean.Customer" type="java.lang.Object" scope="request"/>


</body>
</html>
