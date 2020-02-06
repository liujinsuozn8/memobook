session测试

- session.jsp：session cookie持久化测试
    - 入口:http://localhost:8080/weblearn_war_exploded/session/session.jsp
    
- lifecycle.jsp： session生命周期--创建HttpSession对象测试
    - 入口:http://localhost:8080/weblearn_war_exploded/session/lifecycle.jsp
    
- invalidate.jsp： HttpSession对象销毁测试
    - 每次都在jsp中，创建一个session并销毁，所以每次都会获得一个不同的HttpSession对象
    - 入口:http://localhost:8080/weblearn_war_exploded/session/invalidate.jsp
    
- maxtime.jsp：HttpSession过期时间测试
    - 入口：http://localhost:8080/weblearn_war_exploded/session/maxtime.jsp
    
- session/method: HttpSession常用方法测试
    - 登录入口：http://localhost:8080/weblearn_war_exploded/session/method/login.jsp
    
- session/url: url重写测试，内容与`session/method`相同
    - 测试前需要禁用cookie
    - 登录入口：http://localhost:8080/weblearn_war_exploded/session/url/login.jsp
    
- shopping
    - 使用session实现购物车

- session/resubmit: 重复提交测试
    - resubmitTestServlet中使用sleep来模拟网络延迟
    - 通过转发跳转到success.jsp页面，在该页面进行刷新也会导致请求的重复提交
    - 入口：localhost:8080/weblearn_war_exploded/session/resubmit/index.jsp