"use strict";
// 定义类
var Person = /** @class */ (function () {
    // 构造函数
    function Person(name) {
        this.age = 20; // 为属性设置默认值
        // 需要为属性设置，否则会有编译异常
        this.name = name;
        this.address = '';
    }
    Person.prototype.printInfo = function () {
        console.log("name=" + this.name + ", age=" + this.age + ", address=" + this.address);
    };
    // 为 address 添加getter
    Person.prototype.getAddress = function () {
        return this.address;
    };
    Person.prototype.setAddress = function (value) {
        this.address = value;
    };
    return Person;
}());
// 实例化对象
var person = new Person('testName');
person.printInfo(); // 输出: name=testName, age=20, address=
person.setAddress('abcdefg');
console.log(person.getAddress()); // 输出: abcdefg
person.age = 22;
person.printInfo(); // 输出: name=testName, age=22, address=abcdefg
// 无法直接访问 private 变量
// 编译异常
// error TS2341: Property 'address' is private and only accessible within class 'Person'.
// console.log(person.address);
