"use strict";
var Fruit = /** @class */ (function () {
    function Fruit(name) {
        this.name = name;
    }
    Fruit.print = function () {
        console.log('this is Fruit');
        // 在静态方法中调用静态属性时，需要通过类来调用
        console.log("type = " + Fruit.type);
        // 无法调用非静态方法和变量，编译异常
        // error TS2304: Cannot find name 'getName'.
        // getName();
    };
    Fruit.prototype.getName = function () {
        return this.name;
    };
    // 声明静态方法与静态变量
    Fruit.type = 'xxxxx';
    return Fruit;
}());
// 调用类的静态方法
Fruit.print();
// 调用类的静态函数
console.log(Fruit.type);
