var fs = require("fs")

// 创建读取流和写入流
var reader = fs.createReadStream("xxxx.png")
var writer = fs.createWriteStream("zzzz.png")

// 监听写入流的打开与关闭
writer.once("open", function(){console.log("writer opened")})
writer.once("close", function(){console.log("writer closed")})

// 监听读取流的打开与关闭
reader.once("open", function(){console.log("reader opened")})
reader.once("close", function(){
    console.log("reader closed")
    // 当读取流关闭时，关闭写入流
    writer.end()
})

// 读取数据
reader.on("data", function(data){
    // 进行数据的互写
    writer.write(data)
})