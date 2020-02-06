<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    Date date = new Date();
    out.println(date);
%>

<%--直接使用上面声明的date对象--%>
<%= date %>

<%--<%--%>
<%--    String ageStr = request.getParameter("age");--%>
<%--    int age = Integer.parseInt(ageStr);--%>
<%--    if (age >18) {--%>
<%--        out.println("a");--%>
<%--    } else {--%>
<%--        out.println("b");--%>
<%--    }--%>
<%--%>--%>

<%
    String ageStr = request.getParameter("age");
    int age = Integer.parseInt(ageStr);
    if (age >18) {
%>
        aaa
<%
    } else {
%>
        bbb
<%
    }
%>


<%--在JSP声明中添加私有方法，并在JSP脚本片段中调用--%>
<%!
    private void testMethod(){
        System.out.println("this is testMethod");
    }
%>

<%
    testMethod();
%>
</body>
</html>
