# 中文编码测试

- 在JSP页面上输入中文，请求页面后不出现乱码
    - 测试页面：hello.jsp
    - http://localhost:8080/weblearn_war_exploded/jspencode/hello.jsp
    
- 获取输入的中文
    - 测试入口:http://localhost:8080/weblearn_war_exploded/jspencode/form.jsp
    - form.jsp：在表单中输入中文，并提交表达，到submit.jsp
    - 在submit.jsp中进行响应，并获取form.jsp中输入的中文