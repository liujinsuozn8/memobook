var fs = require("fs")

// 创建可写流
var stream = fs.createWriteStream("helloZ.txt")

// 通过open和close事件来监听流的打开与关闭
// 两个事件只会触发一次，所以使用once绑定事件性能更好
stream.once("open", function(){
    console.log("stream opened")
})

stream.once("close", function(){console.log("stream closed")})

// 写入数据
stream.write("qwerrtyyuui")
stream.write("asdfgh")
stream.write("zxcvb")

// 关闭流
stream.end()