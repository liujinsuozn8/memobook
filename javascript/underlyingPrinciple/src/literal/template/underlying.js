// 1. ????
// let fn1 = tpl =>console.log(typeof tpl, tpl instanceof Array, ref=tpl);
// let fn1 = tpl =>console.log(`aaa=` + typeof tpl);
// fn1`1234`;      // object true [ '1234' ]
// fn1(`1234`);    // string false 1234


// 2. 模版字面量的本质
// 2.1 使用模版字面量
let param1 = 1234;
let param2 = 'qwer';
let originStr = `number=${param1}, string=${param2}`;
console.log(originStr); // number=1234, string=qwer

// 2.2 使用String.raw 的函数形式
// 2.2.1 通过数组定义插入点
// 相当于 let rawStr01 = `number=${param1}string=${param1}`
let rawStr01 = String.raw({raw:['number=', ', string=','']}, param1, param2);
console.log("rawStr01= " + rawStr01)        // number=1234, string=qwer

// 2.2.2 如果 raw 数组中没有三个元素 ''， param2 无法被插入
// 相当于 let rawStr02 = `number=${param1}string=`
let rawStr02 = String.raw({raw:['number=', ', string=']}, param1, param2);
console.log("rawStr02= " + rawStr02)        // number=1234, string=

// 2.2.3 通过字符串定义插入点
// 相当于 let rawStr03 = `a${111}b${222}c${333}d`
let rawStr03 = String.raw({raw: 'abcd'}, 111, 222, 333);
console.log("rawStr03= " + rawStr03);       // rawStr03= a111b222c333d

// 2.3 String.raw 的运算符式调用
// let rawStr03 = String.raw`...`
// 转义字符不会生效，但是捕获遍历仍然有效
let param03 = 12345
console.log(String.raw`aaaa\nbbb, num=${param03}`);  // aaaa\nbbb, num=12345

// 2.4 将 String.raw 单独声明为 标签函数 来复用
// 声明函数
let foo = template => String.raw(template, 'v1', 'v2', 'v3');
// 复用
// console.log(foo`k1=${}, k2=${}, k3=${}`);        // 必须声明捕获的变量
console.log(foo`k1=${0}, k2=${1}, k3=${2}`);        // k1=v1, k2=v2, k3=v3