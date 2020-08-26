var x = {
    toString() { return '23' },
    valueOf() { return 45 }
};

var y = {
    toString() { return '66' },
};

// 1. 对 x 调用 valueOf，没有字符串，进行数值运算
console.log(1 + x);     // 46

// 2. 对 x 调用 valueOf。操作数中包含字符串，优先执行字符串连接
console.log("1" + x);   // 145
console.log(x + "1");   // 451

/*
    3. y 没有 valueOf，会调用 toString()，返回 "66"
        操作数中包含字符串，优先执行字符串连接
*/
console.log(1 + y);     // 166