var a = {}
Object.defineProperty(a, 'count', {
    value: 100,
    writable:false,
    configurable:false
});

console.assert( a.count === 100);
a.count = 200;
console.assert( a.count === 100);

// 无法修改属性
// Cannot redefine property: count
// Object.defineProperty(a, 'count', {
//     value: 200,
//     writable:true,
//     configurable:false
// });