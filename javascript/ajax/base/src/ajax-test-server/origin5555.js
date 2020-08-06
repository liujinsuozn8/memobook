const Koa = require('koa');
// const Koa = require('./node_modules/koa');
const Router = require('koa-router');
const body = require('koa-body');
const static = require('koa-static');
const session = require('koa-session');

let app = new Koa();
let router = new Router();

app.use(body({
    multipart:true
}));

// 同源测试服务器
app.use(static(__dirname + '/public'));

router.get('/data', async ctx=>{
    ctx.body = {data:'cross origin test data'};
});


// 返回 JSONP 所需的代码字符串
router.get('/jsonpData', async ctx=>{
    // 创建代码片段，调用前端的响应函数，并设置数据设为实参
    const responseCode = 'loadData({data1:1234, data2:"abcd", data3:"testData"})'
    // 直接返回字符串格式的代码片段
    ctx.body = responseCode;
});

// 根据不同的函数名返回不同的代码片段
const CBStrs = {
    printData: 'printData({data1:1234, data2:"abcd", data3:"testData"})',
    concatData: 'concatData({data1:1234, data2:"abcd", data3:"testData"})',
}
router.get('/jsonpByFuncName', async ctx=>{
    const cbName = ctx.query.callback;
    if (!CBStrs.hasOwnProperty(cbName)){
        ctx.status = 400;
        ctx.body = 'no func';
    }

    ctx.body = CBStrs[cbName];
});

// 处理优化后的JSONP请求，需要处理随机函数名和请求参数
router.get('/getJsonp', async ctx=>{
    // 获取函数名
    const cbName = ctx.query.callback;
    // 创建代码片段，如果数据是对象需要转换为JSON字符串
    const resultCode = `${cbName}(${JSON.stringify(ctx.query)})`
    ctx.body = resultCode;
});

////////////////// CORS /////////////////////////////
// 通过中间件为所有请求设置请求头
app.use(async (ctx, next)=>{
    // 设置请求头
    // 1. 允许哪些客户端访问服务端
    ctx.set('Access-Control-Allow-Origin', 'http://localhost:3333')
    // 2. 设置客户端可以通过哪些请求方式访问服务端
    ctx.set('Access-Control-Allow-Methods', 'get,post')
    // 3. 设置可以携带cookie
    ctx.set('Access-Control-Allow-Credentials', true);
    await next()
})
// 处理 CORS 请求
router.get('/corsData', async ctx=>{
    ctx.body = 'corsData';
})


//// 携带cookie，设置session ///////
// 设置session
app.keys = ['some secret hurr'];
const CONFIG={
    key: 'koa:sess',
    maxAge:86400000,
    overwrite:true,
    httpOnly:true,
    signed:true, 
    rolling:false,
    renew:false,
}
app.use(session(CONFIG, app));

// cookie测试
router.post('/cookieLogin', async ctx=>{
    var username = ctx.request.body.username;
    var pwd = ctx.request.body.pwd;
    ctx.session.islogin = true;
    ctx.body = 'success';
})

router.get('/loginCheck', async ctx=>{
    if (ctx.session.islogin){
        ctx.body = 'success';
    } else {
        ctx.body = 'error';
    }
})

app.use(router.routes());
app.use(router.allowedMethods());
app.listen(5555);