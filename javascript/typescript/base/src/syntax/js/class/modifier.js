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
// 1. 在类中声明不同修饰符的属性
var Tool = /** @class */ (function () {
    function Tool(name, introduction) {
        this.executedTime = 0;
        this.name = name;
        this.introduction = introduction;
    }
    Tool.prototype.run = function () {
        console.log(this.name + " is run, executedTime=" + this.executedTime);
        this.addExecutedTime();
    };
    Tool.prototype.stop = function () {
        console.log(this.name + " is stop");
        this.clearExecutedTime();
    };
    Tool.prototype.clearExecutedTime = function () {
        return this.executedTime = 0;
    };
    Tool.prototype.addExecutedTime = function () {
        this.executedTime += 10;
    };
    return Tool;
}());
var tool = new Tool('computer', 'this is a computer');
// 调用 public 方法
tool.run();
tool.run();
tool.stop();
tool.run();
// 无法调用 protected 方法，会有编译异常
// error TS2445: Property 'clearExecutedTime' is protected and only accessible within class 'Tool' and its subclasses.
// tool.clearExecutedTime();
// 无法调用 private 方法，会有编译异常
// error TS2341: Property 'addExecutedTime' is private and only accessible within class 'Tool'.
// tool.addExecutedTime();
// 2. 在继承类中使用父类中不同修饰符的属性
var SubTool = /** @class */ (function (_super) {
    __extends(SubTool, _super);
    function SubTool() {
        return _super.call(this, 'subtool', 'this is subtool') || this;
    }
    SubTool.prototype.work = function () {
        // 调用 protected 方法
        this.clearExecutedTime();
        // 调用 public 方法
        this.run();
        this.run();
        this.run();
        // 无法调用 private 方法，会有编译异常
        // error TS2341: Property 'addExecutedTime' is private and only accessible within class 'Tool'.
        // this.addExecutedTime();
    };
    return SubTool;
}(Tool));
var subtool = new SubTool();
subtool.work();
