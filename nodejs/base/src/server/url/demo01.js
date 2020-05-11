// 使用const声明，使外部无法修改
const http = require("http")
const url = require("url")

// 创建一个http服务
http.createServer((req, res)=>{
    res.writeHead(200, {"Content-type":"text/html;charset='utf-8"})

    // 解析url
    // 去除 chrome的默认请求：/favicon.ico
    if (req.url != '/favicon.ico'){
        const query = url.parse(req.url, true).query
        // 输出请求内容
        let queryStr = ''
        for (let p in query){
            queryStr += `${p} = ${query[p]},`
        }

        // queryStr = queryStr.replace(/,*$/, '')
        console.log(queryStr)
    }

    res.end("this is end")
}).listen(8081)// 监听端口
