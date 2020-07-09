"use strict";
// 6. 枚举类型
console.log('----------枚举类型: 声明方式1------------');
// 6.1 声明方式1: 为每个标识符指定具体的数值
var PayStatus;
(function (PayStatus) {
    PayStatus[PayStatus["pay"] = 20] = "pay";
    PayStatus[PayStatus["cost"] = 30] = "cost";
    PayStatus[PayStatus["end"] = 40] = "end";
})(PayStatus || (PayStatus = {}));
console.log(PayStatus.pay); // 输出: 20
console.log(PayStatus.cost); // 输出: 30
console.log(PayStatus.end); // 输出: 40
console.log('----------枚举类型: 声明方式2------------');
// 6.2 声明方式2，不为标识符设置数值，使用索引值
var Flag;
(function (Flag) {
    Flag[Flag["status01"] = 0] = "status01";
    Flag[Flag["status02"] = 1] = "status02";
    Flag[Flag["status03"] = 2] = "status03";
})(Flag || (Flag = {}));
console.log(Flag.status01); // 输出: 0
console.log(Flag.status02); // 输出: 1
console.log(Flag.status03); // 输出: 2
console.log('----------枚举类型: 声明方式3------------');
// 6.3 方式3: 方式1、方式2 混合
var MyType;
(function (MyType) {
    MyType[MyType["type1"] = 0] = "type1";
    MyType[MyType["type2"] = 1] = "type2";
    MyType[MyType["type3"] = 25] = "type3";
    MyType[MyType["type4"] = 26] = "type4";
    MyType[MyType["type5"] = 27] = "type5";
    MyType[MyType["type6"] = 45] = "type6";
    MyType[MyType["type7"] = 46] = "type7";
})(MyType || (MyType = {}));
console.log(MyType.type1); // 输出: 0
console.log(MyType.type2); // 输出: 1
console.log(MyType.type3); // 输出: 25
console.log(MyType.type4); // 输出: 26
console.log(MyType.type5); // 输出: 27
console.log(MyType.type6); // 输出: 45
console.log(MyType.type7); // 输出: 46
console.log('----------枚举类型: 为变量赋值------------');
var e1 = MyType.type4;
console.log(e1); // 输出: 26
console.log(e1 == MyType.type4); // 输出: true
