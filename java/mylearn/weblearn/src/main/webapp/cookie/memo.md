cookie测试页面

- cookie01.jsp，测试cookie的发送
    - http://localhost:8080/weblearn_war_exploded/cookie/cookie01.jsp
- cookie02.jsp，测试cookie的接收
    - http://localhost:8080/weblearn_war_exploded/cookie/cookie02.jsp
- maxage.jsp，测试持久化cookie的保存时间
    - 内容基本与`cookie02.jsp`相同
    - 使用时需要保证关闭浏览器选项：退出时删除历史
- zerocookie.jsp，将Cookie的保存时间，来测试不可保存Cookie
    - http://localhost:8080/weblearn_war_exploded/cookie/zerocookie.jsp
    
- autologin，自动登录
    - 在login.jsp中进行登录
    - 在hello.jsp中会自动验证
    - 入口：http://localhost:8080/weblearn_war_exploded/cookie/autologin/hello.jsp
    - 入口：http://localhost:8080/weblearn_war_exploded/cookie/autologin/login.jsp
    
- autoshow，显示最近5本浏览过的书
    - 入口：http://localhost:8080/weblearn_war_exploded/cookie/autoshow/books.jsp
    
- cookiepath
    - 测试Cookie的作用域
    - 各页面的设定内容
        - out.jsp
            - 访问`test/inner01.jsp`中设定的Cookie：cookiePathFromInner01、cookieAllPath
            - 设定可以在子目录中访问的Cookie：cookiePathFromOut
        - test/inner01.jsp的内容
            - 设定只在当前目录下可以访问的Cookie：cookiePathFromInner01
            - 设定可以在当前应用全局访问的Cookie：cookieAllPath
        - test/inner02.jsp的内容
            - 访问`test/inner01.jsp`中设定的Cookie：cookiePathFromInner01、cookieAllPath
            - 访问`out.jsp`中设定的Cookie：cookiePathFromOut
        
    - 访问顺序
        - localhost:8080/weblearn_war_exploded/cookie/cookiepath/test/inner01.jsp
            - 设定只在当前目录下可以访问的Cookie：cookiePathFromInner01
            - 设定可以在当前应用全局访问的Cookie：cookieAllPath
            
        - localhost:8080/weblearn_war_exploded/cookie/cookiepath/out.jsp
            - 访问`test/inner01.jsp`中设定的Cookie：cookiePathFromInner01、cookieAllPath
            - 设定可以在子目录中访问的Cookie：cookiePathFromOut
            
        - localhost:8080/weblearn_war_exploded/cookie/cookiepath/test/inner02.jsp
            - 访问`test/inner01.jsp`中设定的Cookie：cookiePathFromInner01、cookieAllPath
            - 访问`out.jsp`中设定的Cookie：cookiePathFromOut