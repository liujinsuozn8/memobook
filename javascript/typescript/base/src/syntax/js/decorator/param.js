"use strict";
// 方法参数装饰器
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
// 1. 定义方法参数装饰器
function Param(target, funcName, paramIndex) {
    console.log(target);
    console.log(funcName);
    console.log(paramIndex);
}
// 2. 使用方法装饰器
var LoggerB = /** @class */ (function () {
    function LoggerB() {
    }
    LoggerB.prototype.info = function (msg) {
        console.log(msg);
    };
    __decorate([
        __param(0, Param)
    ], LoggerB.prototype, "info", null);
    return LoggerB;
}());
var logb = new LoggerB();
logb.info('aaaa');
