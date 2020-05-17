// 1. 将字符串转换为Buffer
var str = "hello world"
var buf = Buffer.from(str)
console.log(str.length) // 返回字符个数
//输出： 11
console.log(buf)
//输出： <Buffer 68 65 6c 6c 6f 20 77 6f 72 6c 64>
console.log(buf.length) // 返回占用内存的大小
//输出： 11
console.log(buf.toString()) // 将Buffer转换为String
//输出： hello world

// 2. 创建指定长度的Buffer

// 2.1 不推荐使用构造器
// var buf02 = new Buffer(1024);
// console.log(buf02.length)

// 2.2
var buf02 = Buffer.alloc(10)
buf02[0] = 0x68
buf02[1] = 0x65
buf02[2] = 0x6c
console.log(buf02[1]) // 直接打印输出二进制
console.log(buf02[1].toString(16)) // 直接打印输出二进制输出

// Buffer遍历
for(var i=0; i<buf02.length; i++){
    console.log(buf02[i])
}

// 3. allocUnsafe
var buf0301 = Buffer.alloc(10)
var buf0302 = Buffer.allocUnsafe(10)
console.log(buf0301)
console.log(buf0302)