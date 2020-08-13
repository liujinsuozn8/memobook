// 1. 创建一个 arguments 的构造器
// 2. 通过剩余参数的方式，吸收所有参数到一个【数组】中
function Arguments(...args){
    // 3. 直接将数组 args 的原型设置为 Arguments，使 args 成为 Arguments 的实例
    // 4. 将设置后的 args 对象【作为实例化结果】返回，替代默认生成的 this
    return Object.setPrototypeOf(args, Arguments.prototype);
}
/* 5. 为 Arguments 添加 和 Array 相同的迭代方法
    使 Arguments 可以具有和数组类似的操作
*/
Arguments.prototype[Symbol.iterator] = Array.prototype[Symbol.iterator];

// 6. 实例化对象
var args = new Arguments('aaa', 'bbb', 'ccc', 'ddd');

// 7. 以数组的方式操作 args
console.log(args.length);
console.log(args[1]);
