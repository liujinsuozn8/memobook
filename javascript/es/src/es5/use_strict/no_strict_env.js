'use strict'
function foo(){
    try{
        x = 1234;       // 没有通过 var 声明变量，在执行期发生异常
    } catch (e){
        console.log(e.message); // 输出1: x is not defined
    }

    console.log(typeof x);      // 输出2: undefined
    
    // 间接调用 eval
    (0, eval)('x = 1234');      // 使用正常的 JS 语法，【创建了一个全局变量 x】
    console.log(typeof x);      // 输出3: number
}

foo();
console.log(x);                 // 输出4: 1234