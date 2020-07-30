const Router = require('koa-router');
const router = new Router();

router.get('/getdata', async ctx=>{
    // 直接返回请求字符串
    ctx.body = ctx.querystring;
});

router.post('/postdata', async ctx=>{
    // 直接返回请求字符串
    ctx.body = ctx.request.body;
});

module.exports = router;