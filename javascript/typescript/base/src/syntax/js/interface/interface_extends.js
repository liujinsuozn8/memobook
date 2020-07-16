"use strict";
// 接口间的继承
//3. 实现接口，同实现所有的约束
var MyInstance = /** @class */ (function () {
    function MyInstance() {
    }
    MyInstance.prototype.work = function () {
        console.log('instance is working');
    };
    MyInstance.prototype.run = function () {
        console.log('instance is running');
    };
    MyInstance.prototype.fly = function () {
        console.log('instance is flying');
    };
    return MyInstance;
}());
var a = new MyInstance();
a.work();
a.run();
a.fly();
