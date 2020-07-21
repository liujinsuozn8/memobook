"use strict";
// 类装饰器
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
// 1. 定义普通装饰器
// param 就是被装饰的类本身
function logClass(target) {
    // 为类添加额外的方法
    target.prototype.info = function (msg) {
        console.log(msg);
    };
}
// 2. 定义装饰器工厂
function ApiURL(url) {
    url = 'http://' + url;
    return function (target) {
        // 为类添加额外的属性
        target.prototype.apiUrl = url;
    };
}
// 3. 使用装饰器标识类，类会自动作为装饰器函数的参数
var HttpClient = /** @class */ (function () {
    function HttpClient() {
    }
    HttpClient.prototype.getData = function () { };
    HttpClient = __decorate([
        logClass,
        ApiURL('www.aaaa.bbb')
    ], HttpClient);
    return HttpClient;
}());
// 4. 使用装饰器扩展的内容
// 默认情况下直接使用会有编译异常
// 可以将 实例的类型声明为 any 来避免
// let hc: HttpClient = new HttpClient();
var hc = new HttpClient();
console.log(hc.apiUrl); // http://www.aaaa.bbb
hc.info('test msg'); // test msg
