const router = require('koa-router')();

// 使用 ctx.query 获取请求参数-------------------
router.get('/handle', async ctx => {
    ctx.query.type = 'get';
    ctx.body = ctx.query;
})

router.delete('/handle/:id', async ctx => {
    ctx.query.type = 'delete';
    ctx.query.param = ctx.params.id;
    ctx.body = ctx.query;
})

// 使用 ctx.request.body 获取请求参数-------------------
router.post('/handle', async ctx => {
    ctx.request.body.type = 'post';
    ctx.body = ctx.request.body
})

router.put('/handle', async ctx => {
    ctx.request.body.type = 'put';
    ctx.body = ctx.request.body
})

// 异常测试
router.get('/error', async ctx=>{
    ctx.status = 404;
    ctx.body = {msg: 'error test'}
})

// 超时测试
router.get('/timeout', async ctx=>{
    ctx.query.type = 'get';
    await new Promise(reject=>{
        setTimeout(()=>reject(), 3000);
    })
    ctx.body = ctx.query;
})


module.exports = router;