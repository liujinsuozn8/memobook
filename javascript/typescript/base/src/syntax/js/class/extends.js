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
// 类的继承
var BasePerson = /** @class */ (function () {
    function BasePerson(name) {
        this.name = name;
    }
    BasePerson.prototype.printInfo = function () {
        console.log("name=" + this.name);
    };
    return BasePerson;
}());
// 1. 通过 extends 关键字继承某个类
var Student = /** @class */ (function (_super) {
    __extends(Student, _super);
    function Student(name) {
        // 2. 通过super调用父类
        return _super.call(this, name) || this;
    }
    // 定义 Student 自己的方法
    Student.prototype.study = function () {
        console.log(this.name + " is studying");
    };
    // 重写父类的方法
    Student.prototype.printInfo = function () {
        console.log("Student name = " + this.name);
    };
    return Student;
}(BasePerson));
var student = new Student('testName');
student.printInfo();
student.study();
