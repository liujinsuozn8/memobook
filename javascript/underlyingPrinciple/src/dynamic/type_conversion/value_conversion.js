String.prototype.valueOf = function(){
    console.log('valueOf');
    return 100;
}
String.prototype.toString = function(){
    console.log('toString');
    return this;
}

// 1. 相当于执行了: var x = new String('5'); x.valueOf()
console.log('5'.valueOf());
// 输出:
// valueOf
// 100   <<<<< number 类型

// 2. 没有隐式创建包装对象，所以不会调用 valueOf
console.log('5');
// 输出:
// 5    <<<<< string 类型

// 3. 加法运算会隐式调用包装类型对象的 valueOf
console.log(1 + new String('6'));
// 输出:
// valueOf
// 101   <<<<< number 类型

// 4. 符号运算会隐式调用包装类型对象的 valueOf
console.log(1 + +new String('6'));
// 输出:
// valueOf
// 101   <<<<< number 类型

// 5. +'6' 会将 '6' 转换为数值6，不会调用valueOf方法
console.log(1 + +'6');
// 输出:
// 7    <<<<< number 类型


/*
    6. '2' 值类型不会调用 valueOf 方法
        加法运算中字符串连接优先，所以会得到字符串 12
*/
console.log(1 + '2');
// 输出:
// 12   <<<<< string 类型