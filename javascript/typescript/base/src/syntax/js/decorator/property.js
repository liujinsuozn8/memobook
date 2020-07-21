"use strict";
// 属性装饰器
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
// 1. 定义属性装饰器
// 为属性设置属性值
function Value(propValue) {
    // target: 对于静态成员来说是类的构造函数，对于实例成员是类的原型对象
    // propName: 成员的名字
    return function (target, propName) {
        target[propName] = propValue;
    };
}
// function Value(target:any, propName:any){
//     target[propName] = 'wwwwwww';
// }
// 2. 使用属性装饰器
var StudentA = /** @class */ (function () {
    function StudentA() {
    }
    StudentA.prototype.printInfo = function () {
        console.log(this.name);
    };
    __decorate([
        Value('www.aa.bbb')
    ], StudentA.prototype, "name", void 0);
    return StudentA;
}());
var studentA = new StudentA();
studentA.printInfo(); // www.aa.bbb
