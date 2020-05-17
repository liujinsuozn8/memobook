var fs = require("fs")
// 打开文件
var fd = fs.open("hello.txt", "w+", function(e, fd){
    if (e){
        console.log(e)
        return
    }

    // 写入文件
    fs.write(fd, "write:hello world by async", function(e){
            if (!e){
            console.log("writed")
            // 关闭文件
            fs.close(fd, function(){console.log("closed")})
        }else {
            console.log(e)
        }
    })
})