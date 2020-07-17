// 泛型函数接口

// 1 将泛型声明在接口定义中
interface GenericFunc<T>{
    (value:T):T;
}

// 1.1 接口实现
function getData<T>(value:T):T{
    return value;
}

// 1.2 调用接口实现
// 1.2.1 固定一种类型并赋值给变量
let myGetData: GenericFunc<string> = getData;
console.log(myGetData('qwert'));

// 1.2.2 在调用时指定类型
console.log(getData<number>(12345));

//------------------------------------------------------------
// 2 将泛型声明在方法定义中
interface GenericFunc02{
    <T> (value: T):T
}

// 2.1 接口实现
function getData02<T>(value: T):T{
    return value;
}

// 2.2 调用接口实现
// 2.2.1 固定一种类型并赋值给变量
let myGetData02: GenericFunc<string> = getData02;
console.log(myGetData02('qwert'));

// 2.2.2 在调用时指定类型
console.log(getData02<number>(12345));

