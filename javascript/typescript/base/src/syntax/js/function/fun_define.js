"use strict";
// 定义函数
// 1. 具名函数
function fun01() {
    return 'abc';
}
console.log(fun01());
// 返回值类型与函数声明不同，会产生异常
// function fun02(): string{
//     return 1234;
// }
// 2. 匿名函数
var fun03 = function () {
    return '123abc';
};
console.log(fun03());
// 3. 没有返回值的函数
function print(info) {
    console.log(info);
}
print('qwe');
