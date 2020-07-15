"use strict";
//  函数参数
// 1. 定义有参函数（具名函数、匿名函数的参数声明相同）
function info2Str(name, age) {
    return "name=" + name + ", age=" + age;
}
console.log(info2Str('testName', 22));
// 实参与形参类型不一致，ts编译异常
// console.log( info2Str('testName', "22") );
// 2. 可选参数函数: 参数名?:类型
// es里方法的实参和形参数量可以不同，但是在ts中必须相同。如果不同，必须配置可选参数
// 可选参数必须配置到参数的最后
function getInfo(name, address) {
    // 判断可选参数是否传值
    if (address) {
        return "name=" + name + ", address=" + address;
    }
    else {
        return "name=" + name + ", address=null";
    }
}
console.log(getInfo('testName', 'qwertyui'));
console.log(getInfo('testName'));
// 3. 默认参数:  参数名:类型=默认值
// 3.1 默认参数是最后一个参数
function getInfo02(name, age) {
    if (age === void 0) { age = 22; }
    return "name=" + name + ", age=" + age;
}
console.log(getInfo02('testName')); // 使用默认值
console.log(getInfo02('testName', 33));
// 3.2 默认参数在中间
function getInfo03(name, age, address) {
    if (age === void 0) { age = 22; }
    return "name=" + name + ", age=" + age + ", address=" + address;
}
// 只能通过设置undefined来省略默认参数
console.log(getInfo03('testName1', undefined, 'aaabbbccc'));
console.log(getInfo03('testName2', 40, 'ertyuu'));
// 4. 可变参数
// 所有形参全部赋值给数组
function sum() {
    var params = [];
    for (var _i = 0; _i < arguments.length; _i++) {
        params[_i] = arguments[_i];
    }
    var result = 0;
    for (var i = 0; i < params.length; i++) {
        result += params[i];
    }
    return result;
}
console.log(sum());
console.log(sum(1, 2, 3, 4, 5));
// 包含一个普通参数，可变参数需要放到最后
function sum02(a) {
    var others = [];
    for (var _i = 1; _i < arguments.length; _i++) {
        others[_i - 1] = arguments[_i];
    }
    for (var i = 0; i < others.length; i++) {
        a += others[i];
    }
    return a;
}
console.log(sum(1));
console.log(sum(1, 2, 3, 4, 5));
// 5. 对象类型参数的约束
function printName(info) {
    console.log("info.name = " + info.name + " info.age = " + info.age);
}
// 5.1 直接在调用时创建一个对象作为实参
// 5.1.1 没有包含指定的属性，编译异常
// error TS2345: Argument of type '{ name: string; }' is not assignable to parameter of type '{ name: string; age: number; }'.
// printName({name:'testName'});
// 5.1.2 比函数定义中的属性多，编译异常
// Argument of type '{ name: string; age: number; adderss: string; }' is not assignable to parameter of type '{ name: string; age: number; }'.
// let testObj = {name:'testName', age:20, adderss:'asdfgh'}
// printName({name:'testName', age:20, adderss:'asdfgh'});
// 5.1.3 属性数量、属性名、属性类型与定义相同，顺序可以不同
printName({ name: 'testName1', age: 20 });
printName({ age: 33, name: 'testName2' });
// 5.2 先创建一个对象，然后将对象作为实参
// 对属性的数量没有要求
var testObj = { name: 'testName3', age: 11, adderss: 'zxcvbnm' };
printName(testObj);
