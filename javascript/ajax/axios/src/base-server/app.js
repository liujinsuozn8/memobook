const Koa = require('koa');
const static = require('koa-static');
const router = require('koa-router')();

const app = new Koa();
app.use(require('koa-body')({
    multipart: true,
    // 设置上传文件的最大值 1M
    formidable: {
        maxFileSize: 100*1024*1024
    }
}));
app.use(static('./public'));

app.use(async (ctx, next)=>{
    ctx.set('Access-Control-Allow-Origin', '*');
    ctx.set('Access-Control-Allow-Methods', 'get,post,put,delete');
    await next();
})

router.use('/base', require('./router/base').routes());

app.use(router.routes());
app.use(router.allowedMethods());

app.listen(3333);