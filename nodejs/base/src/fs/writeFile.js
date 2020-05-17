var fs = require("fs")
fs.writeFileSync("helloX.txt", "writeFileSync:hello world")

fs.writeFile("helloY.txt", "writeFile:hello world", {flag:"a"}, function(e){
    e ? console.log(e) : console.log("writed and closed")
})
