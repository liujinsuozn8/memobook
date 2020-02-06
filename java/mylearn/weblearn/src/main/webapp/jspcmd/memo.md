# jsp指令测试

- include指令测试
    - 测试入口：http://localhost:8080/weblearn_war_exploded/jspcmd/include01.jsp
    - 在`include01.jsp`中包含`include02.jsp`
    - 在`include01.jsp`中添加变量`str`，并在跨JSP页面在`include02.jsp`中使用
    
- page指令测试
    - 测试入口：http://localhost:8080/weblearn_war_exploded/jspcmd/page.jsp
    - 在jsp脚本片段中添加异常代码：`int a =1/ 0;`
    - 配置异常页面：`<%@ page errorPage="/WEB-INF/error.jsp" %>`，并测试异常页面的转发

- error-page测试
    - 附加请求参数：`name`
    - 入口：http://localhost:8080/weblearn_war_exploded/jspcmd/testerror.jsp?name=testName
    