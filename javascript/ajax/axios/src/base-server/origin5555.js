const Koa = require('koa');
const router = require('koa-router')();
const app = new Koa();

app.use(require('koa-body')());
app.use(require('koa-static')('./public'));

app.use(async (ctx, next)=>{
    ctx.set('Access-Control-Allow-Origin', '*');
    ctx.set('Access-Control-Allow-Methods', 'get,post,put,delete');
    await next();
})

router.use('/base', require('./router/base').routes());

app.use(router.routes());
app.use(router.allowedMethods());

app.listen(5555);