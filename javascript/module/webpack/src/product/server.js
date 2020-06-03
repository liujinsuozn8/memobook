const Koa = require('koa')
const Router = require('koa-router')
const static = require('koa-static')

let app = new Koa()
let router = new Router()

// 缓存资源 1小时
app.use(static('build', {maxAge: 1000 * 3600}))

app.use(router.routes())
app.use(router.allowedMethods())
app.listen(9999)