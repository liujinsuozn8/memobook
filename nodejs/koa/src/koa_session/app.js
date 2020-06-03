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