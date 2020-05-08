"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});
exports.foo = foo;
exports.bar = bar;
// 多次导出
function foo() {
    console.log("this is mdoule1 foo");
}

function bar() {
    console.log("this is mdoule1 bar");
}

var arr = exports.arr = [1, 2, "eee", 55];
