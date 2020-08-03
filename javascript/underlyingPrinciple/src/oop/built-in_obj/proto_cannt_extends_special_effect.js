// 1. 创建一个继承 Function 的类
function foo(){}
foo.prototype = new Function();

// 2. 创建一个函数对象
var newFn = new foo('console.log("this is foo")');
console.assert( newFn instanceof Function === true);

// 3. 虽然newFn是函数对象，但是无法继承 Function 的特殊效果: 可以被调用
// 会产生异常: newFn is not a function
newFn();