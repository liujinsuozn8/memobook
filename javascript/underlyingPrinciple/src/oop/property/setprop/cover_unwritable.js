// 1. 为对象设置一个不可写的属性
var a = {}
Object.defineProperty(a, 'count', {
    value:100,
    writable:false,
    configurable:true,
});
// 2. 无法对属性进行修改
a.count = 200
console.assert(a.count === 100);

// 3. 重新覆盖属性描述符，并设置为可写属性
Object.defineProperty(a, 'count', {
    value:200,
    writable: true,
});

// 4. 覆盖成功
console.assert(a.count === 200);
// 5. 可以正常修改属性
a.count = 300;
console.assert(a.count === 300);
