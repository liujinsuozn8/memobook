// ajax异常测试
const Router = require('koa-router');

const router = new Router();

// 将 http 状态码设置为非 200
router.get('/status400', async ctx=>{
    ctx.status = 400;
    ctx.body = 'error data';
})

// 构造一个服务端执行异常，使用 http 状态码被设置为 500
router.get('/status500', async ctx=>{
    // 因为会抛出异常，所以返回数据的设置无效
    ctx.body = 'test data';
    // 模块内处于严格模式，无法使用未声明的变量，抛出一个执行异常
    console.log(abc);
})

module.exports = router;