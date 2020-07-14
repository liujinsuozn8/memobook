"use strict";
var _this = this;
// 1. 捕获局部的this
// 在函数内部声明的箭头函数，默认函数的上下文this
function funX() {
    var _this = this;
    // 编译异常: error TS7041: The containing arrow function captures the global value of 'this'.
    var x = function () { return console.log(_this); };
    x();
}
// 2. 捕获全局的this
// 2.1 全局的箭头函数
// 编译异常: error TS7041: The containing arrow function captures the global value of 'this'.
var globalArrowFun = function () { return function () { return console.log(_this); }; };
// 2.2 对象内部的属性是箭头函数时，默认使用的是全局的this，在页面中是window
var objA = {
    // objA中没有this，继续向外搜索，使用的是全局的this
    // 编译异常: error TS7041: The containing arrow function captures the global value of 'this'.
    fn1: function () { return console.log(_this); },
    fn2: function () {
        console.log(this);
    },
};
