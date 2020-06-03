// 暴露两个函数，实际上是向 module.exports对象中添加了多个属性
exports.foo = function(){
    console.log("modules3 foo")
}
exports.bar = function(){
    console.log("modules3 bar")
}

// 暴露一个数组
exports.arr = [4,1,2,5,4,3,1,2]
