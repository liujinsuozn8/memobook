"use strict";
// 泛型类
// 1. 定义泛型类
var MyArrayList = /** @class */ (function () {
    function MyArrayList() {
        this.data = [];
    }
    MyArrayList.prototype.add = function (e) {
        this.data.push(e);
    };
    MyArrayList.prototype.toString = function () {
        var str = '';
        for (var _i = 0, _a = this.data; _i < _a.length; _i++) {
            var e = _a[_i];
            str += e + ' ,';
        }
        return str;
    };
    return MyArrayList;
}());
// 2. 实例化泛型类对象，并调用方法
var list = new MyArrayList();
list.add(123);
list.add(3456);
list.add(334);
console.log(list.toString());
// 没有调用点，会导致方法调用失败
// let toStringFn = list.toString;
// toStringFn();
