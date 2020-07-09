// 其他类型的变量，无法修改类型
let a1:number = 1234;
// a = 'str test';

// any类型变量，可以任意修改类型
let a2:any;
a2 = 1234;
console.log(a2);
a2 = 'str test';
console.log(a2);