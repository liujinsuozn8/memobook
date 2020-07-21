"use strict";
// 方法装饰器
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
// 1. 定义方法装饰器
// 装饰方法，并将方法的所有参数转换为字符串
function MethodParmasToString(target, propName, descriptor) {
    // 1.1 从属性描述符中获取原始的函数，并保存
    var originFn = descriptor.value;
    // 1.2 重新设置函数
    descriptor.value = function () {
        var args = [];
        for (var _i = 0; _i < arguments.length; _i++) {
            args[_i] = arguments[_i];
        }
        // 1.3 将所有参数转换为String
        args = args.map(function (n) { return String(n); });
        // 1.4 重新调用原始方法
        originFn.apply(void 0, args);
    };
}
// 2. 使用方法装饰器
var Logger = /** @class */ (function () {
    function Logger() {
    }
    Logger.prototype.info = function () {
        var args = [];
        for (var _i = 0; _i < arguments.length; _i++) {
            args[_i] = arguments[_i];
        }
        console.log(args);
    };
    __decorate([
        MethodParmasToString
    ], Logger.prototype, "info", null);
    return Logger;
}());
var log = new Logger();
log.info(1234, 'aaaa', false); // [ '1234', 'aaaa', 'false' ]
