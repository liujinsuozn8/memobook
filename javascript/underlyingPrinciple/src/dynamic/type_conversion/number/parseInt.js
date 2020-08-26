// parseInt 测试

// 1. 只能处理字符串中开头部分的有效数字
console.log( parseInt('1234px') );  // 1234

// 2. 如果字符串开头没有有效数字，返回 NaN
console.log( parseInt('q12345') );  // NaN

// 3. 指定 16 进制处理，不设置 前缀 0x
console.log( parseInt('12', 16) );  // 18

// 4. 指定 8 进制处理，不设置 前缀 0
console.log( parseInt('12', 8) );   // 10

// 5. 不指定 radix，可以处理 16 进制数字
console.log( parseInt('0x012') );   // 18

// 6. 不指定 radix，无法处理 8 进制数字
console.log( parseInt('012') );     // 12