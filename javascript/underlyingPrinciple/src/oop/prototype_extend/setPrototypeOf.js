// 1. 创建2层继承关系
// 1.1 声明父类
function Foo(){}
Foo.prototype.run = ()=> console.log('this is run');

// 1.2 创建第一层继承关系
function FooEx(){}
FooEx.prototype = new Foo();
FooEx.prototype.constructor = FooEx;

// 1.3 创建第二层继承关系
function FooEx2(){}
FooEx2.prototype = new FooEx();
FooEx2.prototype.constructor = FooEx2;

// 2.1 检查实例的继承关系
var obj = new FooEx2();
console.log(obj instanceof FooEx);  // true
console.log(obj instanceof Foo);    // true
// 2.2 调用父类中的方法
obj.run();  // this is run

// 3.1 修改对象的隐式原型
Object.setPrototypeOf(obj, {});
// 2.1 检查实例的继承关系
console.log(obj instanceof FooEx);  // false
console.log(obj instanceof Foo);    // false
console.log(obj instanceof Object); // true