// NUL字符: \0

// 1 声明方式1
str1 = String.fromCharCode(0, 0, 0, 0, 0);
console.log(`str1="${str1}"`);                  // str1=""
console.log(`str1.length=${str1.length}`);      // str1.length=5

// 2 声明方式2
str2 = '\0\0\0\0';
console.log(`str2="${str2}"`);                  // str2=""
console.log(`str2.length=${str2.length}`);      // str2.length=4