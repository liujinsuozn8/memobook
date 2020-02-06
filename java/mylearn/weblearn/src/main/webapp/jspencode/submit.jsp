
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h4> submit page </h4>

<%--<%--%>
<%--    // post请求中，直接获取中文参数会产生乱码--%>
<%--    // 所以先解码，再重新编码--%>
<%--    String username1 = request.getParameter("username");--%>
<%--    out.println(username1);--%>
<%--    out.println("<br>");--%>
<%--    String utf8Name = new String(username1.getBytes("iso-8859-1"), "UTF-8");--%>
<%--    out.println(utf8Name);--%>
<%--    out.println("<br>");--%>
<%--%>--%>

<%
    // 对于post请求，在获取请求信息之前，设定编码
    // 必须在第一次获取之前进行设定，否则在获取过变量之后再设定，该设定无效
    request.setCharacterEncoding("UTF-8");
    String address = request.getParameter("address");
    out.print(address);
    out.println("<br>");
%>

<%--<%= request.getParameter("username") %>--%>


</body>
</html>
