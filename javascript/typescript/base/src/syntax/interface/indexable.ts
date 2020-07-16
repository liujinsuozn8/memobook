// 可索引接口

// 1. 数组约束
interface MyArray{
    [index:number]:string;
}

let arr01:MyArray = ['qwert', 'asdfg', 'zxcvb'];
console.log(arr01);

// 2. 对象约束
interface MyObj{
    [index:string]:any;
}

let obj01:MyObj = {
    name:'testName',
    address:'asghdfgh'
}

console.log(obj01);

// 3. 其他类型无法约束
// index 的类型不对，编译异常
// error TS1023: An index signature parameter type must be either 'string' or 'number'.
// interface MyObj02{
//     [index:MyArray]:string;
// }