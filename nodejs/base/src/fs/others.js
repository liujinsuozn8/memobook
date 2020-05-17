var fs = require("fs")
// var result = fs.readdirSync(".")
// console.log(result)

// var result02 = fs.mkdirSync('C:/Users/liujinsuo/Desktop/others/test')
// console.log(result02)

fs.watchFile("hello.txt", function(){
    console.log(arguments)
    console.log("changed")
})