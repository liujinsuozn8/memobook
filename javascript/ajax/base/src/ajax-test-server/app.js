const Koa = require('koa');
// const Koa = require('./node_modules/koa');   // node 13无法使用时，直接从 node_modules 引入
const Router = require('koa-router');
const static = require('koa-static');
const body = require('koa-body');

let app = new Koa();
let router = new Router();

app.use(static(__dirname + '/public'));
// 附加 `multipart: true`，用来解析 FormData
app.use(body({ multipart: true }));

router.post('/login', async ctx=>{
    ctx.body = ctx.request.body;
});

// 设置子路由
// ajax使用方法测试
router.use('/data', require('./routers/data').routes());
// ajax异常测试
router.use('/errtest', require('./routers/errtest').routes());
// ie缓存测试
router.use('/iecache', require('./routers/iecache').routes());
// ajax封装测试
router.use('/ajaxfn', require('./routers/ajaxfn').routes());
// FormData测试
router.use('/formData', require('./routers/formData').routes());

app.use(router.routes());
app.use(router.allowedMethods());
app.listen(3333);