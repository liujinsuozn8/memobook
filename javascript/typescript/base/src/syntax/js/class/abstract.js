"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
// 1. 定义抽象类和抽象方法
var Animal = /** @class */ (function () {
    function Animal(name) {
        this.name = name;
    }
    // 2. 定义普通方法
    Animal.prototype.getName = function () {
        return this.name;
    };
    return Animal;
}());
// 2.1 子类1
var Dog = /** @class */ (function (_super) {
    __extends(Dog, _super);
    function Dog(name) {
        return _super.call(this, name) || this;
    }
    Dog.prototype.run = function () {
        console.log("dog: " + this.getName() + " is running");
    };
    return Dog;
}(Animal));
// 2.2 子类2
var Cat = /** @class */ (function (_super) {
    __extends(Cat, _super);
    function Cat(name) {
        return _super.call(this, name) || this;
    }
    Cat.prototype.run = function () {
        console.log("cat: " + this.getName() + " is running");
    };
    return Cat;
}(Animal));
//3.  通过子类调用方法
var dog = new Dog('mydog');
dog.run();
var cat = new Cat('mycat');
cat.run();
