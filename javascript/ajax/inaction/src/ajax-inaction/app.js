const Koa = require('koa');
const Router = require('koa-router');
const static = require('koa-static');
const app = new Koa();
const router = new Router();

app.use(require('koa-body')());
app.use(static('./public'));

router.get('/test', async ctx=>{
    ctx.body = 'testdata';
});

router.use('/01email', require('./router/01email').routes());
router.use('/02searchList', require('./router/02searchList').routes());
router.use('/03combSelect', require('./router/03combSelect').routes());

app.use(router.routes());
app.use(router.allowedMethods())

app.listen(3333);