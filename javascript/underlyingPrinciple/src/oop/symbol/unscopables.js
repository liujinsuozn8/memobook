function foo(){}
var constructor = 'abcd';

// 1. 访问 foo 的原型对象
with(foo.prototype){
    // 相当于在比较 foo === foo.prototype.constructor
    console.log(foo === constructor);   // true
}

// 2. 在 with 闭包中，进制访问 foo.prototype.constructor
foo.prototype[Symbol.unscopables] = {constructor:true};
with(foo.prototype){
    // foo.prototype.constructor 被禁用了
    // 所以会使用外部作用域的 constructor
    console.log(foo === constructor);   // false

    // 输出外部作用域的 constructor
    console.log(constructor);   // abcd
}
