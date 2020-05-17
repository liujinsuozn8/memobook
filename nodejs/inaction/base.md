<span id="catalog"></span>

### 目录
- [搭建简单的静态web服务](#搭建简单的静态web服务)
    - [静态web服务的搭建方法](#静态web服务的搭建方法)
    - [将静态资源访问封装为路由操作](#将静态资源访问封装为路由操作)
    - [封装静态与非静态资源的路由](#封装静态与非静态资源的路由)
    - [使用EJS模板引擎](#使用EJS模板引擎)
    - [处理GET与POST请求](#处理GET与POST请求)
- [手写路由框架express](#手写路由框架express)
    - [模块化封装路由的方法](#模块化封装路由的方法)
    - [原生Nodejs封装express框架](#原生Nodejs封装express框架)
        - [最终希望的路由调用方式](#最终希望的路由调用方式)
        - [服务注册与调用的基本实现](#服务注册与调用的基本实现)
        - [不同请求类型的注册与调用](#不同请求类型的注册与调用)
    - [注册并启动服务](#注册并启动服务)
- [](#)
- [](#)
- [](#)
- [](#)
- [](#)

# 搭建简单的静态web服务
## 静态web服务的搭建方法
[top](#catalog)
- 工程参考： [src/server/web_server_demo01](src/server/web_server_demo01)
- 工程结构
    ```
    ├─ app.js
    ├─ common
    │    └─ common.js
    └─ static
         ├─ hello.html
         ├─ index.html
         ├─ test.html
         ├─ css
         │    └─ index.css
         └─js
            └─ index.js
    ```
- 通过 `app.js` 启动服务，通过地址来访问static下的静态资源
    - 参考代码
        - [src/server/web_server_demo01/app.js](src/server/web_server_demo01/app.js)
    - 代码内容
        ```js
        const http = require("http")
        const fs = require("fs")
        const url = require("url")
        const commons = require('./common/common')

        http.createServer(serverHandle).listen(8081)

        function serverHandle(req, res){
            // 获取并过滤请求
            if (req.url === '/favicon.ico'){
                res.writeHead(200, {"Content-type":"text/html;charset='utf-8"})
                res.end()
                return
            }

            // 1. 整理路径
            let pathname = url.parse(req.url).pathname
            let srcPath = "./static" + (pathname === '/' ? '/index.html' : pathname)
            console.log(srcPath)

            // 2. 读取资源
            fs.readFile(srcPath, (err, data)=>{
                // 3. 如果无法读取文件，返回404
                if(err){
                    res.writeHead(404, {"Content-type":"text/html;charset='utf-8'"})
                    console.log("read err")
                    res.end('404')
                    return
                }

                // 4. 返回资源
                res.writeHead(200, {"Content-type": commons.getSrcType(srcPath) + ";charset='utf-8'"})
                res.end(data)
            })
        }
        ```

- `commons/common.js` 提供资源类型检查，遇到css、js等资源时，修改响应头的信息，使页面正常显示
    - [src/server/web_server_demo01/common/common.js](src/server/web_server_demo01/common/common.js)
    - 代码内容
        ```js
        const path = require("path")
        exports.getSrcType = (p) =>{
            switch (path.extname(p)){
                case '.html':
                    return 'text/html'
                case '.css':
                    return 'text/css'
                case '.js':
                    return 'text/javascript'
                default:
                    return 'text/html'
            }
        }
        ```

## 将静态资源访问封装为路由操作
[top](#catalog)
- 什么是路由?
    - 路由（Routing）=  URI + 请求方式（GET、POST等）
    - 通过路由可以确定如何响应客户端对服务器资源的访问

- 工程参考： [src/server/web_server_route](src/server/web_server_route)
- 工程结构
    ```
    ├─ app.js
    ├─ common
    │    └─ routes.js
    └─ static
         ├─ hello.html
         ├─ index.html
         ├─ test.html
         ├─ css
         │    └─ index.css
         └─js
            └─ index.js
    ```

- 将静态资源的访问封装为路由
    - 参考代码
        - [src/server/web_server_route/module/routes.js](src/server/web_server_route/module/routes.js)
    - 封装路由操作。由主逻辑进行调用，并将需要访问的资源根目录作为参数传入
        ```js
        exports.static = (req, res, root) => {
            // 获取并过滤请求
            if (req.url === '/favicon.ico'){
                res.writeHead(200, {"Content-type":"text/html;charset='utf-8"})
                res.end()
                return
            }

            // 1. 整理路径
            let pathname = url.parse(req.url).pathname
            let srcPath = "./" + root + (pathname === '/' ? '/index.html' : pathname)
            console.log(srcPath)

            // 2. 读取资源
            fs.readFile(srcPath, (err, data)=>{
                // 3. 如果无法读取文件，返回404
                if(err){
                    res.writeHead(404, {"Content-type":"text/html;charset='utf-8'"})
                    console.log("read err")
                    res.end('404')
                    return
                }

                // 4. 返回资源
                res.writeHead(200, {"Content-type": getSrcType(srcPath) + ";charset='utf-8'"})
                res.end(data)
            })
        }
        ```

- 通过调用路由操作，来简化主处理
    - 参考代码
        - [src/server/web_server_route/app.js](src/server/web_server_route/app.js)
    - 代码内容
        ```js
        const http = require("http")
        const routes = require("./module/routes")

        http.createServer((req, res) => {
            routes.static(req, res, 'static')
        }).listen(8081)
        ```

## 封装静态与非静态资源的路由
[top](#catalog)
- 进一步封装路由的问题
    - 已经封装了对静态资源的请求，当静态资源中无法搜索到指定资源时，就直接返回了404
    - 对静态资源的封装阻止了对其他请求的捕获
- 完整路由操作的思路
    - 将请求的捕获分为两类
        - 静态资源
        - 非静态资源
    - 捕获流程
        1. 先在静态资源中搜索资源，有则返回，没有则继续
        2. 在非静态资源的处理中搜索响应的处理方法，有则使用
        3. 如果没有找到响应方法，就返回404
    - 注意事项
        - 读取文件需要使用**同步方法**，否则在读取资源之前，异步方法会进入事件队列，导致静态与非静态资源都搜索不到响应方法，而产生异常

- 封装 静态路由操作。使用同步方式读取静态资源
    - 参考代码
        - [src/server/web_server_route02/module/routes.js](src/server/web_server_route02/module/routes.js)
    - 代码内容
        ```js
        exports.static = (req, res, root) => {
            // 获取并过滤请求
            if (req.url === '/favicon.ico'){
                res.writeHead(200, {"Content-type":"text/html;charset='utf-8"})
                res.end()
                return
            }

            // 1. 整理路径
            let pathname = url.parse(req.url).pathname
            let srcPath = "./" + root + (pathname === '/' ? '/index.html' : pathname)
            console.log(srcPath)

            // 2. 同步 读取资源
            try {
                let data = fs.readFileSync(srcPath)
                console.log(data.toString())

                // 4. 返回资源
                if(data){
                    res.writeHead(200, {"Content-type": getSrcType(srcPath) + ";charset='utf-8'"})
                    res.end(data)
                }

                // 3. 如果无法读取文件，则返回，继续执行非静态资源的处理
            } catch (error) {
            }
        }
        ```

- 在主逻辑中执行静态与非静态资源的搜索
    - 参考代码
        - [src/server/web_server_route02/app.js](src/server/web_server_route02/app.js)
    - 代码内容
        ```js
        http.createServer((req, res) => {
            // 1. 处理静态资源
            routes.static(req, res, 'static')

            // 2. 处理非静态资源，如果已经在静态资源中搜索到资源了，则这部分不执行
            // 解析url
            let pathname = url.parse(req.url).pathname

            // 根据不同的url执行不同的逻辑
            if (pathname === '/login'){
                res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
                res.end("this is login")
            } else if (pathname === '/register') {
                res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
                res.end("this is register")
            } else if (pathname === '/admin') {
                res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
                res.end("this is admin")
            } else {
                res.writeHead(404, {'Content-type':"text/html;charset='utf-8'"})
                res.end("404:no handler")
            }

        }).listen(8081)
        ```

## 使用EJS模板引擎
[top](#catalog)
- 安装EJS
    - npm i ejs --save
- ejs模板默认以 `.ejs` 结尾
- 渲染模板的方法
    - 语法
        ```js
        ejs.renderFile('动态资源的路径', 绑定资源对象 [, options], callback(err,data))
        ```
    - 通过callback可以获取到渲染后的数据
- 在页面绑定数据的方式
    - `<%=变量名%>`
    - `<%js代码%>`

- 示例
    - 模板页面
        - 参考代码
            - [src/server/web_server_ejs/views/ejsdemo.ejs](src/server/web_server_ejs/views/ejsdemo.ejs)
        - 页面内容
            ```html
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>ejsdemo</title>
            </head>
            <body>
                <p>ejsdemo</p>
                <!-- 绑定变量 -->
                <span><%=msg%></span>
                <ul>
                    <!-- 通过js的方式循环赋值 -->
                    <%for(let n of groups){%>
                    <li><%=n.title %></li>
                    <%}%>
                </ul>
            </body>
            </html>
            ```
    - 在后端渲染模板
        - 参考代码
            - [src/server/web_server_ejs/app.js](src/server/web_server_ejs/app.js)
        - 代码内容
            ```js
            http.createServer((req, res) => {
                // ...
                // ...
                // 根据不同的url执行不同的逻辑
                if (pathname === '/ejsdemo'){
                    let groups = [
                        {title:'group01'},
                        {title:'group02'},
                        {title:'group03'},
                        {title:'group04'},
                        {title:'group05'}
                    ]

                    // 渲染模板
                    ejs.renderFile('./views/ejsdemo.ejs', {msg:"test msg", groups:groups}, (err, data)=>{
                        res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
                        res.end(data)
                    })
                }
                //...
                //...
            }).listen(8081)
            ```

## 处理GET与POST请求
[top](#catalog)
- 请求方式的判断方法
    - 获取请求方式：`request.method`
    - 属性内容：http请求方式，字符全部大写，如：GET、POST等等
- 处理GET请求
    - request.method === 'GET'
    - 获取url: `url.parse(req.url).pathname`
    - 获取请求参数: `url.parse(req.url).query`
- 与处理POST请求
    - 获取请求参数的方法
        - 需要处理两个事件
            - ondata，接收数据
            - onend，数据接收完毕
    - 处理示例
        ```js
        let postData = ''
        // 接收数据，数据以流的方式来传输
        req.on('data', postDataChunk => {
            postData += postDataChunk
        })
        // 数据接收完，执行回调函数
        req.on('end', () => {
            // ...
        })
        ```

- 示例
    - 登录页面，发送post请求执行登录
        - 参考代码
            - [src/server/web_server_ejs/views/login.ejs](src/server/web_server_ejs/views/login.ejs)
            - [src/server/web_server_ejs/app.js](src/server/web_server_ejs/app.js)
        - html内容
            ```html
            <form action="/dologin" method="post">
                username:<input type="text" name="username"><br>
                password:<input type="text" name="password"><br>
                <input type="submit" value="login">
            </form>
            ```
        - post请求解析
            ```js
            else if (pathname === '/dologin'){
                // 执行登录
                // 捕获post请求
                let postData = ''

                req.on('data', chunk=>{
                    console.log(`chunk = ${chunk}`)

                    postData += chunk
                })
                req.on('end', () => {
                    try {
                        //  console.log(JSON.parse(postData))
                        sendResponse(res, postData)
                    } catch (error) {
                        sendResponse(res, postData, 404)
                    }
                })
            }
            ```

# 手写路由框架express
## 模块化封装路由的方法
[top](#catalog)
- web_server_ejs 工程的问题
    - [搭建简单的静态web服务](#搭建简单的静态web服务) 的 [src\server\web_server_ejs](src\server\web_server_ejs) 项目，将路由的主要控制逻辑都写在了 `app.js` 文件中
    - 代码都写在一个文件中不好维护

- 封装模块化路由
    - 封装的方法
        - 将 `app.js` 中的路由控制代码全部移动到 `module/routes.js`下
        - 将路由的逻辑以方法的形式保存在一个对象中来管理，然后将对象bao
        - 通过: `routes[请求url](req,res)` 的方式调用
        - 调用方法时需要使用 `try catch`，在发生异常时，调用 `routes['error'](req,res)`

- 示例
    - 参考代码
        - [src/myexpress/demo01/app.js](src/myexpress/demo01/app.js)
        - [src/myexpress/demo01/module/routes.js](src/myexpress/demo01/module/routes.js)
    - 封装路由: `routes.js`
        ```js
        // 使用对象统一管理路由
        module.exports ={
            // 处理静态资源
            static: (req, res, root) => {
                // 获取并过滤请求
                if (req.url === '/favicon.ico'){
                    res.writeHead(200, {"Content-type":"text/html;charset='utf-8"})
                    res.end()
                    return
                }

                // 1. 整理路径
                let pathname = url.parse(req.url).pathname
                let srcPath = "./" + root + (pathname === '/' ? '/index.html' : pathname)
                console.log(srcPath)

                // 2. 同步 读取资源
                try {
                    let data = fs.readFileSync(srcPath)

                    // 4. 返回资源
                    if(data){
                        res.writeHead(200, {"Content-type": getSrcType(srcPath) + ";charset='utf-8'"})
                        res.end(data)
                    }

                    // 3. 如果无法读取文件，则返回，继续执行非静态资源的处理
                } catch (error) {
                }
            },

            // 处理非静态资源
            register: (req, res) => {
                res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
                res.end("this is register")
            },

            // ...
            // ...

            // 异常处理
            error: (req, res) => {
                res.writeHead(404, {'Content-type':"text/html;charset='utf-8'"})
                res.end("error")
            }
        }
        ```
    - 调用路由: `app.js`
        ```js
        http.createServer((req, res) => {
            // 1. 处理静态资源
            routes.static(req, res, 'static')

            // 2. 处理非静态资源，如果已经在静态资源中搜索到资源了，则这部分不执行
            // 解析url
            let pathname = url.parse(req.url).pathname
            // 清除url签名的第一个 `/`
            pathname = pathname.replace(/^\//, "")
            try {
                routes[pathname](req, res)
            } catch (error) {
                routes['error'](req, res)
            }

        }).listen(8081)
        ```

## 原生Nodejs封装express框架
### 最终希望的路由调用方式
[top](#catalog)
```js
app.get("url路径", (req, res) => {...})
app.post("url路径", (req, res) => {...})
```

### 服务注册与调用的基本实现
[top](#catalog)
- 实现方法
    1. 定义一个`function`，用于执行已注册的请求的响应方法
    2. 设置一个模块私有的变量，用于保存请求与响应方法
    3. 在`function`上添加注册请求的get、post等方法
    4. 使用时，通过函数对象的方法注册请求与响应方法，直接调用函数对象来执行响应
- 参考代码
    - [src/myexpress/demo02/module/_idea.js](src/myexpress/demo02/module/_idea.js)
- 基本实现
    ```js
    // 2. G用于保存请求与对应的响应方法
    let G ={}

    // 1. 定义一个方法，用于执行已注册的请求的响应方法
    let app = function(req, res){
        if (G['/login']){
            G['/login'](req, res)
        }
    }

    // 3. 在函数对象上添加方法，用于【注册get请求的响应】
    app.get = function(str, cb){
        // 注册url与响应方法
        G[str] = cb
    }

    // 配置路由
    app.get("/login", (req, res)=>{console.log("this is login")})

    // 调用路由
    setTimeout(()=>{
        app("req", 'req')
    },2000)
    ```

### 不同请求类型的注册方法与调用
[top](#catalog)
- 将请求分为三类分别设置，并分别保存在不同的注册对象中来管理
    1. get
    2. post
    3. 静态资源

- 调用流程
    1. 先在静态资源中检查，如果有则读取资源并返回响应
    2. 如果没有，则根据请求类型：get/post，在不同的注册对象中搜索请求，如果有则处理，没有则返回 4040

- 示例
    - 参考代码
        - [src/myexpress/demo02/module/routes.js](src/myexpress/demo02/module/routes.js)
    - 调用与注册
        ```js
        let service = () => {
            // G用于保存对象
            let R = {
                GET:  {},
                POST: {},
                staticPath: 'static' // 设置默认静态服务路径
            }

            // 主逻辑，负责请求的搜索与调用
            function app(req, res){
                let pathname = url.parse(req.url).pathname
                let method = req.method

                changeResponse(res)

                // 尝试获取静态资源
                tryGetStatic(req, res, R.staticPath)

                // 如果静态资源中未搜索到，则在非静态资源中进行搜索
                if (R[method][pathname]){
                    if (method === 'GET'){
                        // 将请求参数附加到req对象中
                        req.body = url.parse(req.url, true).query
                        R[method][pathname](req, res)
                    } else {
                        // 如果是post请求，则读取请求参数
                        let postData = ""
                        req.on('data', chunk=>{ postData += chunk})
                        req.on('end', ()=> {
                            // 将请求参数附加到req对象中
                            req.body = postData
                            R[method][pathname](req, res)
                        })
                    }
                }else{
                    res.sendError("404 error")
                }
            }

            // 请求的注册方法
            // 注册get请求
            app.get = function(str, cb){
                R.GET[str] = cb
            }

            // 注册post请求
            app.post = function(str, cb){
                R.POST[str] = cb
            }

            // 注册静态服务的路径
            app.static = function(staticPath){
                R.staticPath = staticPath
            }

            return app
        }
        ```
    - 静态资源处理
        ```js
        function tryGetStatic(req, res, root) {
            // 1. 整理路径
            let pathname = url.parse(req.url).pathname
            let srcPath = "./" + root + (pathname === '/' ? '/index.html' : pathname)
            console.log(srcPath)

            // 2. 同步 读取资源
            try {
                let data = fs.readFileSync(srcPath)

                // 4. 返回资源
                if(data){
                    res.writeHead(200, {"Content-type": getSrcType(srcPath) + ";charset='utf-8'"})
                    res.end(data)
                }

                // 3. 如果无法读取文件，则返回，继续执行非静态资源的处理
            } catch (error) {
            }
        }
        ```

### 注册并启动服务
[top](#catalog)
- 参考代码
    - [src/myexpress/demo02/app.js](src/myexpress/demo02/app.js)
- 代码内容
    ```js
    // console.log(routes)
    // 注册web服务：
    // routes的形式本身是 `function(req,res)`
    // 所以可以作为server的参数，每当有请求时，就可以自动响应
    http.createServer(routes).listen(8081)

    // 注册静态资源路径，使用默认值
    // routes.static('static')

    routes.get('/register', (req, res) => {
        res.send("this is register")
    })

    routes.get('/news', (req, res) => {
        res.send(JSON.stringify(req.body))
    })

    routes.get('/login', (req, res) =>{
        // 加载login页面
        ejs.renderFile('./views/login.ejs', {}, (err, data)=>{
            res.send(data)
        })
    })

    routes.post('/login', (req, res) => {
        res.send(req.body)
    })
    ```

[top](#catalog)
