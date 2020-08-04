// 1. 创建一个原型对象
var a = {count: 10}
Object.freeze(a);

// 2. 通过原型对象创建一个新的对象
var b = Object.create(a);

// 3. 无法添加属性
b.count = 20
// 4. 属性值无法修改
console.assert(b.count === 10);
// 5. 不会隐式创建的属性描述符
console.assert(b.hasOwnProperty('count') === false);

// 6. 使用 Object.defineProperty 设置属性描述符
Object.defineProperty(b, 'count', {
    value: 20,
    writable: true,
    enumerable: true,
    configurable: true
});

// 7. 子类对象可以覆盖父类属性描述符的影响
console.assert( b.count === 20 );
console.assert( b.hasOwnProperty('count') === true );
// { value: 20, writable: true, enumerable: true, configurable: true }
console.log(Object.getOwnPropertyDescriptor(b, 'count'));