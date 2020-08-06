const router = require('koa-router')();

// 请求超时测试
router.get('/test', async ctx=>{
    // 设置一个比较长的等待时间
    await new Promise( (resolve)=>{
        setTimeout(()=>{resolve()}, 3000)
    })
    ctx.body = {msg:'success'};
});

module.exports = router