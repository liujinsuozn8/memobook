const router = new require('koa-router')();

var defaultKeywords = [
    'aaabcdef',
    'aaaqwert',
    'aaazxcvbn',
    'bbbasdf',
    'bbb12345',
    'bbb23456',
    'bbb45678',
    '汉字1234',
    '汉字asdfg',
    'sddf汉字vxcv',
]

router.get('/keywords', async ctx=>{
    // 1. 去除 key 的首尾空格
    const inputKey = ctx.query.key.trim();

    if (inputKey === ''){
        // 2. 如果key是空字符串，则返回空数组
        ctx.body = [];
    } else {
        // 3. 如果key不是空字符串，则进行搜索
        // console.log(inputKey);
        let result = [];
        for (let kw of defaultKeywords){
            if (kw.indexOf(inputKey) != -1){
                result.push(kw);
            }
        }

        ctx.body = result;
    }

});

module.exports = router;