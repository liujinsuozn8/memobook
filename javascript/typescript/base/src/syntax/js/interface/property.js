"use strict";
// 属性接口
// 2. 在多个方法中复用属性接口
// 2.1 在方法的形参中使用接口
function printInfo(data) {
    console.log("data.name = " + data.name + ", data.age = " + data.age);
}
function printInfo02(data) {
    console.log("name = " + data.name + ", age = " + data.age);
}
// 2.2 直接在调用时创建一个对象作为实参
// 属性数量比结果多，编译异常
// error TS2345: Argument of type '{ name: string; age: number; address: string; }' is not assignable to parameter of type 'Info'.
// printInfo({name:'testName1', age:22, address:'qwertyu'});
printInfo({ name: 'testName1', age: 22 });
printInfo02({ name: 'testName2', age: 33 });
// 2.3 先创建一个对象，然后将对象作为实参
var info = { name: 'testName3', age: 11, address: 'asdfghjk' };
printInfo(info);
printInfo02(info);
