<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: ljs
  Date: 2020/1/31
  Time: 7:07 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    String nowBookName = request.getParameter("book");
    Cookie[] cookies = request.getCookies();

    if (cookies != null && cookies.length >0) {
        ArrayList<Cookie> bookCookies = new ArrayList<>(5);
        Cookie deleteCookie = null;

        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();

            // - 从Cookie中获取所有的以`BOOK_`开头的Cookie，并保存在List中
            if (cookieName.startsWith("BOOK_")){
                bookCookies.add(cookie);
                // - 遍历Cookie，检查是否有Cookie的Value与当前的bookName相同
                if (nowBookName.equals(cookie.getValue())){
                    // - 如果有相同的，则记录下来，作为待删除的Cookie
                    deleteCookie = cookie;
                    break;
                }
            }
        }

            // - 如果没有待删除的Cookie，则List的长度是否等于5,
        if (deleteCookie == null && bookCookies.size() == 5) {
            // - 如果等于5，则删除第一个Cookie
            deleteCookie = bookCookies.get(0);
        }

        if (deleteCookie != null){
            // 删除Cookie
            deleteCookie.setMaxAge(0);
            response.addCookie(deleteCookie);
        }

        // - 在末尾将当前书名插入Cookie
        Cookie newCookie = new Cookie("BOOK_" + nowBookName, nowBookName);
        response.addCookie(newCookie);
    }

%>

book_name: <%= nowBookName%>
<br>
<a href="books.jsp">return</a>

</body>
</html>
