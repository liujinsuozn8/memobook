var obj = [1,2,3,4];
// 默认会展开元素
var arr1 = [0].concat(obj);
console.log(arr1);  // [ 0, 1, 2, 3, 4 ]
console.log(arr1.length);   // 5

// 设置为不展开元素
obj[Symbol.isConcatSpreadable] = false;
var arr2 = [0].concat(obj);
console.log(arr2);   // [ 0, [ 1, 2, 3, 4, [Symbol(Symbol.isConcatSpreadable)]: false ] ]
console.log(arr2.length);   // 2