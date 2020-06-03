const Koa = require('koa')
const Router = require('koa-router')
const views = require('koa-views')
const bodyParser = require('koa-bodyparser')

// 1. 实例化
let app = new Koa()
let router = new Router()

// 配置bodyParser中间件
app.use(bodyParser())

// 配置渲染ejs的中间件
app.use(views('views', {extension : 'ejs'}))

// 配置get的路由
router.get('/login', async (ctx)=>{
    await ctx.render('login')
})

// 配置post的路由,并使用bodyParser来捕获post的请求参数
router.post('/login', async (ctx)=>{
    ctx.body = ctx.request.body
})

// 启动服务
app.use(router.routes())
app.use(router.allowedMethods())
app.listen(8081)