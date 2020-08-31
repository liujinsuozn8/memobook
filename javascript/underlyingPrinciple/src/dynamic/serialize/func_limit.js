// 函数序列化测试

// 1. JS的内置代码
console.log(Object.toString.toString());
// function toString() { [native code] }

// 2. 普通函数
function foo() {
    console.log(100);
}

var fooStr = foo.toString();

// 2.1. 直接用 eval 解析函数代码文本
var f1 = eval(fooStr);
console.log(f1);    // undefined

// 2.2. 将代码文本套在 `()` 内，再用 eval 解析
var f2 = eval(`(${fooStr})`);
f2();   // 100

// 2.3. 使用 Function 解析，需要附加 `return ` 将函数作为返回值返回给调用者
var f3 = new Function('return ' + fooStr);
f3()(); // 100

// 3. 对象或类中的方法声明
var obj = {
    bar(){ console.log(1234) }
};

// 3.1 结果中不会包含 function 前缀
var barStr = obj.bar.toString();
console.log(barStr);    // bar(){ console.log(1234) }

// 3.2 手动添加 function 前缀来处理
var f4 = eval ( `(function ${barStr})` );
f4();   // 1234