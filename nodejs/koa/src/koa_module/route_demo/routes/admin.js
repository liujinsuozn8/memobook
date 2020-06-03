// 模块化路由
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