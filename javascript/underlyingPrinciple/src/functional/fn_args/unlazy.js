// 参数的`非惰性求值`特性

// 1. 函数只有一个参数
function print(msg){
    console.log(arguments);
    console.log(msg);
}
var a = 20;
// 向只有一个参数的函数传递多个表达式实参
print(a+=20, a*=2, 'value:'+a);
// 输出:
// [Arguments] { '0': 40, '1': 80, '2': 'value:80' }    <<<< 表达式全部计算完成
// 40       <<<<< 但是只有第一个参数有效


// 2. 函数有多个参数
function restPrint(a, b, c){
    console.log(arguments);
    console.log(a, b, c);
}

var b = 20;

// 向函数传递多个表达式实参
restPrint(b+=20, b*=2, 'value:'+b);

// 输出:
// [Arguments] { '0': 40, '1': 80, '2': 'value:80' }    <<<< 表达式全部计算完成
// 40 80 value:80