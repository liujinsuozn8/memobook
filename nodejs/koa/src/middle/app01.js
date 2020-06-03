// 1. 引入 koa 模块，和 router 模块
const Koa = require('koa')
const Router = require('koa-router')

// 2. 实例化
let app = new Koa()
let router = new Router()

// 3. 配置路由
router.get('/', async (ctx)=>{
    ctx.body = 'koa return'
})

// 6. 设置应用级中间件
// 匹配所有路由
// app.use(cb)
app.use(async (ctx, next)=>{
    console.log("application middle")

    // 当前路由匹配完成后，继续执行下一个匹配
    // 如果不写next，路由将终止
    // ctx.body = 'middle end'
    await next()
})

// 7. 设置路由级中间件
router.get('/news', async (ctx, next)=>{
    console.log("news middle")

    // 当前路由匹配完成后，继续执行下一个匹配
    // 如果不写next，路由将终止
    await next()
})

// 8. 错误处理中间件
app.use(async (ctx, next)=>{
    console.log('error middle')
    await next()

    if (ctx.status === 404){
        // ctx.body = '404 error'
    }
})

// 获取get请求的请求参数
// http://localhost:8081/news?asdf=aaaa&qwer=1234
router.get('/news', async (ctx)=>{
    console.log(ctx.query)
    console.log(ctx.request.query)
    console.log(ctx.querystring)
    console.log(ctx.request.querystring)
    ctx.body = 'news'
})

// 动态路由
// http://localhost:8081/dynamic/qwer/asdf1234
// { aid: 'qwer', tid: 'asdf1234' }
router.get('/dynamic/:aid/:tid', async (ctx)=>{
    // 获取动态路由的传值
    console.log(ctx.params)
    ctx.body = 'dynamic'
})

// 4. 启动koa
app.use(router.routes())
app.use(router.allowedMethods())

// 5. 开始监听端口
app.listen('8081')