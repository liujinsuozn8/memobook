// const Koa = require('koa');
const Koa = require('./node_modules/koa');
const Router = require('koa-router');
const static = require('koa-static');
const body = require('koa-body');

let app = new Koa();
let router = new Router();

app.use(static(__dirname + '/public'));
// 附加 `multipart: true`，用来解析 FormData
app.use(body({
    multipart: true,
    // 设置上传文件的最大值 1M
    formidable: {
        maxFileSize: 100*1024*1024
    }
}));

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
// 通过服务端绕过同源策略
router.use('/serverCross', require('./routers/serverCross').routes());
// jquery 测试
router.use('/jquery', require('./routers/jqueryTest').routes());
// 请求超时测试
router.use('/timeout', require('./routers/timeout').routes());

app.use(router.routes());
app.use(router.allowedMethods());
app.listen(3333);