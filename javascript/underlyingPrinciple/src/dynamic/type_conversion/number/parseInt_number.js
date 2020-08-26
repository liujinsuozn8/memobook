var obj = {
    toString(){
        return '2e12';
    },
    valueOf(){
        return 3e10;
    }
}

// 1. parseInt、parseFloat 预期参数是字符串
// 会先调用 toString
console.log( parseInt(obj) );   // 2
console.log( parseFloat(obj) ); // 2000000000000

// 2. Number 预期类型数值，会先调用 valueOf
console.log( Number(obj) );     // 30000000000