const http = require("http")
const url = require("url")
const routes = require("./module/routes")
// const ejs = require('ejs')

http.createServer((req, res) => {
    // 1. 处理静态资源
    routes.static(req, res, 'static')

    // 2. 处理非静态资源，如果已经在静态资源中搜索到资源了，则这部分不执行
    // 解析url
    let pathname = url.parse(req.url).pathname

    // 获取请求类型
    // let reqMethod = req.method
    // console.log(reqMethod)
    // // 根据不同的url执行不同的逻辑
    // if (pathname === '/ejsdemo'){
    //     let groups = [
    //         {title:'group01'},
    //         {title:'group02'},
    //         {title:'group03'},
    //         {title:'group04'},
    //         {title:'group05'}
    //     ]

    //     // 渲染模板
    //     ejs.renderFile('./views/ejsdemo.ejs', {msg:"test msg", groups:groups}, (err, data)=>{
    //         res.writeHead(200, {'Content-type':srcType + ";charset='utf-8'"})
    //         res.end(data)
    //     })
    // }
    // else if (pathname === '/news'){
    //     // 解析GET请求的参数
    //     const query = url.parse(req.url, true).query
    //     res.writeHead(200, {'Content-type':srcType + ";charset='utf-8'"})
    //     res.end(JSON.stringify(query))
    // }
    // else if (pathname === '/tologin'){
    //     // 加载login页面
    //     ejs.renderFile('./views/login.ejs', {}, (err, data)=>{
    //         res.writeHead(200, {'Content-type':srcType + ";charset='utf-8'"})
    //         res.end(data)
    //     })
    // }
    // else if (pathname === '/dologin'){
    //     // 执行登录
    //     // 捕获post请求
    //     let postData = ''

    //     req.on('data', chunk=>{
    //         postData += chunk
    //     })
    //     req.on('end', () => {
    //         try {
    //             res.writeHead(200, {'Content-type':srcType + ";charset='utf-8'"})
    //             res.end(postData)
    //         } catch (error) {
    //             res.writeHead(404, {'Content-type':srcType + ";charset='utf-8'"})
    //             res.end(postData)
    //         }
    //     })
    // }
    // else if (pathname === '/register') {
    if (pathname === '/register') {
        res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
        res.end("this is register")
    }
    else if (pathname === '/admin') {
        res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
        res.end("this is admin")
    }
    else {
        res.writeHead(400, {'Content-type':"text/html;charset='utf-8'"})
        res.end("404:no handler")
    }

}).listen(8081)