"use strict";
// 可选属性接口
// 2. 使用接口
function printInfo03(data) {
    if (data.address) {
        console.log("name = " + data.name + ", age= " + data.age + ", address=" + data.address);
    }
    else {
        console.log("name = " + data.name + ", age= " + data.age);
    }
}
// 3.1 参数中包含可选属性
var obj1 = { name: 'testName4', age: 15, address: 'asdfghj' };
printInfo03(obj1);
// 3.2 参数中不包含可选属性
var obj2 = { name: 'testName5', age: 12 };
printInfo03(obj2);
