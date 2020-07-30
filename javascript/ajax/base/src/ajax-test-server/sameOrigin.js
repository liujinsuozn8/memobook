const Koa = require('koa');
const Router = require('koa-router');
const body = require('koa-body');
const static = require('koa-static');

let app = new Koa();
let router = new Router();

app.use(body());

// 同源测试服务器
app.use(static(__dirname + '/public'));

router.get('/data', async ctx=>{
    ctx.body = {data:'same origin test data'};
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

app.use(router.routes());
app.use(router.allowedMethods());
app.listen(5555);