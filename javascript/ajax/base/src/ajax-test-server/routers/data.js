// ajax使用方法测试
const Router = require('koa-router');

const router = new Router();

router.get('/usage-step-data', async ctx=>{
    console.log(ctx.req.url);
    ctx.body = {name:'testName', age:22};
});

// 返回JSON格式的响应数据
router.get('/usage-res-json-data', async ctx=>{
    ctx.body = {array:[111,222,333,444]};
});
// 返回文本格式的响应数据
router.get('/usage-res-text-data', async ctx=>{
    ctx.body = 'abcdefg';
});

router.get('/usage-get-param', async ctx=>{
    // 获取请求参数字符串
    console.log(ctx.querystring);
    // 获取请求参数对象
    console.log();
    // 直接将请求参数对象返回
    ctx.body = ctx.request.query;
});

router.post('/usage-post-param', async ctx=>{
    // 获取请求参数对象
    console.log(ctx.request.body);

    // 直接将请求参数对象返回
    ctx.body = ctx.request.body;
});

// 处理 post 请求的JSON格式参数
router.post('/usage-post-json-param', async ctx=>{
    // 获取请求参数对象
    console.log(ctx.request.body);

    // 直接将请求参数对象返回
    ctx.body = ctx.request.body;
});

router.get('/usage-ready-state', async ctx=>{
    ctx.body = 'usage-ready-state-data';
});

module.exports = router;