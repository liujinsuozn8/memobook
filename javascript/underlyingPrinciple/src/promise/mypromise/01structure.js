// 定义Promise的整体结构

// Promise构造函数的实现
function MyPromise(executor){
    executor(resolve, reject)
}

// 接收成功、失败的响应函数，并返回一个新的Promise对象
MyPromise.prototype.then = function(onResolved, onRejected){}

// 接收失败的响应函数，并返回一个新的Promise对象
MyPromise.prototype.catch = function(onRejected){}

// 返回一个成功的Promise对象
MyPromise.resolve = function(value){}
// 返回一个失败的Promise对象
MyPromise.reject = function(reason){}

// 返回一个Promise
// 只有所有Promise都成功时，才成功；只要有一个失败，则立刻返回一个失败的Promise
MyPromise.all = function(promises){}

// 返回一个Promise。返回第一个完成的 Promise
MyPromise.race = function(promises){}

module.exports = MyPromise