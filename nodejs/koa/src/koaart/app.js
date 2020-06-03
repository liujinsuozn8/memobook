const Koa = require('koa')
const Router = require('koa-router')
const render = require('koa-art-template')

// 1. 实例化
let app = new Koa()
let router = new Router()

render(app, {
    root: __dirname + '/views',
    extname: '.html',
    debug: process.env.NODE_ENV !== 'production'
})


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