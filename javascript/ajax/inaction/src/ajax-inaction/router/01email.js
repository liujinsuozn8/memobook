const router = new require('koa-router')();

router.get('/emailCheck', async ctx=>{
    // 1. 获取参数 email
    const paramEmail = ctx.request.query.email;
    // 2. 设置默认的 email 数组
    const defaultEmails = [
        'aaabbbcc@123.com',
        'qwerty123@qq.com',
        'asdf12@qq.com',
    ];

    // 3. 如果请求参数在默认数组中，则已使用，返回失败
    if (defaultEmails.includes(paramEmail)){
        ctx.status = 400;
        ctx.body = { msg: 'has been used'};
    } else {
        // 4. 如果请求参数不在默认数组中，则未使用，返回成功
        ctx.body = { msg: 'usable'};
    }
});

module.exports = router;