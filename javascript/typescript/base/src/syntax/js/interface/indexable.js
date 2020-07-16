"use strict";
// 可索引接口
var arr01 = ['qwert', 'asdfg', 'zxcvb'];
console.log(arr01);
var obj01 = {
    name: 'testName',
    address: 'asghdfgh'
};
console.log(obj01);
// 3. 其他类型无法约束
// index 的类型不对，编译异常
// error TS1023: An index signature parameter type must be either 'string' or 'number'.
// interface MyObj02{
//     [index:MyArray]:string;
// }
