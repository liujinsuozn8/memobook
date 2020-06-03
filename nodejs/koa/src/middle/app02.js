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
    console.log('news middle start')

    await next()

    console.log('news middle end')
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