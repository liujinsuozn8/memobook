// 1. 创建一个原型对象
var a = {}
Object.defineProperty(a, 'count', {
    value: 10,
    writable: false,    // 设置为不可写、不可修改
    enumerable: false,
    configurable: false,
});

// 2. 通过原型对象创建一个新的对象
var b = Object.create(a);

// 3. 此时使用的是原型对象的 count 属性
console.assert(b.count === 10);
// 4. 对象自身没有 count 属性
console.assert(b.hasOwnProperty('count') === false);

// 5. 修改属性时，因为原型上的属性是 writable: false 的，所以无法隐式创建的属性描述符
b.count = 20
// 6. 属性值无法修改
console.assert(b.count === 10);
// 7. 不会隐式创建的属性描述符
console.assert(b.hasOwnProperty('count') === false);

// 8. 使用 Object.defineProperty 设置属性描述符
Object.defineProperty(b, 'count', {
    value: 20,
    writable: true,
    enumerable: true,
    configurable: true
});

// 9. 在子类对象的自由属性表中添加的属性描述符
console.assert( b.count === 20 );
console.assert( b.hasOwnProperty('count') === true );
// { value: 20, writable: true, enumerable: true, configurable: true }
console.log(Object.getOwnPropertyDescriptor(b, 'count'));