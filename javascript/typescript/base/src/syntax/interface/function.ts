// 函数类型接口

// 1. 声明函数类型接口
interface EntryFormat{
    (key:string, value:any):string;
}

// 2. 接口实现
let formate01: EntryFormat = function(key:string, value:any):string{
    return key + value;
};
let formate02: EntryFormat = function(key:string, value:any):string{
    return `key = ${key}, value=${value}`;
};

console.log(formate01('aaa', 123)); // 输出: aaa123
console.log(formate02('aaa', 123)); // 输出: key = aaa, value=123
