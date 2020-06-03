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