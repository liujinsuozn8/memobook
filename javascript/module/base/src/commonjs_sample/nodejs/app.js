let unip = require("uniq")
// 聚合其他模块
let module1 = require("./module/module1")
let module2 = require("./module/module2")
let module3 = require("./module/module3")

// 调用模块
module1.foo()
module2()
module3.foo()
module3.bar()

// 调用外部的包uniq
let result = unip(module3.arr)
console.log(result)

// 输出:
// module1
// module2
// modules3 foo
// modules3 bar
// [ 1, 2, 3, 4, 5 ]
