"use strict";
// 类类型接口
// 2. 实现接口，同时实现多个
var Bird = /** @class */ (function () {
    function Bird(name, age) {
        this.name = name;
        this.age = age;
    }
    // 2.2 实现方法约束
    // 2.2.1 AnimalInterface 接口的约束
    Bird.prototype.getInfo = function () {
        return "bird: name=" + this.name + ", age=" + this.age;
    };
    Bird.prototype.eat = function (food) {
        console.log("bird: " + this.name + " is eating " + food);
    };
    // 2.2.2 Runnable 接口的约束
    Bird.prototype.run = function () {
        console.log("bird is running");
    };
    return Bird;
}());
// 3. 实例化对象并使用
var bird = new Bird('tom', 3);
console.log(bird.getInfo());
bird.eat('APPLE');
bird.run();
