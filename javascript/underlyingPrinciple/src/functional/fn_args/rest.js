// 1. 基本使用
function foo01(a, ...more){
    console.log(a);
    console.log(more);
}
foo01('aaa', 1, 2, 3, 4);
// aaa
// [ 1, 2, 3, 4 ]

console.log('-------------------------------------------');

// 2. 用模板捕获部分参数
function foo02(a, ...[x, y]){
    console.log(`a = ${a}`);
    console.log(`x = ${x}`);
    console.log(`y = ${y}`);
}
foo02('aaa', 1, 2, 3);
// a = aaa
// x = 1
// y = 2

console.log('-------------------------------------------');

// 3. 在模板中，继续捕获剩余参数
function foo03(a, ...[x, y, ...z]) {
    console.log(`a = ${a}`);
    console.log(`x = ${x}`);
    console.log(`y = ${y}`);
    console.log(`z = ${z}`);
}
foo03('aaa', 1, 2, 3, 4, 5, 6, 7, 8);
// a = aaa
// x = 1
// y = 2
// z = 3,4,5,6,7,8

console.log('-------------------------------------------');

// 4. 如果没有传入剩余参数，剩余参数将会是一个空数组对象
function foo04(a, ...b){
    console.log(a);
    console.log(b);
}

foo04('aaa');
// aaa
// []