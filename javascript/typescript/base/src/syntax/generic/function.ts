// 泛型函数

// 1. 定义泛型函数
function foo<T, S>(v1:T, v2:S):T{
    console.log(v2);
    return v1;
}
// 2. 使用泛型函数，使用时指定泛型的具体类型
console.log(foo<number, boolean>(1234, false));
console.log(foo<string, number>('12345', 23456));
console.log(foo<boolean, null>(true, null));