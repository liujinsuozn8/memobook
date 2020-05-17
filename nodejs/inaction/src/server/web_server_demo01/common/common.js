const path = require("path")
exports.getSrcType = (p) =>{
    switch (path.extname(p)){
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