"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});
// 混合导出
function foo() {
    console.log("this is module2 foo");
}

function bar() {
    console.log("this is module2 bar");
}

exports.foo = foo;
exports.bar = bar;
var obj = exports.obj = { name: "testNamestr" };
