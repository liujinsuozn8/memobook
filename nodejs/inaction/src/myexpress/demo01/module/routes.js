const path = require("path")
const fs = require("fs")
const url = require("url")
const ejs = require('ejs')

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

// 使用对象统一管理路由
module.exports ={
    // 处理静态资源
    static: (req, res, root) => {
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
    },

    // 处理非静态资源
    register: (req, res) => {
        res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
        res.end("this is register")
    },

    news: (req, res) => {
        // 解析GET请求的参数
        const query = url.parse(req.url, true).query
        res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
        res.end(JSON.stringify(query))
    },

    tologin: (req, res) =>{
        // 加载login页面
        ejs.renderFile('./views/login.ejs', {}, (err, data)=>{
            res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
            res.end(data)
        })
    },

    dologin: (req, res) => {
        // 执行登录
        // 捕获post请求
        let postData = ''

        req.on('data', chunk=>{
            postData += chunk
        })
        req.on('end', () => {
            try {
                res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
                res.end(postData)
            } catch (error) {
                res.writeHead(404, {'Content-type':"text/html;charset='utf-8'"})
                res.end('post error')
            }
        })
    },

    ejsdemo: (req, res)=> {
        let groups = [
            {title:'group01'},
            {title:'group02'},
            {title:'group03'},
            {title:'group04'},
            {title:'group05'}
        ]

        // 渲染模板
        ejs.renderFile('./views/ejsdemo.ejs', {msg:"test msg", groups:groups}, (err, data)=>{
            res.writeHead(200, {'Content-type':"text/html;charset='utf-8'"})
            res.end(data)
        })
    },

    error: (req, res) => {
        res.writeHead(404, {'Content-type':"text/html;charset='utf-8'"})
        res.end("error")
    }
}