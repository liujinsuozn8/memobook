// 对象、类声明中，设置return、throw

var obj = {
    arr:[1,2,3,4],

    // 1. 如果 [Symbol.iterator] 是生成器，则无法设置 return、throw
    // *[Symbol.iterator](){
    //     for(let n of this.arr) yield n;
    // },


    // 2. 如果 [Symbol.iterator] 是普通函数
    [Symbol.iterator](){
        // 2.1 创建一个可迭代对象
        var iter = Array.prototype[Symbol.iterator].call(this.arr);

        // 2.2 为可迭代对象添加 `return` 方法
        iter['return'] = function(value){
            console.log('function return');
            return {value, done:true}
        };

        // 2.3 返回可迭代对象
        return iter;
    },
}

for(let i of obj) {
    if (i === 3) break;
    console.log(i);
}
// 输出:
// 1
// 2
// function return