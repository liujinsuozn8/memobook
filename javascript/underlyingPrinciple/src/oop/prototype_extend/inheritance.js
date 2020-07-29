// 1. 创建构造函数 `A()`，并在原型上添加一些成员
function A(){}  // A 的原型是 {}
A.prototype.a1 = ()=> console.log('this is a1');
A.prototype.a2 = ()=> console.log('this is a2');

// 2. 创建构造函数 `B()`，并重设原型
function B(){}
var instanceA = new A();
B.prototype = instanceA;
B.prototype.constructor = B;

// 3. 在 `B()` 的原型上添加一些成员
B.prototype.b1 = ()=> console.log('this is b1');
B.prototype.b2 = ()=> console.log('this is b2');

// 这些方法实际上添加在 instanceA 对象内，可以通过 instanceA 调用这些方法
instanceA.b1(); // this is b1
instanceA.b2(); // this is b2

// instanceA 是 A() 的实例，所以可以调用 A.prototype 上的方法
instanceA.a1(); // this is a1
instanceA.a2(); // this is a2

// 4. 实例化 `B()`
var instanceB = new B();

// 5. `instanceB`，实际上是 `instanceA` 对象的拷贝
// instanceB 可以访问 B.prototype 上的方法
// instanceB 也可以访问 A.prototype 上的方法
instanceB.b1(); // this is b1
instanceB.b2(); // this is b2
instanceB.a1(); // this is a1
instanceB.a2(); // this is a2





