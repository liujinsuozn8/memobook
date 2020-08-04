// 1. 创建一个原型对象
var a = {}
Object.defineProperty(a, 'count', {
    value:10,
    writable:true,
    enumerable:false,
    configurable:false,
});

// 2. 通过原型对象创建一个新的对象
var b = Object.create(a);

// 3. 此时使用的是原型对象的 count 属性
console.assert( b.count === 10 );
// 4. 对象自身没有 count 属性
console.assert( b.hasOwnProperty('count') === false );

// 5. 修改属性时，会在自有属性表中添加一个 count 属性
b.count = 20
console.assert( b.count === 20 );
// 6. 此时对象中已经包含该属性了
console.assert( b.hasOwnProperty('count') === true );

// 7. 隐式创建的描述符
// { value: 20, writable: true, enumerable: true, configurable: true }
console.log( Object.getOwnPropertyDescriptor(b, 'count'));