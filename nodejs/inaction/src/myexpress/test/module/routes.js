const url = require('url')
const path = require('path')
const fs = require('fs')

function changeResponse(res){
    res.send = (data) => {
        res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
        res.end(data)
    }
    res.sendError = (data) => {
        res.writeHead(404, {'Content-type':"text/html;charset='utf-8'"})
        res.end(data)
    }
}

function tryGetStatic(req, res, root){
    // 获取路径
    let pathname = url.parse(req.url).pathname
    // 编辑路径
    pathname = './'+ root + (pathname === '/' ? '/index.html' : pathname)
    try {
        let data = fs.readFileSync(pathname)
        if (data){
            res.writeHead(200, {'Content-type': getSrcType(pathname) + ";charset='utf-8'"})
            res.end(data)
        }
    } catch (error) {
    }
}

function getSrcType(srcPath){
    switch (path.extname(srcPath)){
        case '.html':
            return 'text/html'
        case '.css':
            return 'text/css'
        case '.js':
            return 'text/javascript'
        default:
            return 'text/html'
    }
}

// 扩展res
let service = function(){
    let R = {
        GET:{},
        POST:{},
        staticPath:'static'
    }

    function app(req, res){
        // 获取url中的路径
        let parseObj = url.parse(req.url)
        let pathname = url.parse(req.url).pathname
        // 获取请求方式
        let method = req.method

        changeResponse(res)

        // 先搜索静态资源
        tryGetStatic(req, res, R.staticPath)

        // 再搜索非静态资源
        if (R[method][pathname]){
            if (method === 'GET'){
                req.body = parseObj.query
                R[method][pathname](req, res)
            } else {
                // 读取post请求的参数
                let postdata = ''
                req.on('data', chunk => {postdata += chunk})
                req.on('end', ()=>{
                    req.body = postdata
                    R[method][pathname](req, res)
                })
            }
        } else {
            res.sendError("error 404")
        }
        // 根据请求方式来获取不同的响应方法

    }

    // 注册get请求
    app.get = (url, cb) => {
        R.GET[url] = cb
    }
    // 注册post请求
    app.post = (url, cb) => {
        R.POST[url] = cb
    }
    // 注册静态资源的目录
    app.static = (staticPath) => {
        R.staticPath = staticPath
    }

    return app
}

module.exports = service()