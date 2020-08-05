var obj = {
    source: 'abc.*',
    flags: 'i',
    [Symbol.match]: true
}

// 1. 将普通对象模拟为正则表达式
var rx = new RegExp(obj);
// 2. 以正则表达式的方式使用对象
console.log(rx) // /abc.*/i
console.log(rx.test('12345abcde')); // true