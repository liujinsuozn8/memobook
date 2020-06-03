"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});
// 混合导出
function fun() {
    console.log("this is module4 fun");
}

function fun2() {
    console.log("this is module4 fun2");
}

exports.fun = fun;
exports.fun2 = fun2;
