# jsp标签测试

- `<jsp:include>`测试
    - 访问地址：http://localhost:8080/weblearn_war_exploded/jsptag/jspinclude01.jsp
    - `jspinclude01.jsp`是主页面，在页面中包含`jspinclude02.jsp`
- `<jsp:forward>`测试
    - 访问地址：http://localhost:8080/weblearn_war_exploded/jsptag/jspforward01.jsp
    - 从`jspforward01.jsp`进入，并转发到`jspforward02.jsp`
    - 转发是传送一个变量：`<jsp:param name="user" value="0987"/>`