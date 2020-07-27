<span id="catalog"></span>

### 目录
- [koa简介](#koa简介)
- [相关内容的安装](#相关内容的安装)
- [koa路由](#koa路由)
    - [koa路由的基本使用方法](#koa路由的基本使用方法)
    - [获取get请求参数](#获取get请求参数)
    - [动态路由](#动态路由)
    - [处理post请求](#处理post请求)
        - [使用原生nodejs来处理post请求参数](#使用原生nodejs来处理post请求参数)
        - [使用koa-bodyparser中间件来处理post请求参数](#使用koa-bodyparser中间件来处理post请求参数)
    - [处理静态资源](#处理静态资源)
- [koa中间件](#koa中间件)
    - [koa中间件概述](#koa中间件概述)
    - [应用级中间件](#应用级中间件)
    - [路由级中间件](#路由级中间件)
    - [中间件、路由的执行顺序](#中间件、路由的执行顺序)
    - [静态资源中间件](#静态资源中间件)
- [在koa中使用Cookie](#在koa中使用Cookie)
- [在koa中使用Session](#在koa中使用Session)
- [koa中使用模板引擎](#koa中使用模板引擎)
    - [koa中使用ejs模板引擎](#koa中使用ejs模板引擎)
    - [koa使用art-template模板引擎](#koa使用art-template模板引擎)
- [koa应用生成器](#koa应用生成器)
    - [使用koa生成器创建项目](#使用koa生成器创建项目)
    - [生成内容的分析](#生成内容的分析)
- [koa模块化开发](#koa模块化开发)
    - [路由模块化开发](#路由模块化开发)
- [上下文对象ctx可用内容速查](#上下文对象ctx可用内容速查)
- [](#)
- [](#)
- [](#)
- [](#)

# koa简介
[top](#catalog)
- Nodejs异步编程模型的问题
    1. 回调地域
    2. 异步函数中可能会出现同步调用callback，导致数据的不一致性
- 什么是koa？
    - 一个web框架
    - 特点：
    - 为了解决Nodejs异步编程的问题
    - koa内部不绑定任何中间件，只提供一个轻量的函数库

- koa的特点
    - 可以**避免繁琐的回调函数嵌套**
    - 可以提升错误处理的效率
    - 与express相同的开发方式
    - 框架自带路由操作

- 安装koa
    - `npm i koa --save`

# 相关内容的安装
[top](#catalog)

|安装内容|指令|
|-|-|
|koa应用生成器|`npm i koa-generator -g`|
|koa|`npm i koa --save`|
|路由模块|`npm i koa-router --save`|
|渲染模板页面|`npm i koa-views --save`|
|处理post请求参数|`npm i koa-bodyparser --save`|
|处理静态资源|`npm i koa-static --save`|
|处理session|`npm i koa-session --save`|
|ejs模板引擎|`npm i ejs --save`|
|art-template模板引擎|`npm i art-template --save`|
|art-template模板引擎的支持|`npm i koa-art-template --save`|
|||


# koa路由
## koa路由的基本使用方法
[top](#catalog)
- 安装koa的路由模块
    - `npm i koa-router --save`
- 使用方法
    - 参考代码
        - [src/helloworld/app01.js](src/helloworld/app01.js)
    - 代码内容
        ```js
        // 1. 引入 koa 模块，和 router 模块
        const Koa = require('koa')
        const Router = require('koa-router')

        // 2. 实例化
        let app = new Koa()
        let router = new Router()

        // 3. 配置路由
        router.get('/', async (ctx)=>{
            // 返回数据 = res.writeHead + res.end
            ctx.body = 'koa return'
        })

        router.get('/news', async (ctx)=>{
            ctx.body = 'news'
        })

        // 4. 启动koa
        // 启动路由
        app.use(router.routes())
        // 可以设置也可以不设置，建议设置
        // 官方推荐用法，在 router.routes() 之后使用，会在所有的路由中间件最后调用
        // 如果有路由没有设置请求头，则会根据 ctx.status 设置res的响应头
        app.use(router.allowedMethods())

        // 5. 开始监听端口
        app.listen('8081')
        ```

## 获取get请求参数
[top](#catalog)
- get请求参数都封装在 `ctx.request` 对象中，同时也封装在 `ctx` 中
- 通过 `ctx` 获取参数
    1. `ctx.query`，返回格式化后的参数对象，推荐使用
    2. `ctx.querystring`，返回请求字符串
- 通过 `ctx.request` 获取参数
    1. `ctx.request.query`，返回格式化后的参数对象
    2. `ctx.request.querystring`，返回请求字符串

## 动态路由
[top](#catalog)
- 使用方法
    1. 使用 `/url/:动态参数名1/:动态参数名2...`，的方式配置路由
        - 可以同时配置多个动态参数
    2. 在响应方法中，通过：`ctn.params` 来获取动态路由中的参数

- 示例
    - 参考代码
        - [src/helloworld/app02.js](src/helloworld/app02.js)
    - 代码内容
        ```js
        router.get('/dynamic/:aid/:tid', async (ctx)=>{
            // 获取动态路由的传值
            console.log(ctx.params)
            ctx.body = 'dynamic'
        })
        ```

## 处理post请求
### 使用原生nodejs来处理post请求参数
[top](#catalog)
- 参考代码
    - [src/helloworld/dopost01.js](src/helloworld/dopost01.js)
- 主要内容
    ```js
    // 1. 实例化
    let app = new Koa()
    let router = new Router()

    // 使用原生nodejs来获取post请求的参数
    function getPostParams(ctx){
        return new Promise((resolve, reject)=>{
            try {
                // 异步的从流中读取数据
                let postData = ''
                ctx.req.on('data', chunck=>{ postData += chunck })
                ctx.req.on('end', ()=>{
                    resolve(postData)
                })
            } catch (error) {
                reject(error)
            }
        })
    }

    // 配置post的路由,并使用原生nodejs来捕获post的请求参数
    router.post('/login', async (ctx)=>{
        let postData = await getPostParams(ctx)
        ctx.body = postData
    })
    ```

### 使用koa-bodyparser中间件来处理post请求参数
[top](#catalog)
- 安装: `npm i koa-bodyparser --save`
- 使用方法
    ```js
    // 1. 引入包
    const bodyParser = require('koa-bodyparser')

    // 配置bodyParser中间件
    app.use(bodyParser())

    // 配置post的路由
    router.post('url', async (ctx)=>{
        // 获取post参数
        ctx.request.body
    })
    ```

- 示例
    - 参考代码
        - [src/helloworld/dopost02.js](src/helloworld/dopost02.js)
    - 主要内容
        ```js
        const Koa = require('koa')
        const Router = require('koa-router')
        const bodyParser = require('koa-bodyparser')

        // 1. 实例化
        let app = new Koa()
        let router = new Router()

        // 配置bodyParser中间件
        app.use(bodyParser())

        // 配置post的路由,并使用bodyParser来捕获post的请求参数
        router.post('/login', async (ctx)=>{
            ctx.body = ctx.request.body
        })
        ```

## 处理静态资源
[top](#catalog)
- 参考：[静态资源中间件](#静态资源中间件)

# koa中间件
## koa中间件概述
[top](#catalog)
- 什么是中间件？
    - 在匹配路由之前、之后，完成的操作
    - 类似于AOP切面编程
- koa中间件的功能
    - 执行请求代码
    - 修改请求和响应对象
    - 终结请求-响应循环
    - 调用堆栈中的下一个中间件
- koa中间件的分类
    - 应用级中间件，匹配所有的路由
    - 路由级中间件，匹配指定的路由
    - 错误处理中间件
    - 第三方中间件

## 应用级中间件
[top](#catalog)
- 使用方法
    ```js
    let app = new Koa()
    // 匹配所有请求
    app.use((ctx, next)=>{
        // 业务操作
        // ...
        await next() // 继续向下匹配
    })
    ```
- 应用级中间件的执行顺序
    - 无论在何处编码，总是最先执行。然后再执行其他中间件和路由响应
    - 多个应用级中间件按照**代码执行时**的**配置顺序**执行

- 配置说明
    - 在 callback 中，通过 `await next()`，继续执行下一个匹配
        - 如果不执行，路由将终止
    - 如果中间件内部执行了`ctx.body = ...` ，设置了响应，并且没有执行`await next()`，则请求不会到达指定的路由配置，而是直接返回

- 示例
    - 参考代码
        - [src/middle/app01.js](src/middle/app01.js)
    - 代码内容
        ```js
        // 6. 设置应用级中间件
        app.use(async (ctx, next)=>{
            console.log("application middle")

            // 当前路由匹配完成后，继续执行下一个匹配
            // 如果不写next，路由将终止
            await next()
        })
        ```

## 路由级中间件
[top](#catalog)
- 使用方法
    ```js
    let router = new Router()
    // 匹配指定路由
    router.get('url', (ctx, next)=>{
        // 业务操作
        await next() // 继续向下匹配
    })
    ```

- 路由级中间件的执行顺序
    - 按照**代码执行时**的**路由配置顺序**执行

- 配置说明
    - 相当于为同一个路由配置了**多个响应方法**
    - 在 callback 中，通过 `await next()`，继续执行下一个匹配
        - 如果不执行，路由将终止
    - 如果中间件内部执行了`ctx.body = ...` ，设置了响应，并且没有执行`await next()`，则请求不会到达指定的路由配置，而是直接返回

- 示例
    - 参考代码
        - [src/middle/app01.js](src/middle/app01.js)
    - 代码内容
        ```js
        // 7. 设置路由级中间件
        router.get('/news', async (ctx, next)=>{
            console.log("news middle")

            // 当前路由匹配完成后，继续执行下一个匹配
            // 如果不写next，路由将终止
            await next()
        })
        ```

## 错误处理中间件
[top](#catalog)
- 使用方法
    ```js
    app.use(async (ctx, next)=>{
        // 业务处理
        await next()

        if (ctx.status === 404){
            // 错误处理
            // ...
            ctx.body = ... // 手动设置异常响应内容，避免 Not Found 异常
        }
    })
    ```

- 配置说明
    - 本质上仍然是一个应用级中间件
    - 在 callback 中，通过 `await next()`，继续执行下一个匹配
        - 如果不执行，路由将终止
    - 应该在错误处理中设置异常响应内容，否则浏览器会产生 `Not Found` 异常

- 示例
    - 参考代码
        - [src/middle/app01.js](src/middle/app01.js)
    - 代码内容
        ```js
        // 8. 错误处理中间件
        app.use(async (ctx, next)=>{
            console.log('error middle')
            await next()

            if (ctx.status === 404){
                ctx.body = '404 error'
            }
        })
        ```

## 中间件、路由的执行顺序
[top](#catalog)
- 所有组件以**同步**的方式执行
- 每次执行到 `await next()` 时，会保存调用点，执行下一个匹配，然后在返回执行
- 示例
    - 参考代码
        - [src/middle/app02.js](src/middle/app02.js)
    - 代码内容
        ```js
        const Koa = require('koa')
        const Router = require('koa-router')

        let app = new Koa()
        let router = new Router()

        // 设置第一个应用级中间件
        app.use(async (ctx, next)=>{
            console.log("first middle start")
            await next()
            console.log("first middle end")
        })

        // 设置第二个应用级中间件
        app.use(async (ctx, next)=>{
            console.log("second middle start")
            await next()
            console.log("second middle end")
        })

        // 设置第错误处理中间件
        app.use(async (ctx, next)=>{
            console.log("error middle start")

            await next()

            if(ctx.status === 404){
                console.log("has error")
                ctx.body = '404 error'
            }

            console.log("second middle end")
        })

        // 配置路由级中间件
        router.get('/news', async (ctx, next)=>{
            console.get('news middle start')
            await next()
            console.get('news middle end')
        })

        // 配置路由
        router.get('/news', async (ctx)=>{
            console.log('news route start')
            ctx.body = 'news page'
            console.log('news route end')
        })

        app.use(router.routes())
        app.use(router.allowedMethods())
        app.listen(8081)
        ```
    - 执行结果
        - 正常的url：http://localhost:8081/news
            ```
            first middle start
            second middle start
            error middle start
            news middle start
            news route start
            news route end
            news middle end
            second middle end
            second middle end
            first middle end
            ```
        - 异常的url：http://localhost:8081/xxx
            ```
            first middle start
            second middle start
            error middle start
            has error
            second middle end
            second middle end
            first middle end
            ```

## 静态资源中间件
[top](#catalog)
- 安装: `npm i koa-static --save`
- 使用方法
    ```js
    // 引入中间件
    const static = require('koa-static')

    // 配置静态资源中间件，可以配置多个路径
    app.use(static('静态资源路径1'))
    app.use(static('静态资源路径2'))
    app.use(static('静态资源路径3'))
    // ...
    ```
- 可以通过 `__dirname + '路径'` 的方式使用绝对路径来设置
- 接收请求后，先搜索静态资源，如果没有搜索到则执行`next()`继续搜索
- 示例
    - 参考代码
        - [src/middle/app03.js](src/middle/app03.js)
    - 主要内容
        ```js
        const static = require('koa-static')

        // 配置静态资源中间件
        app.use(static('static'))
        app.use(static(__dirname + '/public'))
        ```

# 在koa中使用Cookie
[top](#catalog)
- 在koa中设置cookie
    ```js
    ctx.cookies.set(name, value [, options])
    ```
- 在koa中获取cookie
    ```js
    ctx.cookies.get( name )
    ```

- 可用options

    |option|描述|
    |-|-|
    |maxAge|一个表示毫秒数的数字，表示多少毫秒后过期|
    |expires|cookie 过期的 Date|
    |path|可访问cookie的路径。默认是 `/`，表示所有路由都可用|
    |domain|cookie 域名。默认当前域下的所有页面都可以访问|
    |secure|安全 cookie，默认为false。设置为 true，表示只有 https 可以访问|
    |httpOnly|是否只是服务器可以访问 cookie，默认是 true。如果是false，则服务器可以访问、在浏览器通过js也可以访问|
    |overwrite|布尔值，默认为false。表示是否覆盖同名的cookie|

# 在koa中使用Session
[top](#catalog)
- 安装：`npm i koa-session --save`
- 使用方法
    ```js
    // 引入
    const session = require('koa-session')

    // 配置中间件
    app.keys = ['some secret hurr'] //cookie签名
    const CONFIG={
        key: 'koa:sess',
        maxAge:86400000,
        overwrite:true,
        httpOnly:true,
        signed:true,
        rolling:false,
        renew:false,
    }

    app.use(session(CONFIG, app))

    router.get('/url', async (ctx)=>{
        // 设置session
        ctx.session.属性名 = 属性值
        // ...
    })

    router.get('/url', async (ctx)=>{
        // 获取session
        let p = ctx.session.属性名
        // ...
    })
    ```

- 中间件选项说明

    |option|默认值|描述|
    |-|-|-|
    |key|'koa:sess'|使用默认值|
    |maxAge|86400000|cookie的过期时间，需要修改|
    |overwrite|true|是否可以覆盖，设不设都可以覆盖|
    |httpOnly|true|true 表示只有服务器的js可以获取cookie，false 表示服务器和浏览器的js都可以获取cookie|
    |signed|true|默认 签名|
    |rolling|false|每次访问时，是否重新设置session的过期时间|
    |renew|false|每次访问时检查session的过期时间，只有快过期时才重新设置过期时间。**需要修改，最好使用true**|

- 示例
    - 参考代码
        - [src/koa_session/app.js](src/koa_session/app.js)
    - js内容
        ```js
        const Koa = require("koa")
        const Router = require('koa-router')
        const session = require('koa-session')

        let app = new Koa()
        let router = new Router()

        app.keys = ['some secret hurr'] //cookie签名
        const CONFIG={
            key: 'koa:sess',
            maxAge:86400000,
            overwrite:true,
            httpOnly:true,
            signed:true, 
            rolling:false,
            renew:true,
        }

        app.use(session(CONFIG, app))

        // 设置session
        router.get('/login', async (ctx)=>{
            ctx.session.user = 'testUser'
            ctx.body = 'login'
        })
        // 获取session
        router.get('/', async (ctx)=>{
            let user = ctx.session.user
            ctx.body = 'user:' + user
        })

        app.use(router.routes())
        app.use(router.allowedMethods())
        app.listen(8081)
        ```

# koa中使用模板引擎
## koa中使用ejs模板引擎
[top](#catalog)
- 安装
    - koa-views: `npm i koa-views --save`
    - ejs: `npm i ejs --save`
- 使用方法
    1. 引入 `koa-views`，并配置中间件，使koa可以使用ejs
        - 配置方式1，**模板的后缀名必须是 html**
            ```js
            app.use(views(
                '模板的保存路径',
                {
                    // 指定使用ejs模板引擎
                    map: { html: 'ejs' }
                }
            ))
            ```
        - 配置方式2，**模板的后缀名必须是 ejs**
            ```js
            app.use(views(
                '模板的保存路径',
                // 指定使用ejs模板引擎
                { 'extension': 'ejs' }
            ))
            ```
    2. 在路由中渲染模板
        ```js
        // 只渲染模板
        router.get('url', (ctx)=>{
            await ctx.render('模板文件名，没有文件后缀')
        })

        // 渲染模板，并设置参数
        router.get('url', (ctx)=>{
            await ctx.render('模板文件名，没有文件后缀', {
                参数名: 参数值,....
            })
        })
        ```
    3. 各页面的通用数据可以保存在 `ctx.state` 中，可以通过中间件来设置
        ```js
        app.use(async (ctx, next)=>{
            ctx.state = {
                属性名1: 属性值1,
                属性名2: 属性值2,
                ...
                ...
            }

            await next()
        })
        ```

- 示例
    - 参考代码
        - [src/koa_ejs/app.js](src/koa_ejs/app.js)
        - [src/koa_ejs/views/news.ejs](src/koa_ejs/views/news.ejs)
    - js内容
        ```js
        const Koa = require('koa')
        const Router = require('koa-router')
        const views = require('koa-views')

        // 1. 实例化
        let app = new Koa()
        let router = new Router()

        // 2. 配置 koa-views 中间件
        app.use(views(
            'views',
            { extension: 'ejs' }
        ))

        // 4. 通过中间件保存通用数据
        app.use(async (ctx, next)=>{
            ctx.state = {
                user: 'test user',
                address: 'test address'
            }

            await next()
        })

        // 3. 渲染页面
        router.get('/news', async (ctx)=>{
            console.log('this is news')
            await ctx.render('news', {
                msg: 'test msg',
                newList: [111,222,333],
                flg: false,
            })
        })

        // 5. 启动服务
        app.use(router.routes())
        app.use(router.allowedMethods())
        app.listen(8081)
        ```
    - ejs内容
        ```html
        <body>
            <!-- 引入其他模板，绑定html数据 -->
            <%- include("common/title.ejs") %>
            <br>

            <!-- 设置通用数据 -->
            user: <%=user%>
            <br>
            address: <%=address%>
            <br>
            <br>

            ejs news page:
            <br>
            msg: <%=msg%>

            <br>
            <br>
            array:
            <ul>
                <% for(let n of newList){ %>
                    <li><%=n%></li>
                <% } %>
            </ul>

            <%if (flg) {%>
                <span>flg = true</span>
            <% } else{ %>
                <span>flg = false</span>
            <% } %>
        </body>
        ```

## koa使用art-template模板引擎
[top](#catalog)
- 安装
    - art-template模板引擎: `npm i art-template --save`
    - art-template模板引擎的支持: `npm i koa-art-template --save`

- 使用方法
    ```js
    // 引入
    const render = require('koa-art-template')
    // 实例化
    let app = new Koa()
    let router = new Router()

    // 配置
    render(app, {
        root: __dirname + '目录'        // 保存资源的目录
        extname: '.html',               // 解析文件的后缀名
        debug: process.env.NODE_ENV !== 'production'    // 是否开启debug模式
    })

    router.get('/url', async (ctx)=>{
        // 渲染页面
        await ctx.render('资源路径', {
            属性名1: 属性值1,
            ...
        })
    })
    ```

# koa应用生成器
## 使用koa生成器创建项目
[top](#catalog)
- 通过koa脚手架生成工具，创建一个基于koa2的应用骨架
- 安装: `npm i koa-generator -g`
- 创建项目: `koa koa_demo`
    - 项目结构
        ```
        koa_demo
          ├─ app.js
          ├─ package.json
          ├─ bin
          │   └─ www
          ├─ public
          │   ├─ images
          │   ├─ javascripts
          │   └─ stylesheets
          │       └─ style.css
          ├─ routes
          │   ├─ index.js
          │   └─ users.js
          │
          └─ views
              ├─ error.jade
              ├─ index.jade
              └─ layout.jade
        ```
- 安装依赖: `npm i`
- 启动项目: `npm run start`

## 生成内容的分析
[top](#catalog)
- `app.js` 相当于一个包聚合器，并暴露给外部
- `routes/index.js`、`routes/users.js`，将各自业务中使用的路由封装为模块，并在 `app.js` 中进行导入

# koa模块化开发
## 路由模块化开发
[top](#catalog)
- 路由模块化开发的本质
    - 将 `app.js` 中的路由拆分到子路由模块，再由`app.js`进行引入
        - 即: 将 `app.js` 从路由管理者变为路由终结
    - 由不同的父级路径来管理不同的子路由
    - 最终形成**多层路由**的访问方式

- 开发方式
    1. 各业务路由单独封装到一个js文件，变成一个路由模块
    2. 每个路由模块向外暴露 `router` 对象
        - 暴露方式，只暴露 `router` 对象
            ```js
            module.exports = router
            ```
        - 暴露 `router` 对象的同时，启动路由
            ```js
            module.exports = router.routes()
            ```
    3. `app.js` 作为路由中介，导入路由模块，并设置子路由
        - 设置方式
            ```js
            router.use('子路由负责的路径', 路由模块.routes())
            app.use(.allowedMethods())
            ```
    4. 页面的访问方式：`站点名/应用名/子路由负责的路径/路由模块中的路径`
    5. 如果子路由比较复杂，可以按照 1-3 继续切分多级路由模块
        - 即一个 `路由.js` + `/路由/` 目录

- 如果直接使用 `router.use(路由模块.routes())` 来配置路由，则默认绑定到`/`目录下

- 示例
    - 参考代码
        - [src/koa_module/route_demo/app.js](src/koa_module/route_demo/app.js)
        - [src/koa_module/route_demo/routes/admin.js](src/koa_module/route_demo/routes/admin.js)

    - 路由模块: `admin.js`
        ```js
        const Router = require('koa-router')

        let router = new Router()

        router.get('/',async (ctx)=>{
            ctx.body = 'admin router: /'
        })

        router.get('/user',async (ctx)=>{
            ctx.body = 'admin router: /user'
        })

        router.get('/banner',async (ctx)=>{
            ctx.body = 'admin router: /banner'
        })

        router.get('/news',async (ctx)=>{
            ctx.body = 'admin router: /news'
        })

        module.exports = router
        ```
    - 配置子路由
        ```js
        const Koa = require('koa')
        const Router = require('koa-router')
        const adminRouter = require('./routes/admin')

        let app = new Koa()
        let router = new Router()

        router.get('/', async (ctx)=>{
            ctx.body = 'index'
        })

        // 配置子路由
        router.use('/admin', adminRouter.routes())

        app.use(router.routes())
        app.use(router.allowedMethods())

        app.listen(8081)
        ```

# 上下文对象ctx可用内容速查
[top](#catalog)

|可用内容|描述|
|-|-|
|ctx.body|返回响应|
|ctx.status|状态码，数字|
|ctx.request|封装后的get请求|
|ctx.query|get请求--js对象|
|ctx.request.query| get请求--js对象|
|ctx.querystring|get请求参数--js字符串|
|ctx.request.querystring|get请求参数--js字符串|
|ctx.req|http.createServer()中的原生req|
|ctx.request.body|使用 koa-bodyparser中间件后的post请求参数|
|ctx.cookies.set()|设置cookie|
|ctx.cookies.get()|获取cookie|
|ctx.session.属性名 = 属性值|设置session|
|ctx.session.属性名|获取session|
|ctx.render('模板文件名', 参数对象)|使用koa0views 中间件后，渲染模板，并绑定参数|
|ctx.state|用于保存各页面的通用数据|

[top](#catalog)