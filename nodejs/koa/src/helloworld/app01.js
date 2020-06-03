// 1. 引入 koa 模块，和 router 模块
const Koa = require('koa')
const Router = require('koa-router')

// 直接通过 koa启动
// let app = new Koa()
// app.get(async (ctx)=>{
//     // 返回数据 = res.writeHead + res.end
//     ctx.body = 'koa return'
// })

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