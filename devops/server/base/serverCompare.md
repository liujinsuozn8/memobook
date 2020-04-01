
<span id="catalog"></span>

# 服务器分类
[top](#catalog)
- 3种服务器类别
    - web服务器
    - http服务器
    - 应用服务器

- 3种服务器的比较

    |服务器类别|功能|备注|
    |-|-|-|
    |web服务器|<ul><li>提供web化服务，如：<ul><li>网站页面服务</li><li>邮件服务</li><li>网络下载服务</li></ul></li></ul>|web服务器的本质就是**响应用户需求，并提供服务**，所以很多服务器都可以称为web服务器|
    |http服务器<br><label style="color:red">静态服务器</label>|<ul><li>把服务器上的资源通过http协议形式传输给客户端</li><li>侧重于**对静态资源传输的支持**</li></ul>|web服务器应用层通讯协议主要是：http协议，所以基本可以认为: http服务器 = web服务器 |
    |应用服务器<br><label style="color:red">动态服务器</label>|<ul><li>可以理解为:**某个特定应用的承载容器**</li><li>可以用作：<ul><li>动态容器</li><li>应用容器</li><li>web容器</li></ul></li><li>支持动态响应<ul><li>能够根据不同的用户请求，动态的生成资源并返回，在客户端程序不同的内容</li></ul></li><li>在流量不大的时候，也可以作为http服务器，但是实际开发中很少这样使用</li></ul>|<ul><li>一般情况下，需要有运行时环境的支持，如:<ul><li>Tomcat，需要java的运行时环境</li></ul></li></ul>|



# 服务器软件的对比
[top](#catalog)

|软件名|类别|基本功能|备注|
|-|-|-|-|
|Nginx|http服务器软件|<ul><li>通过http协议将静态资源传输给客户端</li><li>反向代理服务器</li><li>负载均衡</li><li>nginx一般与后边的**动态应用服务器**配合<ul><li>将用户请求转发给应用服务器，来提供灵活、稳定的web服务</li></ul></li><li>性能好、稳定性高、能够挡住流量冲击，一般被放到整体架构的**最前面**，最先面对用户请求</li></ul>|可以和Lua脚本等配合进行二次开发，变成一个提供动态服务的应用服务器，如：OpenResty|
|Tengine|http服务器软件|<ul><li>基于Nginx做了加强和封装<li>主要针对大流量网站的场景</li><ul>
|apache http server|http服务器软件|功能上和nginx对等|性能不如nginx|
|Tomcat|应用服务器软件<br>(javaEE 轻量级)|提供动态服务，可以用作:<ul><li>web容器</li><li>动态容器</li><li>应用容器</li></ul>|<ul><li>开源轻量级</li><li>需要java运行时环境的支持</li><li>符合Servlet标准</li><li>springboot的内置**默认**应用容器</li><ul>|
|jetty|http服务器软件<br>应用服务器软件<br>(轻量级)|<ul><li>可以提供 静态服务</li><li>也可以提供 动态服务</li><li>可以用作：应用容器</li><li>提供了对http/2、webSocket、JMX等的支持</li></ul>|<ul><li>开源轻量级</li><li>功能上与tomcat相同</li><li>比tomcat更加轻量、配置更简单</li><li>符合Servlet标准</li><li>是springboot的内置应用容器之一</li></ul>|
|Undertow|应用服务器软件<br>(轻量级)|灵活高性能的we应用服务器|<ul><li>开源轻量级</li><li>符合Servlet标准</li><li>大流量的情况下，性能优于tomcat和jetty</li><li>是springboot的内置应用容器之一</li></ul>|
|JBoss|(企业级的)<br>应用服务器软件<br>动态应用服务器软件(javaEE 重量级)|<ul><li>可以部署动态应用，是Servlet、EJB的容器</li><li>**是javaEE框架部署的全套解决方案**</li><ul>|已经不仅仅是一款应用服务器软件，甚至可以视作一个重量级的应用服务平台|
|WildFly|(企业级的)<br>应用服务器软件<br>动态应用服务器软件(javaEE 重量级)|参考 JBoss|<ul><li>由Red Hat开发、维护</li><li>JBoss从8开始命名为WildFly</li><li>内置的默认web容器：Undertow</li><ul>|
|iis|http服务器软件|具有应用服务器能力的HTTP服务器，也可以做应用服务器|只能运行于windows系统下，不开源|
|WebLogic|(全能型、重量级)<br>应用服务器|用于部署企业级Java应用|<ul><li>Oracle旗下的软件</li><li>支持JavaEE下几乎所有的应用规范</li></ul>|
|WebSphere|(全能型、重量级)<br>应用服务器||<ul><li>IBM旗下的软件</li><li>支持多种JavaEE规范</li></ul>|
