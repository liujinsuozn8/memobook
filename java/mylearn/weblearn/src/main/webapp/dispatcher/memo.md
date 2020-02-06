# 转发、重定向 测试

- test.html
    - 测试入口：`http://localhost:8080/weblearn_war_exploded/dispatcher/test.html`
    - 通过连接向指定的Servlet发送Get请求，并在Servlet内进行转发和重定向到：`TestServlet`
    - 转发Servlet：weblearn/src/main/java/com/ljs/test/dispatcher/ForwardServlet.java
    - 重定向Servlet：weblearn/src/main/java/com/ljs/test/dispatcher/RedirectServlet.java
    - 转发和重定向目标：weblearn/src/main/java/com/ljs/test/dispatcher/TestServlet.java
- a.jsp
    - 测试入口：`http://localhost:8080/weblearn_war_exploded/dispatcher/a.jsp`
    - 通过连接跳转到其他jsp中，并在其他jsp内进行web应用内部的转发和重定向
        - 转发jsp：bforward.jsp，
        - 重定向jsp：bredirect.jsp
        - 转发和重定向的目标jsp: c.jsp
    - 通过跳转到其他jsp中，并在其他jsp内重定向到web应用外部
        - 重定向jsp：outapp.jsp
        - 外部资源目标：`https://www.baidu.com`
        
- 配套Servlet存储目录
    - weblearn/src/main/java/com/ljs/test/dispatcher
