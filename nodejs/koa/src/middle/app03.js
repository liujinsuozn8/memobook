const Koa = require('koa')
const Router = require('koa-router')
const static = require('koa-static')
// const views= require('koa-views')
const bodyparser = require('koa-bodyparser')

let app = new Koa()
let router = new Router()

// 配置静态资源中间件
app.use(static('static'))
app.use(static(__dirname + '/public'))

// 配置模板渲染中间件
// app.use(views('view', {extension: 'ejs'}))

// 配置post请求处理中间件
app.use(bodyparser())

// 配置路由
router.post('/login', (ctx)=>{
    ctx.body = ctx.request.body
})

app.use(router.routes())
app.use(router.allowedMethods())
app.listen(8081)