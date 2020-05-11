const path = require("path")
const fs = require("fs")
const url = require("url")

exports.static = (req, res, root) => {
    // 获取并过滤请求
    if (req.url === '/favicon.ico'){
        res.writeHead(200, {"Content-type":"text/html;charset='utf-8"})
        res.end()
        return
    }

    // 1. 整理路径
    let pathname = url.parse(req.url).pathname
    let srcPath = "./" + root + (pathname === '/' ? '/index.html' : pathname)
    console.log(srcPath)

    // 2. 读取资源
    fs.readFile(srcPath, (err, data)=>{
        // 3. 如果无法读取文件，返回404
        if(err){
            res.writeHead(404, {"Content-type":"text/html;charset='utf-8'"})
            console.log("read err")
            res.end('404')
            return
        }

        // 4. 返回资源
        res.writeHead(200, {"Content-type": getSrcType(srcPath) + ";charset='utf-8'"})
        res.end(data)
    })
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