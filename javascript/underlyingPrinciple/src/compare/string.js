let str1 = 'abc';
let str2 = 'ab';
let str3 = '200';
let num = 100;

// 1. 两个字符串的比较，依次比较每个字符
console.log( str1 >= str2 );    // true

// 2. 非数字字符串与数字比较
// str转换为NaN，与任何值比较都是false
console.log( str1 <= num );     // false
console.log( str1 >= num );     // false

// 3. 数值字符串与数字比较
console.log( str3 <= num );     // false 
console.log( str3 >= num );     // true