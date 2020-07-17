"use strict";
// 泛型函数
// 1. 定义泛型函数
function foo(v1, v2) {
    console.log(v2);
    return v1;
}
// 2. 使用泛型函数，使用时指定泛型的具体类型
console.log(foo(1234, false));
console.log(foo('12345', 23456));
console.log(foo(true, null));
