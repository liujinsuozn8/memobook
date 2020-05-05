var fs = require("fs")
var reader = fs.createReadStream("xxxx.png")
var writer = fs.createWriteStream("aaaa.png")
reader.pipe(writer)
