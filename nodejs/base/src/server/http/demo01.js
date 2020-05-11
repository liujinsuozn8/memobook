// 使用const声明，使外部无法修改
const http = require("http")

/*
    创建web服务
    (request, response)=>{}
        request：浏览器发送的请求数据
        response：发送给浏览器的响应
 */
http.createServer((req, res)=>{
    console.log(req.url) // 获取url
    // 设置响应头
    // 200表示成功，文件类型是html，字符集是utf-8
    res.writeHead(200, {"Content-type":"text/html;charset='utf-8'"})

    // 向页面写入html头，标识html的字符编码，来防止中文乱码
    res.write("<meta charset='utf-8'>")
    // 向页面输出一段内容
    res.write("this is nodejs\n")
    // 输出包含中文字符的内容
    res.write("this is 中\n")

    // 结束响应，这行代码必须写
    // 结束的同时，可以向页面输出一段内容
    res.end("this is end")
}).listen(8081)// 监听端口

console.log("server run as http://127.0.0.1:8081")