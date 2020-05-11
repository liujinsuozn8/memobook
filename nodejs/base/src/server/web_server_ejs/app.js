const http = require("http")
const url = require("url")
const routes = require("./module/routes")
const ejs = require('ejs')

http.createServer((req, res) => {
    // 1. 处理静态资源
    routes.static(req, res, 'static')

    // 2. 处理非静态资源，如果已经在静态资源中搜索到资源了，则这部分不执行
    // 解析url
    let pathname = url.parse(req.url).pathname

    // 获取请求类型
    let reqMethod = req.method
    console.log(reqMethod)
    // 根据不同的url执行不同的逻辑
    if (pathname === '/ejsdemo'){
        let groups = [
            {title:'group01'},
            {title:'group02'},
            {title:'group03'},
            {title:'group04'},
            {title:'group05'}
        ]

        // 渲染模板
        ejs.renderFile('./views/ejsdemo.ejs', {msg:"test msg", groups:groups}, (err, data)=>{
            sendResponse(res, data)
        })
    }
    else if (pathname === '/news'){
        // 解析GET请求的参数
        const query = url.parse(req.url, true).query
        sendResponse(res, JSON.stringify(query))
    }
    else if (pathname === '/tologin'){
        // 加载login页面
        ejs.renderFile('./views/login.ejs', {}, (err, data)=>{
            sendResponse(res, data)
        })
    }
    else if (pathname === '/dologin'){
        sendResponse(res, "this is register")
    }
    else if (pathname === '/register') {
        sendResponse(res, "this is register")
    }
    else if (pathname === '/admin') {
        sendResponse(res, "this is admin")
    }
    else {
        sendResponse(res, "404:no handler", 404)
    }

}).listen(8081)

function sendResponse(res, data, resultCode=200, srcType='text/html'){
    // 构造请求头
    res.writeHead(resultCode, {'Content-type':srcType + ";charset='utf-8'"})
    // 如果数据是空则直接返回，不能设置null，否则会产生异常
    if (data === null){
        res.end()
        return
    }
    
    // 设置响应数据
    if (data instanceof Array){
        for(let n in data){
            res.write(n)
        }
    } else {
        res.write(data)
    }
    res.end()
}