* ftp,http,stmp,pop,tcp/ip
* HTTP协议的工作流程
    1. 原始状态：客户端和服务器之间没有关系
    2. 客户端-->服务器:建立连接，发送请求
        * 连接：网络上的虚拟电路
        * Request Headers
    3. 服务器-->客户端:沿着连接，返回响应信息
        * Response Headers
    4. 断开连接，电路消失
* HTTP请求信息格式
    * 要素
        * 请求行
            * 请求方法
                * 协议规定的方法
                    * GET
                    * POST
                    * HEAD
                        * 和GET基本相同，只是不返回内容
                        * 例如只确认某个资源是否存在，而不需要返回该资源
                    * PUT
                    * DELETE
                    * TRACE
                        * 如果用了代理上网，可以用TRACE来测试代理有没有修改原始的HTTP请求
                    * OPTIONS
                        * 返回服务器可用的请求方法
                * 协议里规定的方法WebServer中不一定支持这些方法
            * 请求路径/资源
                * URL的一个部分
            * 使用的协议(协议的版本)
        * 请求头信息
            * 格式为：`key:value`
            * `Host:.....`
            * `Content-length:信息长度`：发送的信息的长度，在该信息之后才能输入主体信息，用于POST等的发送
        * 空行
            * **头信息结束后，有一个空行，头信息和主体信息需要这个空行做区分**
                * 无论后面有没有请求主体信息，后边都会有一个空行
        * 请求主体信息(可以没有)

    * 实例
        ```
        POST XXXXX HTTP/1.1
        Host:localhost
        Content-type:application/x-www-form-urlencoded
        Content-length:20

        ....(请求主体信息)        
        ```

* HTTP响应信息的格式
    * 要素
        * 响应行：
            * 协议版本
            * 状态码
                * 用来反应服务器响应得分情况
                * 常见码：
                    * 200：服务器成功返回网页
                    * 301：永久重定向
                    * 302：临时重定向
                    * 304：未修改（NOT MODIFIED）
                        * 从缓存取数据，减轻服务器压力
                        * `If-Modified-Since`、`If-None-Match`，通过两个字段：时间戳、标识符来判别
                    * 404：请求的网页不存在
                    * 503：服务器暂时不可用
                    * 500：服务器内部错误
                * 5个系列

                    |状态码|定义|说明|
                    |-|-|-|
                    |1XX|信息|接收到请求，继续处理|
                    |2XX|成功|操作成功的收到，理解和接受|
                    |3XX|重定向|为了完成请求，必须采取进一步措施|
                    |4XX|客户端错误|请求的语法有错误或不能完全被满足|
                    |5XX|服务端错误|服务器无法完成请求|
            * 状态文字
                * 描述状态码，便于观察

        * 响应头信息
            * 多个key:value
                * contente-length:主体长度
        * 空行
        * 响应主体(可以没有)
    * 实例
        ```
        HTTP/1.1 200 OK
        Content-type:text/html
        Content-length:5

        ....(响应主体)
        ```
* 重定向的问题
    * 不会影响GET请求
    * 对于POST请求
        * 重定向会导致数据丢失
        * 原始请求为POST，重定向请求变为GET
        * 可以使用状态码：**307**，在重定向中保持原有的请求数据
            * 使用307后，原始请求和重定向请求都是POST请求
* 标准HTTP协议的换行符是:`\r\n`
* POST请求
    * `content-type:application/x-www-form-urlencoded`，添加此信息后，服务器才能正常读取POST请求
    * `Content-length:信息长度`，添加此信息服务器才能正常读取
    * 请求主体形式：`key=val&key=val...`
* Cookie
    * 客户端-->服务器：请求头附加`cookies:value`
    * 服务器-->客户端：响应头：Set-Cookie

* Referer防盗链
    * 在请求头中
    * Referer代表网页的来源，即上一页的地址
    * 如果直接在浏览器上输入地址进入，则没有Refer
    * 通过Referer，服务器能够识别来源
    * 如何配置apache服务器来做防盗链
        * 原理：
            * 在web服务器层面，根据http协议的referer头信息来判断
            * 如果判断Referer来自站外，则可以将请求内容**重写到一个防盗链资源上(如图片)**
        * 具体步骤：
            1. 打开apache重写模块mod_rewrite
            2. 在需要防盗的网站或目录，编写`.htaccess`文件，并指定防盗链规则   
                ```php
                RewriteEngine On //启动防盗脸
                Rewrite Base ... //指定监控哪部分页面
                RewriteCond  ... //指定规则
                //图片防盗
                RewriteCond %<REQUEST_FILENAME>.*\.(jpg|jpeg|gif|png) [NC] //[NC]不区分大小写
                //指定访问来源，如果来源不是指定地址，则执行防盗处理
                RewriteCond %<REQUEST_REFERER> !localhost [NC]

                RewriteRule .* 防盗地址 //设定重写的地址
                ```
    * 可以通过仿造Referer来反防盗链

* HTTP协议缓存空寂
    * 在网络上有一些缓存服务器，另外浏览器自身也有缓存功能
    * 如图片的缓存
        * 图片的下载过程
            1. 第一次请求时，是200
            2. 之后再请求时，是304 Not Modified 未修改状态
        * 原理
            * 当第一次访问是，图片会正常下载，返回200

