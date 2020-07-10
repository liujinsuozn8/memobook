// 函数重载
// ts为了兼容es5、es6，重载的写法与其他语言不同

// 1. 参数数量相同，类型不同
function makeInfo(name:string):string;   // 重载声明
function makeInfo(age:number):string;    // 重载声明
function makeInfo(info:any):string{      // 重载实现
    // 通过参数类型判断是哪个重载
    if (typeof info === 'string'){
        return `name = ${info}`;
    } else {
        return `age = ${info}`;
    }
}

console.log(makeInfo('testName'));
console.log(makeInfo(22));
// 无法找到匹配的重载
// makeInfo(true);

// 2. 参数数量不同
function formatInfo(name:string):string;                // 重载声明
function formatInfo(name:string, age:number):string;    // 重载声明
function formatInfo(name:string, age?:number):string{   // 重载实现
    // 通过判断age是否为空，来判断使用了哪个重载
    if (age){
        return `name=${name}, age=${age}`;
    } else {
        return `name=${name}`;
    }
}

console.log( formatInfo('testName', 22) );
console.log( formatInfo('testName02') );
// 无法找到匹配的重载
// console.log( formatInfo(123) );