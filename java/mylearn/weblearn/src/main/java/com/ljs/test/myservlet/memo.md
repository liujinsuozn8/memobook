Servlet测试

LoginServlet 实现Servlet接口
LoginServlet2 继承 GenericServlet
LoginServlet3 继承 HttpServlet，并访问数据库来验证用户名和密码

自定义Servlet抽象类：
    - MyGenericServlet
        - 同时实现Servlet接口，和ServletConfig接口
        - 在子类中即可直接使用ServletConfig对象
        - 子类需要实现service方法，但是执行于http相关的操作时，都需要进行强转，比较麻烦
    - MyHttpServlet
        - 继承MyGenericServlet，并添加Http相关操作
            - 根据请求方式，分别调用doGet和doPost方法
            - 子类中可以根据请求方式来重写doGet和doPost方法，而不用实现Service方法
            