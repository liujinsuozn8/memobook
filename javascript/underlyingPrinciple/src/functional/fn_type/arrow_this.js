// 测试箭头函数的this

function foo(){
    var fn = ()=>this.name;
    var obj = {name:'tom'};
    // 1. 将箭头函数绑定为函数内部的 obj
    console.log('call: ', fn.call(obj) );
    console.log('bind: ', fn.bind(obj)() );
}

var other = {name:'bob'};

// 2. 将执行 foo 时的 this 替换为 other
foo.call(other);

// 输出
// call:  bob       <<<< 绑定失败，仍然使用了上下文中的this
// bind:  bob       <<<< 绑定失败，仍然使用了上下文中的this