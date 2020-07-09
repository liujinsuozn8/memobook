// 6. 枚举类型

console.log('----------枚举类型: 声明方式1------------');
// 6.1 声明方式1: 为每个标识符指定具体的数值
enum PayStatus{
    pay= 20,
    cost= 30,
    end= 40,
}
console.log(PayStatus.pay); // 输出: 20
console.log(PayStatus.cost); // 输出: 30
console.log(PayStatus.end); // 输出: 40

console.log('----------枚举类型: 声明方式2------------');
// 6.2 声明方式2，不为标识符设置数值，使用索引值
enum Flag{
    status01,
    status02,
    status03,
}
console.log(Flag.status01); // 输出: 0
console.log(Flag.status02); // 输出: 1
console.log(Flag.status03); // 输出: 2

console.log('----------枚举类型: 声明方式3------------');
// 6.3 方式3: 方式1、方式2 混合
enum MyType{
    type1,
    type2,
    type3 = 25,
    type4,
    type5,
    type6 = 45,
    type7,
}
console.log(MyType.type1); // 输出: 0
console.log(MyType.type2); // 输出: 1
console.log(MyType.type3); // 输出: 25
console.log(MyType.type4); // 输出: 26
console.log(MyType.type5); // 输出: 27
console.log(MyType.type6); // 输出: 45
console.log(MyType.type7); // 输出: 46

console.log('----------枚举类型: 为变量赋值------------');
let e1 = MyType.type4;
console.log( e1 );  // 输出: 26
console.log( e1 == MyType.type4 );  // 输出: true
