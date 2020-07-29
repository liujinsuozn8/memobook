// 导入模块A

const obj = require('./moduleA')
console.log(obj);   // 输出: { [Symbol(mySymbol)]: 'abcd' }

// 通过全局符号表访问符号属性
var mySymbol = Symbol.for("mySymbol");
console.log(obj[mySymbol]); // 输出: abcd