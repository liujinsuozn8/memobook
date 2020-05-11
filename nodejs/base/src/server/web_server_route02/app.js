const http = require("http")
const url = require("url")
const routes = require("./module/routes")

http.createServer((req, res) => {
    // 1. 处理静态资源
    routes.static(req, res, 'static')

    // 2. 处理非静态资源，如果已经在静态资源中搜索到资源了，则这部分不执行
    // 解析url
    let pathname = url.parse(req.url).pathname

    // 根据不同的url执行不同的逻辑
    if (pathname === '/login'){
        res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
        res.end("this is login")
    } else if (pathname === '/register') {
        res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
        res.end("this is register")
    } else if (pathname === '/admin') {
        res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
        res.end("this is admin")
    } else {
        res.writeHead(404, {'Content-type':"text/html;charset='utf-8'"})
        res.end("404:no handler")
    }

}).listen(8081)