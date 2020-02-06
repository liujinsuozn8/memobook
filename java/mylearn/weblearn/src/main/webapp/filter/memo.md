Filter过滤器测试

- filter/introduct
    - 基本概念测试
        - 入口：http://localhost:8080/weblearn_war_exploded/filter/introduct/index.jsp
    - Filter执行顺序测试
        - 入口：http://localhost:8080/weblearn_war_exploded/filter/introduct/test.jsp

- filter/sample/login
    - 登录示例
        - 入口：localhost:8080/weblearn_war_exploded/filter/sample/login/login.jsp

- filter/dispatcher
    - dispatcher配置 测试
        - 通过转发到达目标页面，可以触发forward
            - 入口：http://localhost:8080/weblearn_war_exploded/filter/dispatcher/index.jsp
        - 通过转发到达目标页面，不可以触发forward
            - 入口：http://localhost:8080/weblearn_war_exploded/filter/dispatcher/index2.jsp
        - 直接到达目标页面
            - 入口：http://localhost:8080/weblearn_war_exploded/filter/dispatcher/test.jsp
        - error触发测试
            - 入口：http://localhost:8080/weblearn_war_exploded/filter/dispatcher/testerror.jsp
            
- filter/sample/cache
    - 使浏览器不缓存页面的过滤器
        - 入口：http://localhost:8080/weblearn_war_exploded/filter/sample/cache/a.jsp

- filter/sample/encoding
    - 为所有页面设置字符编码
        - 附加请求参数：name
        - http://localhost:8080/weblearn_war_exploded/filter/sample/encoding/a.jsp?name=aaa
        - http://localhost:8080/weblearn_war_exploded/filter/sample/encoding/a.jsp?name=中文测试
        - http://localhost:8080/weblearn_war_exploded/filter/sample/encoding/b.jsp?name=中文测试
        
- filter/sample/authority
    - 入口：http://localhost:8080/weblearn_war_exploded/filter/sample/authority/index.jsp
    - 权限管理测试
        - http://localhost:8080/weblearn_war_exploded/filter/sample/authority/authorityManage.jsp
    
- filter/sample/reqparam
    - 入口：http://localhost:8080/weblearn_war_exploded/filter/sample/reqparam/input.jsp
    
    
http://localhost:8080/weblearn_war_exploded/filter/sample/authority/pages/page1.jsp