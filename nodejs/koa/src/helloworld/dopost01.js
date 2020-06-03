const Koa = require('koa')
const Router = require('koa-router')
const views = require('koa-views')

// 1. 实例化
let app = new Koa()
let router = new Router()

// 配置渲染ejs的中间件
app.use(views('views', {extension : 'ejs'}))

// 配置get的路由
router.get('/login', async (ctx)=>{
    await ctx.render('login')
})

// 使用原生nodejs来获取post请求的参数
function getPostParams(ctx){
    return new Promise((resolve, reject)=>{
        try {
            // 异步的从流中读取数据
            let postData = ''
            ctx.req.on('data', chunck=>{ postData += chunck })
            ctx.req.on('end', ()=>{
                resolve(postData)
            })
        } catch (error) {
            reject(error)
        }
    })
}

// 配置post的路由,并使用原生nodejs来捕获post的请求参数
router.post('/login', async (ctx)=>{
    let postData = await getPostParams(ctx)
    ctx.body = postData
})

// 启动服务
app.use(router.routes())
app.use(router.allowedMethods())
app.listen(8081)