const router = new require('koa-router')()

// $.ajax 基本使用测试
router.get('/getbase', async ctx=>{
    ctx.body = {msg: 'success', ...ctx.query}
})

router.post('/postbase', async ctx=>{
    ctx.body = {msg: 'success', ...ctx.request.body}
})

// 返回一个异常，测试 error()
router.get('/errorbase', async ctx=>{
    ctx.status = 400;
    ctx.body = {msg: 'error', ...ctx.query}
})

module.exports = router;