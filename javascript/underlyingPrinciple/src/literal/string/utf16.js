// js字符串对 UTF-16 的支持

// '\u{nnnnn}' 使用UTF-16字符
let str3 = '\u{20BB7}'
console.log(`str3 = ${str3}`);                  // str3 = 𠮷
console.log(`str3.length = ${str3.length}`);    // str3.length = 2
