// 1. 尝试在不赋值的情况下使用变量
// let num1:number;
// console.log(num1);
/*
    只声明不赋值，在编译时会报错
    - error TS2454: Variable 'n' is used before being assigned.
    但是编译结果仍然可以继续使用，会输出: undefined
*/

console.log('-----------2. 将变量设置为 undefined 类型-----------');
// 2. 将变量设置为 undefined 类型
let num2:undefined;
// 2.1 undefined 类型可以不赋值，直接使用
console.log(num2);  // 输出 undefined
// 2.2 将变量设置为 undefined
num2 = undefined;
console.log(num2);  // 输出 undefined
/*
    2.3 如果将 undefined类型设置为其他类型的值
    编译器会报错
    - error TS2322: Type '1234' is not assignable to type 'undefined'.
    但是编译结果仍然可以继续使用，会输出: 1234
*/
// num2 = 1234;
// console.log(num2);

console.log('-----------3. 将变量设置为 即是 number 又是 undefined-----------');
// 3. 将变量设置为 即是 number 又是 undefined
let num3: number | undefined;
// 3.1 已定义、未赋值
console.log(num3);  // 输出: undefined

// 3.2 已定义、已赋值
num3 = 5678;
console.log(num3);  // 输出: 5678


console.log('-----------4. 将变量设置为 null-----------');
// 4. 将变量设置为 null
let num4: null;
// 4.1 如果未赋值就使用，编译器会报错
// - error TS2454: Variable 'num4' is used before being assigned.
// console.log(num4);

// 4.2 但是只能赋值为null，赋值为其他类型的数据会报错
// num4 = 12345;
num4 = null;
console.log(num4);  // 输出: null


console.log('-----------5. 将变量设置为 即是 number ，又是 undefined， 又是 null-----------');
// 5. 将变量设置为 即是 number ，又是 undefined， 又是 null
let num5: number | undefined | null;
// 5.1 已定义、未赋值
console.log(num5);  // 输出: undefined

// 5.2 已定义、已赋值，但是值是 null
num5 = null;
console.log(num5);  // 输出: null

// 5.3 已定义、已赋值
num5 = 7890;
console.log(num5);  // 输出: 7890