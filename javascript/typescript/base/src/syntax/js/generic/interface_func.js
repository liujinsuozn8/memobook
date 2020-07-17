"use strict";
// 泛型函数接口
// 1.1 接口实现
function getData(value) {
    return value;
}
// 1.2 调用接口实现
// 1.2.1 固定一种类型并赋值给变量
var myGetData = getData;
console.log(myGetData('qwert'));
// 1.2.2 在调用时指定类型
console.log(getData(12345));
// 2.1 接口实现
function getData02(value) {
    return value;
}
// 2.2 调用接口实现
// 2.2.1 固定一种类型并赋值给变量
var myGetData02 = getData02;
console.log(myGetData02('qwert'));
// 2.2.2 在调用时指定类型
console.log(getData02(12345));
