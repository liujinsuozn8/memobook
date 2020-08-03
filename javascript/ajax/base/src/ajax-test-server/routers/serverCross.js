const router = require('koa-router')();
const request = require('request');

// 通过服务器绕过同源策略
router.get('/data', async ctx=>{
    //  4. 向其他服务器发送请求
    ctx.body = await sendOther();
});

// 1. 发送异步请求
async function sendOther(){
    return new Promise( (resolve, reject)=> {
        // 2. 向非同源服务器发送请求
        request('http://localhost:5555/corsData', function(error, response, body){
            // 3. 将请求数据返回
            resolve(body);
        });
    })
}

module.exports=router;