const http = require("http")
const routes = require("./module/routes")

http.createServer((req, res) => {
    routes.static(req, res, 'static')
}).listen(8081)