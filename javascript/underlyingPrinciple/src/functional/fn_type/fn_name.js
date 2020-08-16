// 函数名 `name` 测试

// 0. 创建一个匿名函数、一个具名函数
var x = function(){};
var y = function foo(){};

// 1. 输出函数名
console.log(x.name);    // x
console.log(y.name);    // foo

// 2. name 属性是函数的【自有属性】
console.log(x.hasOwnProperty('name'));

// 3. 输出函数名的数据描述符
// { value: 'x', writable: false, enumerable: false, configurable: true }
console.log(Object.getOwnPropertyDescriptor(x, 'name'));
// { value: 'foo', writable: false, enumerable: false, configurable: true }
console.log(Object.getOwnPropertyDescriptor(y, 'name'));

// 4. 删除函数名
delete x.name;
delete y.name;
console.log(Object.getOwnPropertyDescriptor(x, 'name'));    // undefined
console.log(Object.getOwnPropertyDescriptor(y, 'name'));    // undefined

// 5. 无法通过属性赋值的方式来修改函数名
x.name = 'aaa';
console.log( x.hasOwnProperty('name') ); // false，不包含 name 属性

// 6. 通过数据描述符来修改函数名
Object.defineProperty(x, 'name', {value:'aaa', writable:false, enumerable:false, configurable:true});
console.log(x.name);    // aaa
// { value: 'aaa', writable: false, enumerable: false, configurable: true }
console.log(Object.getOwnPropertyDescriptor(x, 'name'));