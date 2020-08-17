// 显示函数名
var showIt = f => console.log('->' + f.name);

// 遍历调用栈
function enumStack(callback){
    var f = arguments.callee;
    // 通过 callee 遍历调用栈
    while(f.caller){
        callback(f = f.caller);
    }
}

// 创建一个掉用关系
// <---------- max = 1 ----------><----------- max > 1 ---------->
// test --> level_2 --> level_n --> level_2 --> level_n...
var i = 0, max = 0;
function level_n(){
    if ( ++i < max){
        level_2();
    }
    enumStack(showIt);
}

function level_2(){
    level_n();
}

function test(){
    level_2();
}

/*
    1. max = 1 不会出现死循环
    - level_n.caller ---> level_2
    - level_2.test ---> test
*/
// max = 1;
// test();

// max > 1，会出现死循环
/*
    2. max > 1，会出现死循环
    - 顶层部分是:
        - level_n.caller ---> level_2
        - level_2.caller ---> level_n
    - 最底层是： level_2.test ---> test

    - 栈顶的 level_2、level_n 已经出现了死循环，所以无法遍历到栈底
*/
max = 2;
test();