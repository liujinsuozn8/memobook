//  函数参数
// 1. 定义有参函数（具名函数、匿名函数的参数声明相同）
function info2Str(name:string, age:number): string{
    return `name=${name}, age=${age}`;
}

console.log( info2Str('testName', 22) );

// 实参与形参类型不一致，ts编译异常
// console.log( info2Str('testName', "22") );

// 2. 可选参数函数: 参数名?:类型
// es里方法的实参和形参数量可以不同，但是在ts中必须相同。如果不同，必须配置可选参数
// 可选参数必须配置到参数的最后
function getInfo(name:string, address?:string): string{
    // 判断可选参数是否传值
    if (address){
        return `name=${name}, address=${address}`;
    } else {
        return `name=${name}, address=null`;
    }
}
console.log(getInfo('testName', 'qwertyui'));
console.log(getInfo('testName'));

// 3. 默认参数:  参数名:类型=默认值
function getInfo02(name:string, age:number=22):string{
    return `name=${name}, age=${age}`;
}
console.log( getInfo02('testName') ); // 使用默认值
console.log( getInfo02('testName', 33) );

// function getInfo03(age:number=22, name:string){
//     return `name=${name}, age=${age}`;
// }
// console.log( getInfo03('xxxx'));

// 4. 可变参数
// 所有形参全部赋值给数组
function sum(...params:number[]):number{
    let result: number = 0;
    for(let i=0; i < params.length; i++){
        result += params[i];
    }
    return result;
}
console.log( sum() );
console.log( sum(1,2,3,4,5) );

// 包含一个普通参数，可变参数需要放到最后
function sum02(a:number, ...others:number[]):number{
    for(let i=0; i< others.length; i++){
        a += others[i];
    }
    return a;
}

console.log( sum(1) );
console.log( sum(1,2,3,4,5) );