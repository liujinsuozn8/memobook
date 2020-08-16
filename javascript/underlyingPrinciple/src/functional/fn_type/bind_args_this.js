// 绑定函数测试

function foo() {
    'use strict';
    console.log('this: ', this);
    console.log('args: ', ...arguments);
}

// 0. 创建绑定函数
var newFoo = foo.bind(null, 1, 2, 3);

// 1. 执行时附加参数测试
newFoo(4, 5, 6);
// this:  null
// args:  1 2 3 4 5 6

// 2. call、apply 无法修改已绑定的this
newFoo.call(new Object(), 5, 6, 7, 8);
// this:  null
// args:  1 2 3 5 6 7 8

newFoo.apply(new Object(), [5, 6, 7, 8]);
// this:  null
// args:  1 2 3 5 6 7 8