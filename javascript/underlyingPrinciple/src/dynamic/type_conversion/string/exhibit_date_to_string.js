// date 显示转换成 string

var date = new Date();
// 1. toGMTString，转换为格林尼治标准时间的日期字符串
console.log( date.toGMTString() );          // Tue, 25 Aug 2020 06:53:57 GMT

// 2. toUTCString，转换为协调世界时的日期字符串
console.log( date.toUTCString() );          // Tue, 25 Aug 2020 06:53:57 GMT

// 3. toISOString，转换为ISO 8601标准的日期字符串
console.log( date.toISOString() );          // 2020-08-25T06:53:57.440Z

// 4. toDateString，将日期部分转换为默认格式的字符串
console.log( date.toDateString() );         // Tue Aug 25 2020

// 5. toTimeString，将时间部分转换为默认格式的字符串
console.log( date.toTimeString() );         // 14:53:57 GMT+0800 (GMT+08:00)

// 6. toLocaleDateString，宿主环境下的日期
console.log( date.toLocaleDateString() );   // 2020-8-25

// 7. toLocaleTimeString，宿主环境下的时间
console.log( date.toLocaleTimeString() );   // 14:53:57

// 8. toJSON，使用`JSON.stringify`时，调用的方法
console.log( JSON.stringify(date) );    // "2020-08-25T06:58:33.768Z"

// 9. 重新设置 toJSON 方法
date.toJSON = function(){
    var month = this.getMonth() + 1;
    return `${this.getFullYear()}${month < 10? '0' + month: month}${this.getDate()}`;
}
console.log( JSON.stringify(date) );    // 20200825