"use strict";
// 函数类型接口
// 2. 接口实现
var formate01 = function (key, value) {
    return key + value;
};
var formate02 = function (key, value) {
    return "key = " + key + ", value=" + value;
};
console.log(formate01('aaa', 123)); // 输出: aaa123
console.log(formate02('aaa', 123)); // 输出: key = aaa, value=123
