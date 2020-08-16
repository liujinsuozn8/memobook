// 绑定函数作为构造函数测试

// 0. 创建构造函数
function Foo(arg){
    console.log(`arg = ${arg}`);
    console.log(Object.getPrototypeOf(this) === Foo.prototype);
    console.log(new.target === Foo);
}

// 1. 直接只用构造函数创建对象
new Foo(12345);
// arg = 12345
// true
// true

// 2. 通过绑定函数创建对象
var newFoo = Foo.bind(null, 2345);
new newFoo('abcd');
// arg = 2345
// true
// true