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

// 创建一个调用关系
// test --> level_2 --> level_n --> enumStack
function level_n(){
    enumStack(showIt);
}

function level_2(){
    level_n();
}

function test(){
    level_2();
}

test();
// ->level_n
// ->level_2
// ->test
// ->