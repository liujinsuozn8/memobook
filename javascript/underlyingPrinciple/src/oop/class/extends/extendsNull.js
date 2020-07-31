class Foo extends null{
    // 在构造器中手动返回一个对象
    constructor() {
        return Object.create(new.target.prototype)
    }
    foo() {
        console.log('call foo')
    }
}

// 实例化对象
var foo = new Foo();
foo.foo();  // call foo

// 检查类型
console.log(foo instanceof Foo);    // true
// 对象隐式原型的隐式原型被删除了，无法找到Object
// 不在是Object的子类
console.log(foo instanceof Object); // false
