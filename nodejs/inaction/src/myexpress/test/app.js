const http = require('http')
const ejs = require('ejs')
const routes = require('./module/routes')

http.createServer(routes).listen(8081)

// 注册请求
routes.get('/news', (req, res)=>{
    res.send("this is news")
})

routes.get('/login', (req, res)=>{
    ejs.renderFile('./views/login.ejs', (err, data)=>{
        res.send(data)
    })
})

routes.post('/login', (req, res)=>{
    res.send(req.body)
})