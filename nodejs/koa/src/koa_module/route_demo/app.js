const Koa = require('koa')
const Router = require('koa-router')
const adminRouter = require('./routes/admin')

let app = new Koa()
let router = new Router()

router.get('/', async (ctx)=>{
    ctx.body = 'index'
})

// router.get('/admin', async (ctx)=>{
//     ctx.body = 'admin'
// })

// 配置子路由
router.use('/admin', adminRouter.routes())

app.use(router.routes())
app.use(router.allowedMethods())

app.listen(8081)