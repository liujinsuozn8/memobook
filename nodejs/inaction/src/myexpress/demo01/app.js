const http = require("http")
const url = require("url")
const routes = require("./module/routes")

http.createServer((req, res) => {
    // 1. 处理静态资源
    routes.static(req, res, 'static')

    // 2. 处理非静态资源，如果已经在静态资源中搜索到资源了，则这部分不执行
    // 解析url
    let pathname = url.parse(req.url).pathname
    // 清除url签名的第一个 `/`
    pathname = pathname.replace(/^\//, "")
    try {
        routes[pathname](req, res)
    } catch (error) {
        routes['error'](req, res)
    }

}).listen(8081)
