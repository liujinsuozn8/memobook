const path = require("path")
const url = require("url")
const fs = require("fs")

// 扩展response对象
function changeResponse(res){
    res.send = data=>{
        res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
        if (data === null){
            res.end()
        } else {
            res.end(data)
        }
    }

    res.sendError = data => {
        res.writeHead(400, {'Content-type':"text/html;charset='utf-8'"})
        if (data === null){
            res.end()
        } else {
            res.end(data)
        }
    }
}

// 匹配静态资源
function tryGetStatic(req, res, root) {
    // 1. 整理路径
    let pathname = url.parse(req.url).pathname
    let srcPath = "./" + root + (pathname === '/' ? '/index.html' : pathname)
    console.log(srcPath)

    // 2. 同步 读取资源
    try {
        let data = fs.readFileSync(srcPath)

        // 4. 返回资源
        if(data){
            res.writeHead(200, {"Content-type": getSrcType(srcPath) + ";charset='utf-8'"})
            res.end(data)
        }

        // 3. 如果无法读取文件，则返回，继续执行非静态资源的处理
    } catch (error) {
    }
}

// 匹配静态资源的后缀来返回不同的请求头
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


// 将相关内容封装在一个函数中，防止泄漏
let service = () => {
    // G用于保存对象
    let R = {
        GET:  {},
        POST: {},
        staticPath: 'static' // 设置默认静态服务路径
    }

    // 主逻辑，负责请求的搜索与调用
    function app(req, res){
        let pathname = url.parse(req.url).pathname
        let method = req.method

        changeResponse(res)

        // 尝试获取静态资源
        tryGetStatic(req, res, R.staticPath)

        // 如果静态资源中未搜索到，则在非静态资源中进行搜索
        if (R[method][pathname]){
            if (method === 'GET'){
                // 将请求参数附加到req对象中
                req.body = url.parse(req.url, true).query
                R[method][pathname](req, res)
            } else {
                // 如果是post请求，则读取请求参数
                let postData = ""
                req.on('data', chunk=>{ postData += chunk})
                req.on('end', ()=> {
                    // 将请求参数附加到req对象中
                    req.body = postData
                    R[method][pathname](req, res)
                })
            }
        }else{
            res.sendError("404 error")
        }
    }

    // 请求的注册方法
    // 注册get请求
    app.get = function(str, cb){
        R.GET[str] = cb
    }

    // 注册post请求
    app.post = function(str, cb){
        R.POST[str] = cb
    }

    // 注册静态服务的路径
    app.static = function(staticPath){
        R.staticPath = staticPath
    }

    return app
}

module.exports = service()
