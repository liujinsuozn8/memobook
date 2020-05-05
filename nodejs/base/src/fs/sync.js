var fs = require("fs")

// 同步文件操作
// 打开文件
var fd = fs.openSync("hello.txt", "w+")
// 写入文件
fs.writeSync(fd, "write:hello world")
// 关闭文件
fs.closeSync(fd)
