# 域对象 属性测试
- 从 attr1.jsp 可以跳转到
    - attr2.jsp
    - TestServlet
- 两个跳转页面的结果相同，因为都是从attr1.jsp发出的Get请求，但是在Servlet中无法直接使用pageContext对象
- attr1.jsp的url
    - http://localhost:8080/weblearn_war_exploded/attribute/attr1.jsp
    
- 配套Servlet存储目录
    - weblearn/src/main/java/com/ljs/test/attribute
