const http = require("http")
const routes = require("./module/routes")
const ejs = require('ejs')

// console.log(routes)
// 注册web服务：
// routes的形式本身是 `function(req,res)`
// 所以可以作为server的参数，每当有请求时，就可以自动响应
http.createServer(routes).listen(8081)

// 注册静态资源路径，使用默认值
// routes.static('static')

routes.get('/register', (req, res) => {
    res.send("this is register")
})

routes.get('/news', (req, res) => {
    res.send(JSON.stringify(req.body))
})

routes.get('/login', (req, res) =>{
    // 加载login页面
    ejs.renderFile('./views/login.ejs', {}, (err, data)=>{
        res.send(data)
    })
})

routes.post('/login', (req, res) => {
    res.send(req.body)
})
