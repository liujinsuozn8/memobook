
// G用于保存url与响应方法
let G ={}

// 定义一个注册方法
let app = function(req, res){
    if (G['/login']){
        G['/login'](req, res)
    }
}

// 在函数对象上设置get请求的响应方法
app.get = function(str, cb){
    // 注册url与响应方法
    G[str] = cb
}

// 配置路由
app.get("/login", (req, res)=>{console.log("this is login")})

// 调用路由
setTimeout(()=>{
    app("req", 'req')
},2000)