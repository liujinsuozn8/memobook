<%@ page import="com.ljs.mvc.bean.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    Customer customer = (Customer) request.getAttribute("customer");

    String id =null;
    String name = null;
    String oldname = null;
    String email = null;
    //如果是从index.jsp过来的，则通过request.getAttribute("customer")获得
    //如果是发生异常从当前页面重定向过来的，则从param中获取
    if (customer != null){
        id = customer.getId();
        name = customer.getName();
        oldname = customer.getName();
        email = customer.getEmail();
    } else {
        id = request.getParameter("id");
        name = request.getParameter("name");
        oldname = request.getParameter("oldname");
        email = request.getParameter("email");
    }
%>

<%
    String msg = (String) request.getAttribute("msg");
    if (msg != null){
%>
    <label style="color: red">msg : <%=msg%></label>
<%
    }
%>
<form action="update.do" method="post">
<%--    使用disabled会导致Servlet无法获取导致，使用readonly--%>
    id    : <input type="text" name="id" value="<%= id%>" readonly="true"><br>
    name  : <input type="text" name="name" value="<%= name %>"><br>
            <input type="hidden" name="oldname" value="<%= oldname%>">
    email : <input type="text" name="email" value="<%= email%>"><br>
    <br>
    <input type="submit" value="Update">
</form>

</body>
</html>
