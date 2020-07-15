// 可选属性接口

// 1. 定义属性接口
interface Info2{
    name:string;
    age:number;
    address?:string;    // 定义可选属性
}

// 2. 使用接口
function printInfo03(data: Info2): void{
    if (data.address){
        console.log(`name = ${data.name}, age= ${data.age}, address=${data.address}`);
    }else{
        console.log(`name = ${data.name}, age= ${data.age}`);
    }
}

// 3.1 参数中包含可选属性
let obj1={ name:'testName4', age:15, address:'asdfghj' };
printInfo03(obj1);

// 3.2 参数中不包含可选属性
let obj2={ name:'testName5', age:12 };
printInfo03(obj2);