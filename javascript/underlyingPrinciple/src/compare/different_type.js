let num1 = 1;
let num2 = 23;
let num3 = 24;
let str = '23';
let b0 = false;
let b1 = true;
let o1 = {name:'aaa', age:22};
let o2 = {name:'aaa', age:22};
let o3 = new Object();
o3.name = 'aaa';
o3.age = 22;
let o4 = {name:'aaa', age:23};

// num 与 string比较，str转换为 number
console.log( `num1 < str: ${num1 < str}` );  //true
console.log( `num2 <= str: ${num2 <= str}` ); //true
console.log( `num3 <= str: ${num3 <= str}` ); //false

// true、false 比较，boolean转换为 number
console.log( `b1 > b0: ${b1 > b0}` );     //true

// boolean 与 number 比较，boolean、str转换为 number
console.log( `num1 <= b1: ${num1 <= b1}` );  //true
console.log( `num1 <= b0: ${num1 <= b0}` );  //false

// object 之间比较，object的属性、属性值相等
console.log( `o1 < o2: ${o1 < o2}` );       // false
console.log( `o1 > o2: ${o1 > o2}` );       // false
console.log( `o1 <= o2: ${o1 <= o2}` );     // true
console.log( `o1 >= o2: ${o1 >= o2}` );     // true

// 对象字面量与new Object 比较
console.log( `o1 < o3: ${o1 < o3}` );       // false
console.log( `o1 > o3: ${o1 > o3}` );       // false
console.log( `o1 <= o3: ${o1 <= o3}` );     // true
console.log( `o1 >= o3: ${o1 >= o3}` );     // true

// object 之间比较，object的属性、属性值不相等
console.log( `o1 < o4: ${o1 < o4}` );       // false
console.log( `o1 > o4: ${o1 > o4}` );       // false
console.log( `o1 <= o4: ${o1 <= o4}` );     // true
console.log( `o1 >= o4: ${o1 >= o4}` );     // true

// 空对象比较
console.log( {} < {} );     // false
console.log( {} > {} );     // false
console.log( {} <= {} );    // true
console.log( {} >= {} );    // true