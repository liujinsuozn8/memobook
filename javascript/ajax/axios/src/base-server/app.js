const Koa = require('koa');
const static = require('koa-static');
const router = require('koa-router')();

const app = new Koa();
app.use(require('koa-body')());
app.use(static('./public'));

router.use('/base', require('./router/base').routes());

app.use(router.routes());
app.use(router.allowedMethods());

app.listen(3333);