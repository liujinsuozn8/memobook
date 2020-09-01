// bind 后的函数作为构造器

var obj = {};

function Foo(x, y, z) {
    // 3. 绑定函数作为构造函数时，this会变成新创建的实例对象
    console.log(this === obj);      // false

    // 4. 参数将使用 绑定时传入的参数，如果有剩余则使用 调用构造函数时传入的参数
    console.log(`x=${x}, y=${y}, z=${z}`);  // x=aaa, y=bbb, z=ccc
}

// 1. 将 foo 的 this 绑定为 obj
var NewFoo = Foo.bind(obj, 'aaa', 'bbb');

// 2. 绑定后的函数作为构造器创建实例对象
var result = new NewFoo('ccc', 'ddd', 'eee');

// 5. 实例对象将同时是: 原始函数 和 绑定后函数的 实例
console.log(result instanceof NewFoo);  // true
console.log(result instanceof Foo);     // true