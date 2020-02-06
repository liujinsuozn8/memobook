<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--    JSP的9个隐式对象--%>
    <%
        Date date = new Date();
        System.out.println(date); // Thu Jan 23 21:59:48 CST 2020
    %>

    <%
        String s = date.toString();

        // 没有声明就可以使用的对象，就是隐含对象
        String name = request.getParameter("name");
        System.out.println(name); //2222

        Class clazz = request.getClass();
        System.out.println(clazz); // class org.apache.catalina.connector.RequestFacade

        // 输出：true
        System.out.println(response instanceof HttpServletResponse);

        // pageContext
        ServletRequest requestFromPC = pageContext.getRequest();
        System.out.println(requestFromPC == request); //true

        // session
        System.out.println(session.getId());

        //application
        System.out.println(application.getInitParameter("password")); //qwer

        //config
        System.out.println(config.getInitParameter("test")); //testValue

        // page
        System.out.println(page); //org.apache.jsp.hello_jsp@b9e7297
        System.out.println(this); //org.apache.jsp.hello_jsp@b9e7297

        // out
        out.println(name);
    %>
    <br>
    <%
        out.println("aaa");
    %>

    <%
        out.println("ccc");
        out.println("<br>");
        out.println("ddd");
    %>
</body>
</html>
