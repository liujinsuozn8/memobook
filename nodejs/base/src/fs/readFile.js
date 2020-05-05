var fs = require("fs")

var buf = fs.readFileSync("xxxx.png")
console.log(buf)

// 读取文件并写入到新文件中
fs.readFile("xxxx.png", function(e, buf){
    if (e){
        console.log(e)
    } else {
        fs.writeFile("yyy.png", buf, function(){
            console.log("writed")
        })
    }
})
