// ie缓存测试
const Router = require('koa-router');

const router = new Router();

router.get('/random-data', async ctx =>{
    // 返回一个1-20的随机数
    const randomNum = Math.floor(Math.random() * 20 + 1);
    console.log(randomNum);
    ctx.body = randomNum;
});

module.exports = router;