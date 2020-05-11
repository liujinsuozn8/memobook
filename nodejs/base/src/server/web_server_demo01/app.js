const http = require("http")
const fs = require("fs")
const url = require("url")
const commons = require('./common/common')

http.createServer(serverHandle).listen(8081)

function serverHandle(req, res){
    // 获取并过滤请求
    if (req.url === '/favicon.ico'){
        res.writeHead(200, {"Content-type":"text/html;charset='utf-8"})
        res.end()
        return
    }

    // 1. 整理路径
    let pathname = url.parse(req.url).pathname
    let srcPath = "./static" + (pathname === '/' ? '/index.html' : pathname)
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
        res.writeHead(200, {"Content-type": commons.getSrcType(srcPath) + ";charset='utf-8'"})
        res.end(data)
    })
}